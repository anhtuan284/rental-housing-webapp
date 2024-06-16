// import { useNavigate } from "react-router-dom";
// import { createContext, useContext, useEffect, useState } from "react";

// import { IUser } from "@/types";
// import APIs, { authApi, endpoints } from "@/configs/APIs";

// export const INITIAL_USER = {
//   id: "",
//   name: "",
//   username: "",
//   email: "",
//   avatar: "",
// };

// const INITIAL_STATE = {
//   user: INITIAL_USER,
//   isLoading: false,
//   isAuthenticated: false,
//   setUser: () => {},
//   setIsAuthenticated: () => {},
//   checkAuthUser: async () => false as boolean,
// };

// type IContextType = {
//   user: IUser;
//   isLoading: boolean;
//   setUser: React.Dispatch<React.SetStateAction<IUser>>;
//   isAuthenticated: boolean;
//   setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
//   checkAuthUser: () => Promise<boolean>;
// };

// const AuthContext = createContext<IContextType>(INITIAL_STATE);

// export function AuthProvider({ children }: { children: React.ReactNode }) {
//   const navigate = useNavigate();
//   const [user, setUser] = useState<IUser>(INITIAL_USER);
//   const [isAuthenticated, setIsAuthenticated] = useState(false);
//   const [isLoading, setIsLoading] = useState(false);

//   const getCurrentUser = async () => {
//     try {
//       let token = localStorage.getItem("access_token");
//       let res = await authApi(token).get(endpoints["current-user"]);
//     } catch (ex) {
//       console.log("On Catch!!");
//       if (ex.response && ex.response.status === 400) {
//         alert("Username or password is incorrect");
//       }
//     } finally {
//       setLoading(false);
//     }
//   };
//   const checkAuthUser = async () => {
//     setIsLoading(true);
//     try {
//       const currentAccount = await getCurrentUser();
//       if (currentAccount) {
//         setUser({
//           id: currentAccount.$id,
//           name: currentAccount.name,
//           username: currentAccount.username,
//           email: currentAccount.email,
//           avatar: currentAccount.avatar,
//         });
//         setIsAuthenticated(true);

//         return true;
//       }

//       return false;
//     } catch (error) {
//       console.error(error);
//       return false;
//     } finally {
//       setIsLoading(false);
//     }
//   };

//   useEffect(() => {
//     const cookieFallback = localStorage.getItem("cookieFallback");
//     if (
//       cookieFallback === "[]" ||
//       cookieFallback === null ||
//       cookieFallback === undefined
//     ) {
//       navigate("/sign-in");
//     }

//     checkAuthUser();
//   }, []);

//   const value = {
//     user,
//     setUser,
//     isLoading,
//     isAuthenticated,
//     setIsAuthenticated,
//     checkAuthUser,
//   };

//   return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
// }

// export const useUserContext = () => useContext(AuthContext);
