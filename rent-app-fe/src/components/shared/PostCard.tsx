import UserContext from "@/context/UserContext";
import { multiFormatDateString } from "@/lib/utils";
import { IPost } from "@/types";
import { useContext } from "react";
import { Link } from "react-router-dom";
import ReportBadge from "../ui/report";
import styled from "styled-components";

type PostCardProps = {
  post: IPost;
};

const ReportBadgeWrapper = styled.div``;
const PostCard = ({ post }: PostCardProps) => {
  const { user } = useContext(UserContext);

  if (!post.user) return;
  const reportCount = post.report ? post.report.length : 0;

  return (
    <div className="post-card">
      <div className="flex-between">
        <div className="flex items-center gap-3">
          <Link to={`/profile/${post.user.userId}`}>
            <img
              src={post.user.avatar || "/assets/icons/profile-placeholder.svg"}
              alt="creator"
              className="w-12 lg:h-12 rounded-full"
            />
          </Link>
          <div className="flex flex-col">
            <Link to={`/profile/${post.user.userId}`}>
              <p className="base-medium lg:body-bold text-light-1">
                {post.user.name}
              </p>
            </Link>
            <div className="flex-center gap-2 text-light-3 ">
              <p className="subtle-semibold lg:small-regular ">
                <i>{multiFormatDateString(post.created_date)}</i>
              </p>
              •
              <p className="subtle-semibold lg:small-regular text-primary-500">
                <strong>{post.location?.city}</strong>
              </p>
            </div>
          </div>
        </div>
        <ReportBadgeWrapper className="flex align-center">
          {reportCount > 0 && <ReportBadge reportCount={reportCount} />}{" "}
          <Link
            to={`/update-post/${post.postId}`}
            className={`${user?.id.toString() != post.user.userId && "hidden"}`}
          >
            <img
              src={"/assets/icons/edit.svg"}
              alt="edit"
              width={35}
              height={35}
            />
          </Link>
        </ReportBadgeWrapper>
      </div>

      <Link to={`/posts/${post.postId}`}>
        <div className="small-medium lg:base-medium py-5">
          <p>{post.title}</p>
          <ul className="flex gap-1 mt-2">
            <li className="text-light-3 small-regular">{post.description}</li>
          </ul>
          <div className="my-1 text-purple-400 text-lg">
            {post.propertyDetail.price
              .toString()
              .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}{" "}
            / Tháng
          </div>
        </div>

        <img
          src={post.imageSet[0]?.url || "/assets/icons/profile-placeholder.svg"}
          alt="post image"
          className="post-card_img"
        />
      </Link>

      {/* <PostStats post={post} userId={user.id} /> */}
    </div>
  );
};

export default PostCard;
