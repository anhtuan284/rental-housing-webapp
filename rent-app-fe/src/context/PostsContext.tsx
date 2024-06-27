import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from "react";
import { IPost } from "@/types";
import { authApi, endpoints } from "@/configs/APIs";
import { convertToIPost } from "@/lib/utils";
import Cookies from "js-cookie";
import UserContext from "./UserContext";

interface PostsContextType {
  posts: IPost[] | null;
  loading: boolean;
  getPost: (page?: number, filters?: any, type?: boolean) => Promise<void>;
  setFilters: (filters: any) => void;
  loadMorePosts: () => void;
  setType: (type: boolean) => void;
  hasMore: boolean;
  type: boolean;
}

const PostsContext = createContext<PostsContextType | undefined>(undefined);

export const PostsProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [posts, setPosts] = useState<IPost[] | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [page, setPage] = useState<number>(1);
  const [filters, setFilters] = useState<any>({});
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [type, setType] = useState<boolean>(true); // true for landlord, false for renter
  const token = Cookies.get("access_token");
  const { user } = useContext(UserContext);

  const cleanFilters = (filters: any) => {
    return Object.fromEntries(
      Object.entries(filters).filter(
        ([key, value]) => value !== 0 && value !== undefined && value !== null
      )
    );
  };

  const getPost = async (page = 1, filters = {}, type = true) => {
    setLoading(true);
    if (!token || token === null) return;
    try {
      const cleanedFilters = cleanFilters(filters);
      let url =
        type === true
          ? endpoints["get-landlord-post"]
          : endpoints["get-renter-post"];
      const res = await authApi(token).get(url, {
        params: { page, ...cleanedFilters },
      });
      const rawPost: Array<any> = res.data;
      const transPost = rawPost.map((post) => convertToIPost(post));
      if (page === 1) {
        setPosts(transPost);
      } else {
        setPosts((prevPosts) =>
          prevPosts ? [...prevPosts, ...transPost] : transPost
        );
      }
      setHasMore(rawPost.length > 0); // Update hasMore based on response length
    } catch (ex: any) {
      console.error("Error fetching posts:", ex.message);
    } finally {
      setLoading(false);
    }
  };

  const loadMorePosts = () => {
    if (hasMore) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    getPost(page, filters, type);
  }, [page, filters, user, type]);

  const applyFilters = (newFilters: any) => {
    setPage(1); // Reset page to 1 when filters change
    setPosts(null);
    setFilters(newFilters);
  };

  const changeType = (newType: boolean) => {
    setPage(1); // Reset page to 1 when type changes
    setPosts(null);
    setType(newType);
  };

  return (
    <PostsContext.Provider
      value={{
        posts,
        loading,
        getPost,
        setFilters: applyFilters,
        loadMorePosts,
        setType: changeType,
        hasMore,
        type,
      }}
    >
      {children}
    </PostsContext.Provider>
  );
};

export const usePosts = () => {
  const context = useContext(PostsContext);
  if (context === undefined) {
    throw new Error("usePosts must be used within a PostsProvider");
  }
  return context;
};
