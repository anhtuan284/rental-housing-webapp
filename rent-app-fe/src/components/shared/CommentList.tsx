import React from "react";
import Comment from "./Comment";

const CommentList = ({ postId }: { postId: string }) => {
  const comments = [
    { userId: "1", name: "User One", avatar: "", content: "Great post!" },
    {
      userId: "2",
      name: "User Two",
      avatar: "",
      content: "Thanks for sharing!",
    },
  ];

  return (
    <div className="comment-list">
      {comments.map((comment) => (
        <Comment key={comment.userId} comment={comment} />
      ))}
    </div>
  );
};

export default CommentList;
