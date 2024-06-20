import { db } from "@/configs/firebase";
import UserContext from "@/context/UserContext";
import { getRecipientEmail } from "@/lib/utils";
import { ChatUser, Conversation } from "@/types";
import { collection, query, where } from "firebase/firestore";
import { useContext } from "react";
import { useCollection } from "react-firebase-hooks/firestore";

export const useRecipient = (conversationUsers: Conversation["users"]) => {
  const { user } = useContext(UserContext);
  const recipientEmail = getRecipientEmail(conversationUsers, user);
  const queryGetRecipient = query(
    collection(db, "users"),
    where("email", "==", recipientEmail)
  );
  const [recipientsSnapshot, __loading, __error] =
    useCollection(queryGetRecipient);
  const recipient = recipientsSnapshot?.docs[0]?.data() as ChatUser | undefined;

  return {
    recipient,
    recipientEmail,
  };
};
