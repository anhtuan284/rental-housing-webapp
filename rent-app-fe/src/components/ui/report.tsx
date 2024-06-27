import React from "react";
import { IconButton } from "@mui/material";
import ReportIcon from "@mui/icons-material/Report";

type ReportBadgeProps = {
  reportCount: number;
};

const ReportBadge: React.FC<ReportBadgeProps> = ({ reportCount }) => {
  if (reportCount <= 0) return null;

  return (
    <IconButton
      size="small"
      style={{
        backgroundColor: "#e7932a",
        color: "#f1eeec",
        borderRadius: "50%",
      }}
    >
      <ReportIcon />
      <span style={{ marginLeft: "2px" }}>{reportCount}</span>
    </IconButton>
  );
};

export default ReportBadge;
