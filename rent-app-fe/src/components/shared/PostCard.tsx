// import UserContext from "@/context/UserContext";
// import { multiFormatDateString } from "@/lib/utils";
// import { IPost } from "@/types";
// import { useContext } from "react";
// import { Link } from "react-router-dom";

// type PostCardProps = {
//   post: IPost;
// };

// const PostCard = ({ post }: PostCardProps) => {
//   const { user } = useContext(UserContext);

//   if (!post.creator) return;

//   return (
//     <div className="post-card">
//       <div className="flex-between">
//         <div className="flex items-center gap-3">
//           <Link to={`/profile/${post.creator.$id}`}>
//             <img
//               src={
//                 post.creator?.avatar || "/assets/icons/profile-placeholder.svg"
//               }
//               alt="creator"
//               className="w-12 lg:h-12 rounded-full"
//             />
//           </Link>

//           <div className="flex flex-col">
//             <p className="base-medium lg:body-bold text-light-1">
//               {post.creator.name}
//             </p>
//             <div className="flex-center gap-2 text-light-3">
//               <p className="subtle-semibold lg:small-regular ">
//                 {multiFormatDateString(post.created_date)}
//               </p>
//               •
//               <p className="subtle-semibold lg:small-regular">
//                 {post.location}
//               </p>
//             </div>
//           </div>
//         </div>

//         <Link
//           to={`/update-post/${post.postId}`}
//           className={`${user?.id !== post.creator.id && "hidden"}`}
//         >
//           <img
//             src={"/assets/icons/edit.svg"}
//             alt="edit"
//             width={20}
//             height={20}
//           />
//         </Link>
//       </div>

//       <Link to={`/posts/${post.postId}`}>
//         <div className="small-medium lg:base-medium py-5">
//           <p>{post.caption}</p>
//           <ul className="flex gap-1 mt-2">
//             {post.tags.map((tag: string, index: string) => (
//               <li key={`${tag}${index}`} className="text-light-3 small-regular">
//                 #{tag}
//               </li>
//             ))}
//           </ul>
//         </div>

//         <img
//           src={post.imageUrl || "/assets/icons/profile-placeholder.svg"}
//           alt="post image"
//           className="post-card_img"
//         />
//       </Link>

//       <PostStats post={post} userId={user.id} />
//     </div>
//   );
// };

// export default PostCard;
