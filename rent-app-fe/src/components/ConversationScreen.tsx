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
import DeleteIcon from "@mui/icons-material/Delete";
import AddToPhotosIcon from "@mui/icons-material/AddToPhotos";
import {
  addDoc,
  collection,
  doc,
  serverTimestamp,
  setDoc,
} from "firebase/firestore";
import UserContext from "@/context/UserContext";
import {
  convertFirestoreTimestampToString,
  generateQueryGetMessages,
} from "@/lib/utils";
import Message from "./Message";
import { db, storage } from "@/configs/firebase";
import { getDownloadURL, ref, uploadBytes } from "firebase/storage";

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
  height: 84%;
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

const PreviewImagesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
`;

const PreviewImage = styled.img`
  max-height: 150px;
  max-width: 150px;
  border-radius: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
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
  const [messagesList, setMessagesList] = useState<IMessage[]>([]);
  const [tempImageUrl, setTempImageUrl] = useState<string[]>([]);
  const [tempImageFiles, setTempImageFiles] = useState<File[]>([]);

  const { user } = useContext(UserContext);
  const { recipientEmail, recipient } = useRecipient(conversation.users);

  const endOfMessagesRef = useRef<HTMLDivElement>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setMessagesList(messages);
    scrollToBottom();
  }, [messages]);

  useEffect(() => {
    scrollToBottom();
  }, [messagesList]);

  const handleFileInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const files = event.target.files;
    if (files) {
      const newImageFiles = Array.from(files);
      const newImageUrls = newImageFiles.map((file) =>
        URL.createObjectURL(file)
      );
      setTempImageFiles((prevFiles) => [...prevFiles, ...newImageFiles]);
      setTempImageUrl((prevUrls) => [...prevUrls, ...newImageUrls]);
    }
  };

  const clearImagePreview = () => {
    setTempImageUrl([]);
    setTempImageFiles([]);
    if (fileInputRef.current) {
      fileInputRef.current.value = ""; // Clear the file input
    }
  };

  const handleImageUpload = async (imageFile: File) => {
    try {
      const storageRef = ref(storage, `images/${imageFile.name}`);
      await uploadBytes(storageRef, imageFile);
      const imageUrl = await getDownloadURL(storageRef);
      return imageUrl;
    } catch (error) {
      console.error("Error uploading image:", error);
      throw error;
    }
  };

  const deleteImage = (index: number) => {
    const newImageUrlList = [...tempImageUrl];
    const newImageFilesList = [...tempImageFiles];

    newImageUrlList.splice(index, 1);
    newImageFilesList.splice(index, 1);

    setTempImageUrl(newImageUrlList);
    setTempImageFiles(newImageFilesList);
  };

  const sendMessage = async () => {
    if (!newMessage.trim() && tempImageFiles.length === 0) return;

    try {
      await setDoc(
        doc(db, "users", user?.email as string),
        {
          lastSeen: serverTimestamp(),
        },
        { merge: true }
      );

      if (!conversationId) {
        console.error("conversationId is undefined");
        return;
      }

      let messageData: any = {
        conversation_id: conversationId,
        sent_at: serverTimestamp(),
        text: newMessage.trim(),
        user: user?.email,
      };

      if (tempImageFiles.length > 0) {
        const imageUrls = await Promise.all(
          tempImageFiles.map(handleImageUpload)
        );
        messageData = {
          ...messageData,
          img: imageUrls,
        };
        clearImagePreview();
      }

      await addDoc(collection(db, "messages"), messageData);
      setNewMessage("");
      scrollToBottom();
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  const sendMessageOnEnter: React.KeyboardEventHandler<HTMLInputElement> = (
    event
  ) => {
    if (event.key === "Enter") {
      event.preventDefault();
      sendMessage();
    }
  };

  const sendMessageOnClick: React.MouseEventHandler<HTMLButtonElement> = (
    event
  ) => {
    event.preventDefault();
    sendMessage();
  };

  const scrollToBottom = () => {
    endOfMessagesRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  return (
    <>
      <StyledRecipientHeader>
        <RecipientAvatar
          recipient={recipient}
          recipientEmail={recipientEmail}
        />
        <StyledHeaderInfo>
          <StyledH3>{recipient?.name}</StyledH3>
          {recipient && (
            <span>
              Last active:{" "}
              {convertFirestoreTimestampToString(recipient.lastSeen)}
            </span>
          )}
        </StyledHeaderInfo>
        <StyledHeaderIcons>
          <IconButton onClick={() => {}}>
            <MoreVertIcon />
          </IconButton>
        </StyledHeaderIcons>
      </StyledRecipientHeader>

      <StyledMessageContainer>
        {messagesList.map((message) => (
          <Message key={message.id} message={message} />
        ))}
        <EndOfMessagesForAutoScroll ref={endOfMessagesRef} />
      </StyledMessageContainer>

      {tempImageUrl.length > 0 && (
        <PreviewImagesContainer>
          <IconButton>
            <AddToPhotosIcon
              onClick={() => fileInputRef.current?.click()}
              style={{
                width: "50px",
                height: "50px",
                position: "relative",
                top: 0,
                right: 0,
                color: "rgba(255,255,255)",
                zIndex: 1,
              }}
            />
          </IconButton>
          {tempImageUrl.map((url, index) => (
            <div key={index} style={{ position: "relative" }}>
              <PreviewImage src={url} alt="Preview" />
              <IconButton
                style={{
                  position: "absolute",
                  top: 0,
                  right: 0,
                  backgroundColor: "rgba(255, 255, 255, 0.5)",
                }}
                onClick={() => deleteImage(index)}
              >
                <DeleteIcon />
              </IconButton>
            </div>
          ))}
        </PreviewImagesContainer>
      )}

      <StyledInputContainer onSubmit={(e) => e.preventDefault()}>
        <IconButton onClick={() => fileInputRef.current?.click()}>
          <AttachFileIcon />
          <input
            type="file"
            accept="image/*"
            ref={fileInputRef}
            style={{ display: "none" }}
            onChange={handleFileInputChange}
            multiple
          />
        </IconButton>
        <StyledInput
          value={newMessage}
          onChange={(event) => setNewMessage(event.target.value)}
          onKeyDown={sendMessageOnEnter}
          placeholder="Type a message..."
        />
        <IconButton
          onClick={sendMessageOnClick}
          disabled={!newMessage.trim() && tempImageFiles.length === 0}
        >
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
