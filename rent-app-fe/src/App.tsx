import { Route, Routes, useNavigate } from "react-router-dom";
import "./globals.css";
import AuthLayout from "./_auth/AuthLayout";
import SigninForm from "./_auth/forms/SigninForm";
import SignupForm from "./_auth/forms/SignupForm";
import RootLayout from "./_root/RootLayout";
import { Home, NoPage } from "./_root/pages";
import MyUserReducer from "./reducer/MyReducer";
import { useEffect, useReducer } from "react";
import UserContext from "./context/UserContext";
import { Toaster } from "./components/ui/toaster";
import Cookie from "js-cookie";
import { transformToIUser } from "./lib/utils";
import { authApi, endpoints } from "./configs/APIs";
import { useToast } from "./components/ui/use-toast";

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
      <main className="flex h-screen">
        <Routes>
          <Route element={<AuthLayout />}>
            <Route path="/sign-in" element={<SigninForm />} />
            <Route path="/sign-up" element={<SignupForm />} />
          </Route>

          <Route element={<RootLayout />}>
            <Route index element={<Home />} />
            <Route path="*" element={<NoPage />} />
          </Route>
        </Routes>

        <Toaster />
      </main>
    </UserContext.Provider>
  );
}

export default App;
