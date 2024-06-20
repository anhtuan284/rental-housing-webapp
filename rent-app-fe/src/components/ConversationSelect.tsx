import { useRecipient } from "@/hooks/useRecipient";
import { Conversation } from "@/types";
import styled from "styled-components";
import RecipientAvatar from "./RecipientAvatar";
import { useNavigate } from "react-router-dom";

const StyledContainer = styled.div`
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 15px;
  border-radius: 8px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #877eff;
  }
`;

const RecipientEmail = styled.span`
  margin-left: 15px;
  font-size: 16px;
  color: #dacaca;
`;

const ConversationSelect = ({
  id,
  conversationUsers,
}: {
  id: string;
  conversationUsers: Conversation["users"];
}) => {
  const { recipient, recipientEmail } = useRecipient(conversationUsers);

  const navigate = useNavigate();

  const onSelectConversation = () => {
    navigate(`/conversations/${id}`);
  };

  return (
    <StyledContainer onClick={onSelectConversation}>
      <RecipientAvatar recipient={recipient} recipientEmail={recipientEmail} />
      <RecipientEmail>{recipientEmail}</RecipientEmail>
    </StyledContainer>
  );
};

export default ConversationSelect;
