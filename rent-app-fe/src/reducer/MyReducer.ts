import { IUser } from "@/types";

// Define the possible actions
export type UserAction =
  | { type: "login"; payload: IUser }
  | { type: "logout" };

// Define the reducer function with types
const MyUserReducer = (currentState: IUser | null, action: UserAction): IUser | null => {
  switch (action.type) {
    case "login":
      return action.payload;
    case "logout":
      return null;
    default:
      return currentState;
  }
};

export default MyUserReducer;
