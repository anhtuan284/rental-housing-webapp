// pages/PostForRent.jsx
import React from "react";
import { Link } from "react-router-dom";

const PostForRent = () => {
  return (
    <div>
      <h1>Post For Rent Page</h1>
      <Link to="/">
        <button>Go to Home</button>
      </Link>
      <p>This is the Post For Rent page.</p>
    </div>
  );
};

export default PostForRent;
