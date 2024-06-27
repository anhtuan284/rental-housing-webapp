import { authApi, endpoints } from "@/configs/APIs";
import Comment from "./Comment";
import { useContext, useEffect, useState } from "react";
import { Button, Input, useToast } from "../ui";
import { IComment } from "@/types";
import Loader from "./Loader";
import { transformcomment } from "@/lib/utils";
import UserContext from "@/context/UserContext";

const CommentList = ({ postId }: { postId: string }) => {
  const [comments, setComments] = useState<IComment[]>([]);
  const { toast } = useToast();
  const [loading, setLoading] = useState<boolean>(false);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const { user } = useContext(UserContext);
  const [newComment, setNewComment] = useState<string>("");

  useEffect(() => {
    fetchComments(postId);
  }, [postId]);

  const fetchComments = async (postId: string) => {
    setLoading(true);
    try {
      const res = await authApi().get(endpoints["get-comments"](postId));
      if (res.status === 200) {
        const rawCmt: Array<any> = res.data;
        const transCmt = rawCmt.map((cmt) => transformcomment(cmt));
        setComments(transCmt);
      } else {
        setComments([]);
      }
    } catch (err: any) {
      toast({
        title: "Error fetching comments",
        description: err.message,
      });
    } finally {
      setLoading(false);
    }
  };

  const handleAddComment = async () => {
    if (!newComment.trim()) {
      return;
    }
    setSubmitting(true);

    try {
      const res = await authApi().post(endpoints["add-comment"], {
        postId: postId,
        content: newComment,
      });

      if (res.status === 200) {
        // Temporarily add the new comment with a gray color
        const tempComment: IComment = {
          content: newComment,
          commentId: 0,
          createdDate: Date.now(),
          updatedDate: Date.now(),
          positive: 0,
          user: {
            userId: user ? user?.id.toString() : "",
            name: user ? user?.name : "",
            avatar: user ? user?.avatar : "",
          },
        };
        setComments((prevComments) => [...prevComments, tempComment]);
        setNewComment("");

        fetchComments(postId);
      } else {
        toast({
          title: "Error adding comment",
          description: "Failed to add the comment. Please try again.",
        });
      }
    } catch (err: any) {
      toast({
        title: "Error adding comment",
        description: err.message,
      });
    } finally {
      setSubmitting(false);
      setNewComment("");
    }
  };

  const handleDelete = (commentId: number) => {
    setComments((prevComments) =>
      prevComments.filter((comment) => comment.commentId !== commentId)
    );
  };

  return (
    <div className="comment-list gap-2">
      {loading ? (
        <Loader />
      ) : (
        <>
          {comments && comments.length > 0 ? (
            comments.map((comment) => (
              <Comment
                key={comment.commentId}
                comment={comment}
                onDelete={handleDelete} // Truyền hàm onDelete vào Comment
              />
            ))
          ) : (
            <div className="text-slate-400 justify-center">
              <i>This post has no comment yet !</i>
            </div>
          )}
        </>
      )}
      <div className="flex w-full items-center space-x-2 mt-3">
        <img src={user?.avatar} className="w-12 h-12"></img>
        <Input
          type="comment"
          className="shad-input"
          placeholder="Your comment..."
          onChange={(e) => setNewComment(e.currentTarget.value)}
        />
        <Button
          onClick={handleAddComment}
          disabled={submitting}
          className="shad-button_primary rounded hover:bg-primary-500 disabled:bg-gray-400"
        >
          {submitting ? "Submitting..." : "Submit"}
        </Button>
      </div>
    </div>
  );
};

export default CommentList;
