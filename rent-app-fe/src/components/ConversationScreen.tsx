import React, { useContext, useEffect, useRef, useState } from "react";
import IconButton from "@mui/material/IconButton";
import styled from "styled-components";
import { useRecipient } from "../hooks/useRecipient";
import { Conversation, IMessage } from "../types";
import InsertEmoticonIcon from "@mui/icons-material/InsertEmoticon";
import RecipientAvatar from "./RecipientAvatar";
import AttachFileIcon from "@mui/icons-material/AttachFile";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import SendIcon from "@mui/icons-material/Send";
import MicIcon from "@mui/icons-material/Mic";
import {
  addDoc,
  collection,
  onSnapshot,
  serverTimestamp,
  setDoc,
  Query,
  DocumentData,
  doc,
} from "firebase/firestore";
import UserContext from "@/context/UserContext";
import {
  convertFirestoreTimestampToString,
  generateQueryGetMessages,
} from "@/lib/utils";
import Message from "./Message";
import { db } from "@/configs/firebase";

const StyledRecipientHeader = styled.div`
  position: sticky;
  z-index: 100;
  top: 0;
  display: flex;
  background-color: #444444;
  align-items: center;
  padding: 11px;
  height: 80px;
  border-bottom: 1px solid #46454596;
`;

const StyledHeaderInfo = styled.div`
  flex-grow: 1;

  > h3 {
    margin-top: 0;
    margin-bottom: 3px;
  }

  > span {
    font-size: 14px;
    color: gray;
  }
`;

const StyledH3 = styled.h3`
  word-break: break-all;
`;

const StyledHeaderIcons = styled.div`
  display: flex;
`;

const StyledMessageContainer = styled.div`
  margin-bottom: 0px;
  height: 84%; /* Calculate height minus header */
  overflow-y: auto;
`;

const StyledInputContainer = styled.form`
  display: flex;
  align-items: center;
  padding: 10px;
  position: sticky;
  bottom: 0;
  z-index: 100;
  background-color: #444444;
  border-top: 1px solid #46454596;
`;

const StyledInput = styled.input`
  flex-grow: 1;
  outline: none;
  border: none;
  border-radius: 10px;
  background-color: #f0f0f0;
  padding: 15px;
  margin-left: 15px;
  margin-right: 15px;
  color: #000000;
`;

const EndOfMessagesForAutoScroll = styled.div`
  margin-bottom: 30px;
`;

interface ConversationScreenProps {
  conversation: Conversation;
  messages: IMessage[];
  conversationId?: string;
}

const ConversationScreen: React.FC<ConversationScreenProps> = ({
  conversation,
  messages,
  conversationId,
}) => {
  const [newMessage, setNewMessage] = useState("");
  const [messagesList, setMessages] = useState<IMessage[]>([]); // State to store messages

  useEffect(() => {
    setMessages(messages);
  }, [messages]);

  const { user } = useContext(UserContext);

  const conversationUsers = conversation.users;

  const { recipientEmail, recipient } = useRecipient(conversationUsers);

  const endOfMessagesRef = useRef<HTMLDivElement>(null);

  // Function to send a new message
  const sendMessage = async () => {
    if (!newMessage.trim()) return;

    try {
      // Update user's last seen timestamp
      await setDoc(
        doc(db, "users", user?.email as string),
        {
          lastSeen: serverTimestamp(),
        },
        { merge: true }
      );

      // Check if conversationId is defined
      if (!conversationId) {
        console.error("conversationId is undefined");
        return;
      }

      // Add new message to the Firestore collection
      await addDoc(collection(db, "messages"), {
        conversation_id: conversationId,
        sent_at: serverTimestamp(),
        text: newMessage.trim(),
        user: user?.email,
      });

      // Clear the input field after sending the message
      setNewMessage("");
      scrollToBottom(); // Scroll to the bottom of the message container
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  // Event handler for sending message on Enter key press
  const sendMessageOnEnter: React.KeyboardEventHandler<HTMLInputElement> = (
    event
  ) => {
    if (event.key === "Enter") {
      event.preventDefault();
      sendMessage();
    }
  };

  // Event handler for sending message on Send button click
  const sendMessageOnClick: React.MouseEventHandler<HTMLButtonElement> = (
    event
  ) => {
    event.preventDefault();
    sendMessage();
  };

  // Function to scroll to the bottom of the message container
  const scrollToBottom = () => {
    endOfMessagesRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Scroll to bottom when messagesList updates
  useEffect(() => {
    scrollToBottom();
  }, [messagesList]);

  return (
    <>
      {/* Header */}
      <StyledRecipientHeader>
        <RecipientAvatar
          recipient={recipient}
          recipientEmail={recipientEmail}
        />
        <StyledHeaderInfo>
          <StyledH3>{recipientEmail}</StyledH3>
          {recipient && (
            <span>
              Last active:{" "}
              {convertFirestoreTimestampToString(recipient.lastSeen)}
            </span>
          )}
        </StyledHeaderInfo>
        <StyledHeaderIcons>
          <IconButton>
            <AttachFileIcon />
          </IconButton>
          <IconButton>
            <MoreVertIcon />
          </IconButton>
        </StyledHeaderIcons>
      </StyledRecipientHeader>

      {/* Message container */}
      <StyledMessageContainer>
        {messagesList.map((message) => (
          <Message key={message.id} message={message} />
        ))}
        <EndOfMessagesForAutoScroll ref={endOfMessagesRef} />
      </StyledMessageContainer>

      {/* Input area for sending new messages */}
      <StyledInputContainer onSubmit={(e) => e.preventDefault()}>
        <InsertEmoticonIcon />
        <StyledInput
          value={newMessage}
          onChange={(event) => setNewMessage(event.target.value)}
          onKeyDown={sendMessageOnEnter}
          placeholder="Type a message..."
        />
        <IconButton onClick={sendMessageOnClick} disabled={!newMessage.trim()}>
          <SendIcon />
        </IconButton>
        <IconButton>
          <MicIcon />
        </IconButton>
      </StyledInputContainer>
    </>
  );
};

export default ConversationScreen;
