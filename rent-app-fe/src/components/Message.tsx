import React, { useContext } from "react";
import styled from "styled-components";
import { IMessage } from "../types";
import UserContext from "@/context/UserContext";
import { Timestamp } from "firebase/firestore";

// Styled component for a message
const StyledMessage = styled.p`
  width: fit-content;
  word-break: break-all;
  max-width: 90%;
  min-width: 30%;
  padding: 15px 15px 30px;
  border-radius: 8px;
  margin: 10px;
  position: relative;
`;

// Styled component for a sender's message
const StyledSenderMessage = styled(StyledMessage)`
  margin-left: auto;
  background-color: #2a296b;
`;

// Styled component for a receiver's message
const StyledReceiverMessage = styled(StyledMessage)`
  background-color: #46454596;
`;

// Styled component for displaying the timestamp
const StyledTimestamp = styled.span`
  color: gray;
  padding: 10px;
  font-size: x-small;
  position: absolute;
  bottom: 0;
  right: 0;
  text-align: right;
`;

// Message component to display individual messages
const Message: React.FC<{ message: IMessage }> = ({ message }) => {
  const { user } = useContext(UserContext); // Get current user from context

  // Determine the message type based on the sender (current user or other user)
  const MessageType =
    user?.email === message.user ? StyledSenderMessage : StyledReceiverMessage;

  // Format the timestamp to a readable string

  return (
    <MessageType>
      {message.text}
      <StyledTimestamp>{message.sent_at}</StyledTimestamp>
    </MessageType>
  );
};

export default Message;
