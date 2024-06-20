import { SearchIcon } from "lucide-react";
import styled from "styled-components";
import IconButton from "@mui/material/IconButton";
import Button from "@mui/material/Button";
import * as EmailValidator from "email-validator";
import { useCollection } from "react-firebase-hooks/firestore";

import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from "@mui/material";
import { useContext, useState } from "react";
import UserContext from "@/context/UserContext";
import { addDoc, collection, query, where } from "firebase/firestore";
import { db } from "@/configs/firebase";
import { Conversation } from "@/types";
import Cookies from "js-cookie";
import { AxiosResponse } from "axios";
import { authApi, endpoints } from "@/configs/APIs";
import ConversationSelect from "../ConversationSelect";

const StyleContainer = styled.div`
  height: 100vh;
  min-width: 350px;
  max-width: 400px;
  border-right: 1px solid #46454596;
  border-left: 1px solid #46454596;
`;
const StyleSearch = styled.div`
  display: flex;
  align-items: center;
  padding: 15px;
  border-radius: 2px;
`;
const StyledSearchInput = styled.input`
  outline: none;
  border: 2px solid #796fff;
  border-radius: 8px;
  padding: 5px 10px;
  margin: 10px;
  flex: 1;
  font-size: 16px;
  color: black;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;

  &:focus {
    border-color: #21085a68;
    box-shadow: 0 0 5px rgba(81, 52, 100, 0.5);
  }

  &::placeholder {
    color: #888;
  }
`;

// const StyleSidebarButton =styled.button`

// `

const StyleSidebarButton = styled(Button)`
  width: 100%;
  border-bottom: 0.5px solid #46454596;
  border-top: 0.5px solid #46454596;
`;

const SidebarChat = () => {
  const { user } = useContext(UserContext);
  const [isOpenNewConversationDialog, setIsOpenNewConversationDialog] =
    useState(false);
  const [recipientUser, setRecipientUser] = useState("");
  const inviteSelf = recipientUser === user?.email;

  // get all conversations of currentuser ok
  const queryGetConversationsForCurrentUser = user?.id
    ? query(
        collection(db, "conversations"),
        where("users", "array-contains", user.email)
      )
    : null;

  // sent then get data
  const [conversationsSnapshot] = useCollection(
    queryGetConversationsForCurrentUser
  );
  let token = Cookies.get("access_token");

  const isConversationAlreadyExists = (recipientUser: string) =>
    conversationsSnapshot?.docs.find((conversation) =>
      (conversation.data() as Conversation).users.includes(recipientUser)
    );

  const checkUserInApp = async (recipientUser: string) => {
    try {
      if (token) {
        const userRes: AxiosResponse = await authApi(token).post(
          endpoints["check-user-by-email"],
          {
            userEmail: recipientUser,
          }
        );

        if (userRes.status === 200) {
          return true;
        } else if (userRes.status === 201) {
          return false;
        }
      }
    } catch (error) {
      console.error("Error checking user:", error);
      return false;
    }
  };
  const toggleNewConversationDialog = (isOpen: boolean) => {
    setIsOpenNewConversationDialog(isOpen);
    if (!isOpen) setRecipientUser("");
  };
  const closeNewConversationDialog = () => {
    toggleNewConversationDialog(false);
  };
  const createConversation = async () => {
    if (!recipientUser) return;

    if (
      EmailValidator.validate(recipientUser) &&
      !inviteSelf &&
      !isConversationAlreadyExists(recipientUser) &&
      (await checkUserInApp(recipientUser))
    ) {
      await addDoc(collection(db, "conversations"), {
        users: [user?.email, recipientUser],
      });
    }

    closeNewConversationDialog();
  };

  return (
    <StyleContainer>
      <StyleSearch>
        <SearchIcon></SearchIcon>
        <StyledSearchInput placeholder="Nhập đi" />
      </StyleSearch>
      <StyleSidebarButton
        onClick={() => {
          toggleNewConversationDialog(true);
        }}
      >
        Start a new conversation
      </StyleSidebarButton>

      {/* List chat */}
      {conversationsSnapshot?.docs.map((conversation) => (
        <ConversationSelect
          key={conversation.id}
          id={conversation.id}
          conversationUsers={(conversation.data() as Conversation).users}
        />
      ))}

      <Dialog
        open={isOpenNewConversationDialog}
        onClose={closeNewConversationDialog}
      >
        <DialogTitle>New Conversation</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Please enter a user's name for the user you wish to chat with
          </DialogContentText>
          <TextField
            autoFocus
            label="User's Email"
            type="email"
            fullWidth
            variant="standard"
            value={recipientUser}
            onChange={(event) => {
              setRecipientUser(event.target.value);
            }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={closeNewConversationDialog}>Cancel</Button>
          <Button disabled={!recipientUser} onClick={createConversation}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </StyleContainer>
  );
};

export default SidebarChat;
