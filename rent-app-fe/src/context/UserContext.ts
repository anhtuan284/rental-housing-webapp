import { UserAction } from "@/reducer/MyReducer";
import { IUser } from "@/types";
import { createContext, Dispatch } from "react";

// Define the shape of the context value
interface UserContextType {
  user: IUser | null;
  dispatch: Dispatch<UserAction>;
}

const defaultUserContext: UserContextType = {
  user: null,
  dispatch: () => {},
};

const UserContext = createContext<UserContextType>(defaultUserContext);

export default UserContext;
