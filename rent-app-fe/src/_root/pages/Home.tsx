import React, { useEffect, useRef, useCallback, useState } from "react";
import { BingMap, Loader, MapModal, PostCard } from "@/components/shared";
import { usePosts } from "@/context/PostsContext";
import { Button, Input, useToast } from "@/components/ui";
import { IPost } from "@/types";
import { Link } from "react-router-dom";

const Home: React.FC = () => {
  const { posts, loading, loadMorePosts, setFilters, hasMore } = usePosts();

  const { toast } = useToast();
  const observer = useRef<IntersectionObserver | null>(null);
  const [filters, updateFilters] = useState({
    minPrice: 0,
    maxPrice: 0,
    kw: "",
    minAcreage: 0,
    maxAcreage: 0,
    capacity: 0,
  });
  const [isMapModalOpen, setIsMapModalOpen] = useState(false); // State to control modal visibility
  const lastPostElementRef = useCallback(
    (node: HTMLLIElement | null) => {
      if (loading) return;
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          loadMorePosts();
        }
      });
      if (node) observer.current.observe(node);
    },
    [loading, hasMore, loadMorePosts]
  );

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    updateFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("filter submitted");
    setFilters(filters); // Apply filters when form is submitted
  };

  return (
    <div className="flex flex-1">
      <div className="home-container">
        <div className="home-posts">
          <h2 className="h3-bold md:h2-bold text-left w-full">Home Feed</h2>
          <form
            onSubmit={handleSubmit}
            className="grid grid-cols-1 md:grid-cols-3 gap-3"
          >
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="kw"
                className="block text-sm font-medium text-gray-700"
              >
                Keyword
              </label>
              <Input
                type="text"
                id="kw"
                name="kw"
                placeholder="Keyword"
                value={filters.kw}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="minPrice"
                className="block text-sm font-medium text-gray-700"
              >
                Min Price
              </label>
              <Input
                type="number"
                id="minPrice"
                name="minPrice"
                placeholder="Min Price"
                value={filters.minPrice}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="maxPrice"
                className="block text-sm font-medium text-gray-700"
              >
                Max Price
              </label>
              <Input
                type="number"
                id="maxPrice"
                name="maxPrice"
                placeholder="Max Price"
                value={filters.maxPrice}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="minAcreage"
                className="block text-sm font-medium text-gray-700"
              >
                Min Acreage
              </label>
              <Input
                type="number"
                id="minAcreage"
                name="minAcreage"
                placeholder="Min Acreage"
                value={filters.minAcreage}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="maxAcreage"
                className="block text-sm font-medium text-gray-700"
              >
                Max Acreage
              </label>
              <Input
                type="number"
                id="maxAcreage"
                name="maxAcreage"
                placeholder="Max Acreage"
                value={filters.maxAcreage}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <div className="grid w-full max-w-sm items-center gap-1.5">
              <label
                htmlFor="capacity"
                className="block text-sm font-medium text-gray-700"
              >
                Capacity
              </label>
              <Input
                type="number"
                id="capacity"
                name="capacity"
                placeholder="Capacity"
                value={filters.capacity}
                className="shad-input"
                onChange={handleFilterChange}
              />
            </div>
            <Button type="submit" className="bg-primary-500 col-span-1">
              Apply Filters
            </Button>
          </form>

          <ul className="flex flex-col flex-1 gap-9 w-full ">
            {loading && (posts?.length === 0 || posts === null) ? (
              <>
                <Loader />
              </>
            ) : (
              posts?.map((post: IPost, index) => (
                <li
                  key={post.postId}
                  ref={index === posts.length - 1 ? lastPostElementRef : null}
                  className="flex justify-center w-full"
                >
                  <PostCard post={post} />
                </li>
              ))
            )}
          </ul>
          {loading && posts && posts.length > 0 ? <Loader /> : null}
        </div>
      </div>
    </div>
  );
};

export default Home;
