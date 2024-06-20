import React, { useState } from "react";
import { Link } from "react-router-dom";

type CommentProps = {
  comment: {
    userId: string;
    name: string;
    avatar: string;
    content: string;
    positive: boolean;
  };
};

const Comment = ({ comment }: CommentProps) => {
  const [visile, setVisible] = useState<boolean>(comment.positive);

  return (
    <div className="flex items-start gap-3 my-3">
      <Link to={`/profile/${comment.userId}`}>
        <img
          src={comment.avatar || "/assets/icons/profile-placeholder.svg"}
          alt="user avatar"
          className="w-8 h-8 rounded-full"
        />
      </Link>
      <div className="flex">
        <div className="bg-slate-900 rounded-lg p-3">
          <Link to={`/profile/${comment.userId}`}>
            <p className="comment-user-name font-extrabold text-primary-500">
              {comment.name}
            </p>
          </Link>
          {visile ? (
            <p className="comment-text">{comment.content}</p>
          ) : (
            <div className="flex align-start">
              <p className="text-italic text-gray-500 ">
                <i>Bình luận này đã bị ẩn do phát hiện mang tính tiêu cực</i>
              </p>
              <button
                className="btn btn-primary"
                onClick={() => setVisible(!visile)}
              >
                Xem
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Comment;
