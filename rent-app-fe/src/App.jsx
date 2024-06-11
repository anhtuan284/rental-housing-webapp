import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home"; // Import the Home component
import PostForRent from "./pages/PostForRent";

const App = () => {
  return (
    <Router>
      <div>
        <h1>Welcome to My App</h1>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/post-for-rent" element={<PostForRent />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
