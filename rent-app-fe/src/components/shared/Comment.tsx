import { authApi, endpoints } from "@/configs/APIs";
import UserContext from "@/context/UserContext";
import { multiFormatDateString } from "@/lib/utils";
import { IComment } from "@/types";
import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import { useToast } from "../ui";
import Loader from "./Loader";

type CommentProps = {
  comment: IComment;
  onDelete: (commentId: number) => void; // Thêm prop onDelete
};

const Comment = ({ comment, onDelete }: CommentProps) => {
  const { user } = useContext(UserContext); // Assume this hook gives you the current user's ID
  const { toast } = useToast();
  const [visible, setVisible] = useState<boolean>(comment.positive === 1);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [isSaving, setIsSaving] = useState<boolean>(false);
  const [editedContent, setEditedContent] = useState<string>(comment.content);

  const handleUpdate = async () => {
    try {
      setIsSaving(true);
      let res = await authApi().post(endpoints["edit-comment"], {
        commentId: comment.commentId,
        content: editedContent,
      });
      if (res.status === 200) {
        comment.content = editedContent;
        toast({
          title: "Comment updated successfully",
          description: "Your comment has been updated.",
        });
      }
    } catch (ex: any) {
      toast({
        title: "Error updating comment",
        description: ex.message,
      });
    } finally {
      setIsSaving(false);
      setIsEditing(false);
    }
  };

  const handleDelete = async (commentId: string) => {
    try {
      let res = await authApi().delete(endpoints["delete-comment"](commentId));
      if (res.status === 200) {
        onDelete(comment.commentId); // Gọi hàm onDelete sau khi xóa thành công
        toast({
          title: "Comment deleted successfully",
          description: "Your comment has been deleted.",
        });
      }
    } catch (ex: any) {
      toast({
        title: "Error deleting comment",
        description: ex.message,
      });
    }
  };

  return (
    <div className="flex items-start gap-3 my-5">
      <Link to={`/profile/${comment.user.userId}`}>
        <img
          src={comment.user.avatar || "/assets/icons/profile-placeholder.svg"}
          alt="user avatar"
          className="w-8 h-8 rounded-full"
        />
      </Link>
      <div className="flex-1">
        <div className="bg-slate-900 rounded-lg p-3">
          <Link
            to={`/profile/${comment.user.userId}`}
            className="flex flex-1 justify-start gap-2"
          >
            <p className="comment-user-name font-extrabold text-primary-500">
              {comment.user.name}
            </p>
            <p className="comment-user-name italic text-gray-500">
              {multiFormatDateString(comment.createdDate)}
            </p>
          </Link>
          {visible ? (
            <div>
              {isEditing ? (
                <div>
                  <input
                    type="text"
                    value={editedContent}
                    onChange={(e) => setEditedContent(e.target.value)}
                    className="w-full p-2 rounded bg-gray-700 text-white"
                  />
                  <div className="flex gap-2 mt-2">
                    <button
                      className="btn btn-primary text-primary-500 font-bold"
                      onClick={handleUpdate}
                      disabled={isSaving}
                    >
                      {isSaving ? <Loader /> : "Save"}
                    </button>
                    <button
                      className="btn btn-secondary text-gray-600 font-bold"
                      onClick={() => setIsEditing(false)}
                    >
                      Cancel
                    </button>
                  </div>
                </div>
              ) : (
                <p className="comment-text">{comment.content}</p>
              )}
            </div>
          ) : (
            <div className="flex align-start">
              <p className="text-italic text-gray-500 me-3">
                <i>Bình luận này đã bị ẩn do phát hiện mang tính tiêu cực</i>
              </p>
              <button
                className="btn btn-primary text-primary-500 font-bold"
                onClick={() => setVisible(!visible)}
              >
                Xem
              </button>
            </div>
          )}
        </div>
        {user?.id.toString() == comment.user.userId && (
          <div className="flex gap-2 ml-2 mt-2">
            <button
              className="btn btn-primary text-blue-700 font-bold"
              onClick={() => setIsEditing(!isEditing)}
            >
              Update
            </button>
            <button
              className="btn btn-danger text-red-500 font-bold"
              onClick={() => handleDelete(comment.commentId.toString())}
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Comment;
