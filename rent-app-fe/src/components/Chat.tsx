import styled from "styled-components";
import SidebarChat from "@/components/shared/SidebarChat";

const StyledContainer = styled.div`
  display: flex;
`;

const Content = styled.div`
  flex-grow: 1;
  padding: 20px;
`;

const Chat = () => {
  return (
    <StyledContainer>
      <SidebarChat />
      <Content>
        <h1>Message</h1>
      </Content>
    </StyledContainer>
  );
};

export default Chat;
