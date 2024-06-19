import React from "react";
import { Link } from "react-router-dom";

type CommentProps = {
  comment: {
    userId: string;
    name: string;
    avatar: string;
    content: string;
  };
};

const Comment = ({ comment }: CommentProps) => {
  return (
    <div className="comment">
      <Link to={`/profile/${comment.userId}`}>
        <img
          src={comment.avatar || "/assets/icons/profile-placeholder.svg"}
          alt="user avatar"
          className="w-8 h-8 rounded-full"
        />
      </Link>
      <div className="comment-content">
        <Link to={`/profile/${comment.userId}`}>
          <p className="comment-user-name">{comment.name}</p>
        </Link>
        <p className="comment-text">{comment.content}</p>
      </div>
    </div>
  );
};

export default Comment;
