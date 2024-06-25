import { Route, Routes, useNavigate } from "react-router-dom";
import "./globals.css";
import AuthLayout from "./_auth/AuthLayout";
import SigninForm from "./_auth/forms/SigninForm";
import SignupForm from "./_auth/forms/SignupForm";
import RootLayout from "./_root/RootLayout";
import {
  ChatBox,
  CreatePost,
  Home,
  NoPage,
  PostDetails,
  Profile,
} from "./_root/pages";
import MyUserReducer from "./reducer/MyReducer";
import { useEffect, useReducer } from "react";
import UserContext from "./context/UserContext";
import { Toaster } from "./components/ui/toaster";
import Cookie from "js-cookie";
import { transformToIUser } from "./lib/utils";
import { authApi, endpoints } from "./configs/APIs";
import { useToast } from "./components/ui/use-toast";
import { loginFirebase } from "./configs/firebase";
import Conversation from "./_root/pages/conversations/[id]";
import { PostsProvider, usePosts } from "./context/PostsContext";
import UpdateProfile from "./_root/pages/UpdateProfile";
import { BingMap, MapModal } from "./components/shared";

function App() {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  const navigate = useNavigate();
  const { toast } = useToast();

  const checkUserSession = async () => {
    let accessToken = Cookie.get("access_token");
    if (accessToken && !user) {
      try {
        let res = await authApi(accessToken).get(endpoints["current-user"]);
        const currentUser = res.data;

        let transUser = transformToIUser(currentUser);
        loginFirebase(transUser);

        dispatch({
          type: "login",
          payload: transUser,
        });
      } catch (err) {
        toast({
          title: "Session expired",
          description: "Your session has expired. Please log in again.",
        });
        Cookie.remove("access_token");
        dispatch({ type: "logout" });
        navigate("/sign-in");
      }
    }
  };
  useEffect(() => {
    checkUserSession();
  }, []);

  return (
    <UserContext.Provider value={{ user, dispatch }}>
      <PostsProvider>
        <main className="flex h-screen">
          <Routes>
            <Route element={<AuthLayout />}>
              <Route path="/sign-in" element={<SigninForm />} />
              <Route path="/sign-up" element={<SignupForm />} />
            </Route>

            <Route element={<RootLayout />}>
              <Route index element={<Home />} />
              <Route path="/profile/:id" element={<Profile />} />
              <Route
                path="/profile/:userId/update"
                element={<UpdateProfile />}
              />
              <Route path="/chat/" element={<ChatBox />} />
              <Route path="/conversations/:id" element={<Conversation />} />
              <Route path="/posts/:postId" element={<PostDetails />} />
              <Route path="/create-post" element={<CreatePost />} />
              <Route path="/explore" element={<MapModal />} />
              <Route path="*" element={<NoPage />} />
            </Route>
          </Routes>

          <Toaster />
        </main>
      </PostsProvider>
    </UserContext.Provider>
  );
}

export default App;
