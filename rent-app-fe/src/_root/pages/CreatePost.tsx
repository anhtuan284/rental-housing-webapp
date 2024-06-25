import PostForm from "@/components/forms/PostForm";
import RenterPostForm from "@/components/forms/RenterPostForm";
import UserContext from "@/context/UserContext";
import { useContext } from "react";

const CreatePost = () => {
  const { user } = useContext(UserContext);
  console.log(user?.role);
  return (
    <div className="flex flex-1">
      <div className="common-container">
        <div className="max-w-5xl flex-start gap-3 justify-start w-full">
          <img
            src="/assets/icons/add-post.svg"
            width={36}
            height={36}
            alt="add"
          />
          <h2 className="h3-bold md:h2-bold text-left w-full">Create Post</h2>
        </div>

        {user &&
        (user.role === "ROLE_ADMIN" || user.role === "ROLE_LANDLORD") ? (
          <PostForm action="Create" />
        ) : (
          <RenterPostForm action="Create" />
        )}
      </div>
    </div>
  );
};

export default CreatePost;
