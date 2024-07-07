import {
  Route,
  Routes,
  Link,
  Outlet,
  useParams,
  useLocation,
  useNavigate,
} from "react-router-dom";

import { Button, useToast } from "@/components/ui";
import { useContext, useEffect, useState } from "react";
import UserContext from "@/context/UserContext";
import { Loader } from "@/components/shared";
import { AxiosResponse } from "axios";
import APIs, { authApi, endpoints } from "@/configs/APIs";
import { Conversation, IUser } from "@/types";
import { transformToIUser } from "@/lib/utils";
import Cookies from "js-cookie";
import { addDoc, collection, query, where } from "firebase/firestore";
import { db } from "@/configs/firebase";
import { useCollection } from "react-firebase-hooks/firestore";

interface StabBlockProps {
  value: string | number;
  label: string;
}

const StatBlock = ({ value, label }: StabBlockProps) => (
  <div className="flex-center gap-2">
    <p className="small-semibold lg:body-bold text-primary-500">{value}</p>
    <p className="small-medium lg:base-medium text-light-2">{label}</p>
  </div>
);

const Profile = () => {
  const { id } = useParams();
  const { user } = useContext(UserContext);
  const { pathname } = useLocation();
  const [currentUser, setCurrentUser] = useState<IUser>();
  const [isFollowed, setIsFollowed] = useState<boolean>(false);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  const { toast } = useToast();

  //get all conversations then check already ...
  const queryGetConversationsForCurrentUser = user?.id
    ? query(
        collection(db, "conversations"),
        where("users", "array-contains", user.email)
      )
    : null;
  const [conversationsSnapshot] = useCollection(
    queryGetConversationsForCurrentUser
  );

  const ConversationAlreadyExists = (recipientUser: string) => {
    if (!conversationsSnapshot) return null;

    const existingConversation = conversationsSnapshot.docs.find(
      (conversation) =>
        (conversation.data() as Conversation).users.includes(recipientUser)
    );

    return existingConversation
      ? { id: existingConversation.id, ...existingConversation.data() }
      : null;
  };

  const fetchProfile = async (token: string) => {
    if (token) {
      let profileRes: AxiosResponse = await authApi(token).get(
        endpoints["profile"](id)
      );
      if (profileRes.status === 200) {
        let transformUser = transformToIUser(profileRes.data);
        setCurrentUser({ ...transformUser });
      }
    } else {
      toast({
        title: "Failed to fetch this profile",
        description: "Please try again later.",
      });
    }
  };

  const checkFollow = async (token: string) => {
    if (token) {
      let followRes: AxiosResponse = await authApi(token).post(
        endpoints["check-follow"],
        {
          userId: id,
        }
      );
      if (followRes.status === 200) {
        if (followRes.data === true) {
          setIsFollowed(true);
        }
      }
    }
  };

  const unfollow = async () => {
    setIsFollowed(false);
    try {
      let token = Cookies.get("access_token");
      if (token) {
        let unfollowRes: AxiosResponse = await authApi(token).post(
          endpoints["unfollow"],
          {
            followee: id,
          }
        );
        if (unfollowRes.status === 200) {
          toast({
            title: "Unfollowed successfully",
            description: "You are no longer following this user.",
          });
        }
      } else {
        setIsFollowed(true);
        toast({
          title: "Failed to unfollow",
          description: "Please try again later.",
        });
      }
    } catch (err) {
      setIsFollowed(true);
      toast({
        title: "Failed to unfollow",
        description: "Please try again later.",
      });
    }
  };

  const follow = async () => {
    setIsFollowed(true);
    try {
      let token = Cookies.get("access_token");
      if (token) {
        let followRes: AxiosResponse = await authApi(token).post(
          endpoints["follow"],
          {
            followee: currentUser?.id,
          }
        );
        if (followRes.status === 200) {
          toast({
            title: "Followed successfully",
            description: "You are now following this user.",
          });
        }
      } else {
        setIsFollowed(false);
        toast({
          title: "Failed to follow",
          description: "Please try again later.",
        });
      }
    } catch (err) {
      setIsFollowed(false);
      toast({
        title: "Failed to follow",
        description: "Please try again later.",
      });
    }
  };
  const createConversation = async () => {
    setLoading(false); // Set loading to true when starting conversation creation

    await addDoc(collection(db, "conversations"), {
      users: [user?.email, currentUser?.email],
    });
  };
  const goToConversation = async () => {
    if (!currentUser?.email) {
      return toast({
        title: "Failed to start conversation",
        description: "Please try again later.",
      });
    }
    const conversation = ConversationAlreadyExists(currentUser?.email);
    if (!conversation) {
      await createConversation();
      setTimeout(() => {
        setLoading(false); // Set loading to false after simulated delay
      }, 2000); // Adjust the timeout as needed
    } else {
      navigate(`/conversations/${conversation.id}`);
    }
  };

  useEffect(() => {
    setCurrentUser(undefined);
    if (user?.id != id) {
      let token = Cookies.get("access_token");
      if (!token) return;
      checkFollow(token);
      fetchProfile(token);
    } else {
      if (user) {
        setCurrentUser(user);
      }
    }
  }, [id]);

  if (!currentUser)
    return (
      <div className="flex-center w-full h-full">
        <Loader />
      </div>
    );
  if (!loading) {
    if (!currentUser?.email) {
      console.log(31231231);
      const newConversation = ConversationAlreadyExists(currentUser?.email);
      if (newConversation) {
        navigate(`/conversations/${newConversation.id}`);
      }
    }
  }
  return (
    <div className="profile-container">
      <div className="profile-inner_container">
        <div className="flex xl:flex-row flex-col max-xl:items-center flex-1 gap-7">
          <img
            src={currentUser.avatar || "/assets/icons/profile-placeholder.svg"}
            alt="profile"
            className="w-28 h-28 lg:h-36 lg:w-36 rounded-full"
          />
          <div className="flex flex-col flex-1 justify-between md:mt-2">
            <div className="flex flex-col w-full">
              <h1 className="text-center xl:text-left h3-bold md:h1-semibold w-full">
                {currentUser.name}
              </h1>
              <p className="small-regular md:body-medium text-light-3 text-center xl:text-left">
                @{currentUser.username}
              </p>
            </div>

            <div className="flex gap-8 mt-10 items-center justify-center xl:justify-start flex-wrap z-20">
              <StatBlock value={15} label="Posts" />
              <StatBlock
                value={currentUser.followers?.length}
                label="Followers"
              />
              <StatBlock
                value={currentUser.following?.length}
                label="Following"
              />
            </div>

            <p className="small-medium md:base-medium text-center xl:text-left mt-7 max-w-screen-sm">
              {/* {currentUser.bio} */}
              This is userbio
            </p>
          </div>

          <div className="flex justify-center gap-4">
            <div className={`${user?.id !== currentUser?.id && "hidden"}`}>
              <Link
                to={`/profile/${currentUser.id}/update`}
                className={`h-12 bg-dark-4 px-5 text-light-1 flex-center gap-2 rounded-lg ${
                  user?.id !== currentUser.id && "hidden"
                }`}
              >
                <img
                  src={"/assets/icons/edit.svg"}
                  alt="edit"
                  width={20}
                  height={20}
                />
                <p className="flex whitespace-nowrap small-medium">
                  Edit Profile
                </p>
              </Link>
            </div>
            <div className={`${user?.id === currentUser?.id && "hidden"}`}>
              <div className="flex gap-1">
                {isFollowed ? (
                  <Button
                    type="button"
                    className="shad-button_dark_4"
                    onClick={unfollow}
                  >
                    Unfollow
                  </Button>
                ) : (
                  <Button
                    type="button"
                    className="shad-button_primary"
                    onClick={follow}
                  >
                    Follow
                  </Button>
                )}
                <Button
                  type="button"
                  className="shad-button_ghost hover:scale-110 transition-all ease-in-out"
                  onClick={goToConversation}
                >
                  <img
                    src={"/assets/icons/chat.svg"}
                    alt="chat-icon"
                    width={25}
                    height={25}
                  />
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {currentUser.id === user?.id && (
        <div className="flex max-w-5xl w-full">
          <Link
            to={`/profile/${id}`}
            className={`profile-tab rounded-l-lg ${
              pathname === `/profile/${id}` && "!bg-dark-3"
            }`}
          >
            <img
              src={"/assets/icons/posts.svg"}
              alt="posts"
              width={20}
              height={20}
            />
            Posts
          </Link>
          <Link
            to={`/profile/${id}/liked-posts`}
            className={`profile-tab rounded-r-lg ${
              pathname === `/profile/${id}/liked-posts` && "!bg-dark-3"
            }`}
          >
            <img
              src={"/assets/icons/like.svg"}
              alt="like"
              width={20}
              height={20}
            />
            Liked Posts
          </Link>
        </div>
      )}

      {/* <Routes>
        <Route
          index
          element={<GridPostList posts={currentUser.posts} showUser={false} />}
        />
        {currentUser.$id === user.id && (
          <Route path="/liked-posts" element={<LikedPosts />} />
        )}
      </Routes> */}
      <Outlet />
    </div>
  );
};

export default Profile;
