import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import SidebarChat from "@/components/shared/SidebarChat";
import { db } from "@/configs/firebase";
import {
  doc,
  getDoc,
  collection,
  query,
  where,
  orderBy,
  onSnapshot,
} from "firebase/firestore";
import { Conversation, IMessage } from "@/types";
import ConversationScreen from "@/components/ConversationScreen";
import { transformMessage } from "@/lib/utils";
import Spinner from "@/components/ui/Spinner";

const StyledContainer = styled.div`
  display: flex;
  height: 100vh;
  overflow-x: none;
  overflow-y: none;
`;

const SidebarChatContainer = styled.div``;

const StyledConversationContainer = styled.div`
  flex: 1;
  overflow-x: none;
  overflow-y: none;
  height: 100vh;
  width: 67.5vw;
  ::-webkit-scrollbar {
    display: none;
  }
  -ms-overflow-style: none;
  scrollbar-width: none;
`;
const SpinnerContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
`;
const ConversationComponent: React.FC = () => {
  const { id: conversationId } = useParams<{ id: string }>();
  const [conversation, setConversation] = useState<Conversation | null>(null);
  const [messages, setMessages] = useState<IMessage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchConversationAndMessages = async () => {
      if (!conversationId) return;

      try {
        setLoading(true);

        const conversationRef = doc(db, "conversations", conversationId);
        const conversationSnapshot = await getDoc(conversationRef);

        if (!conversationSnapshot.exists()) {
          throw new Error("Không tìm thấy cuộc trò chuyện");
        }

        const conversationData = conversationSnapshot.data() as Conversation;
        setConversation(conversationData);

        const messagesQuery = query(
          collection(db, "messages"),
          where("conversation_id", "==", conversationId),
          orderBy("sent_at", "asc")
        );

        const unsubscribe = onSnapshot(messagesQuery, (querySnapshot) => {
          const fetchedMessages = querySnapshot.docs.map((messageDoc) =>
            transformMessage(messageDoc)
          );
          setMessages(fetchedMessages);
          setLoading(false);
        });

        return () => unsubscribe();
      } catch (error) {
        setError("Không thể lấy dữ liệu cuộc trò chuyện và tin nhắn");
        setLoading(false);
      }
    };

    fetchConversationAndMessages();
  }, [conversationId]);

  if (loading) {
    return (
      <SpinnerContainer>
        <Spinner />
      </SpinnerContainer>
    );
  }

  if (error) {
    return <div>Lỗi: {error}</div>;
  }

  return (
    <StyledContainer>
      <SidebarChatContainer>
        <SidebarChat />
      </SidebarChatContainer>
      <StyledConversationContainer>
        {conversation && (
          <ConversationScreen
            conversation={conversation}
            messages={messages}
            conversationId={conversationId}
          />
        )}
      </StyledConversationContainer>
    </StyledContainer>
  );
};

export default ConversationComponent;
1;
