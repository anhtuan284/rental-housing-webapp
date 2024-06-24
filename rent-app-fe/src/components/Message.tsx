import React, { useContext } from "react";
import styled from "styled-components";
import { IMessage } from "../types";
import UserContext from "@/context/UserContext";

// Styled component for a message
const StyledMessage = styled.div`
  width: fit-content;
  word-break: break-all;
  max-width: 90%;
  min-width: 30%;
  padding: 15px 15px 30px;
  border-radius: 8px;
  margin: 10px;
  position: relative;
  display: flex;
  flex-direction: column;
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

// Styled component for image container in flex format
const ImageContainer = styled.div`
  max-width: 500px;
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin-top: 10px;
`;

// Styled component for individual image
const SingleImage = styled.img`
  width: 500px;
  height: auto;
  border-radius: 8px;
`;

const MultipleImages = styled.img`
  width: calc(250px - 5px);
  height: auto;
  border-radius: 8px;
  object-fit: cover;
`;

// Function to format the timestamp to a readable string
const formatTimestamp = (timestamp: string): string => {
  const date = new Date(timestamp);
  return date.toLocaleString();
};

// Message component to display individual messages
const Message: React.FC<{ message: IMessage }> = ({ message }) => {
  const { user } = useContext(UserContext); // Get current user from context

  // Determine the message type based on the sender (current user or other user)
  const MessageType =
    user?.email === message.user ? StyledSenderMessage : StyledReceiverMessage;

  return (
    <MessageType>
      {message.text}
      {message.img && message.img.length > 0 && (
        <ImageContainer>
          {message.img.length === 1 ? (
            <SingleImage src={message.img[0]} alt="Message Attachment" />
          ) : (
            message.img.map((url, index) => (
              <MultipleImages
                key={index}
                src={url}
                alt={`Message Attachment ${index + 1}`}
              />
            ))
          )}
        </ImageContainer>
      )}
      <StyledTimestamp>{formatTimestamp(message.sent_at)}</StyledTimestamp>
    </MessageType>
  );
};

export default Message;
