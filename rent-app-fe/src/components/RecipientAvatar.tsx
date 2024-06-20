import { useRecipient } from "@/hooks/useRecipient";
import Avatar from "@mui/material/Avatar";
import styled from "styled-components";

type Props = ReturnType<typeof useRecipient>;
const StyledAvatar = styled(Avatar)`
  margin: 5px 15px 5px 5px;
`;

const RecipientAvatar = ({ recipient, recipientEmail }: Props) => {
  return recipient?.avatar ? (
    <StyledAvatar src={recipient.avatar} />
  ) : (
    <StyledAvatar>
      {recipientEmail && recipientEmail[0].toUpperCase()}
    </StyledAvatar>
  );
};

export default RecipientAvatar;
