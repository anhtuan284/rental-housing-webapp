import React from "react";
import { createRoot } from "react-dom/client"; // Import from react-dom/client
import App from "./App";

const root = document.getElementById("root");

// Use createRoot instead of render
createRoot(root).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
