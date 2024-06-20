import React, { useState, useContext, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import Slider from "react-slick";
import Modal from "react-modal";
import { multiFormatDateString } from "@/lib/utils";
import { usePosts } from "@/context/PostsContext";
import UserContext from "@/context/UserContext";
import { CommentList } from "@/components/shared";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
// Declare the global variable on window
declare global {
  interface Window {
    initializeMap: () => void;
  }
}

// Declare Microsoft object
declare var Microsoft: any;

const PostDetails = () => {
  const { user } = useContext(UserContext);
  const { postId } = useParams<{ postId: string }>();
  const { posts } = usePosts();
  const [post, setPost] = useState<any>(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState<string | null>(null); // State cho ảnh được chọn

  // Fetch post data based on postId
  useEffect(() => {
    const foundPost = posts?.find((p: any) => p.postId == postId);
    if (foundPost) {
      setPost(foundPost);
    }
  }, [postId, posts]);

  // Initialize Bing Maps once post data is available
  useEffect(() => {
    if (post) {
      const mapScript = document.createElement("script");
      mapScript.src =
        "https://www.bing.com/api/maps/mapcontrol?callback=initializeMap";
      mapScript.async = true;
      mapScript.defer = true;
      document.body.appendChild(mapScript);

      window.initializeMap = () => {
        var map = new Microsoft.Maps.Map(document.getElementById("map"), {
          credentials:
            "AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P",
          center: new Microsoft.Maps.Location(
            post.location?.latitude || 10.735307748069317,
            post.location?.longitude || 106.70096272563886
          ),
          zoom: 15,
        });

        var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
          draggable: false,
        });
        map.entities.push(pushpin);
      };

      return () => {
        document.body.removeChild(mapScript);
      };
    }
  }, [post]);

  const openModal = (imageUrl: string) => {
    setSelectedImage(imageUrl);
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setSelectedImage(null);
    setModalIsOpen(false);
  };

  if (!post) return <div>Post not found</div>;

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
      {
        breakpoint: 640,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <div className="flex flex-1">
      <div className="home-container">
        <div className="flex flex-col items-center p-4">
          <div className="w-full max-w-2xl shadow-md rounded-lg p-6">
            <div className="flex justify-between items-center mb-6">
              <div className="flex items-center gap-3">
                <Link to={`/profile/${post.user.userId}`}>
                  <img
                    src={
                      post.user.avatar ||
                      "/assets/icons/profile-placeholder.svg"
                    }
                    alt="creator"
                    className="w-12 lg:h-12 rounded-full"
                  />
                </Link>
                <div className="flex flex-col">
                  <Link to={`/profile/${post.user.userId}`}>
                    <p className="text-lg font-semibold text-white">
                      {post.user.name}
                    </p>
                  </Link>
                  <div className="flex items-center gap-2 text-gray-500">
                    <p className="text-sm">
                      <i>{multiFormatDateString(post.created_date)}</i>
                    </p>
                    <span>•</span>
                    <p className="text-sm text-primary-500 font-semibold">
                      {post.location?.city}
                    </p>
                  </div>
                </div>
              </div>
              {user?.id.toString() === post.user.userId && (
                <Link to={`/update-post/${post.postId}`}>
                  <img
                    src="/assets/icons/edit.svg"
                    alt="edit"
                    className="w-5 h-5"
                  />
                </Link>
              )}
            </div>

            <div className="mb-6">
              <h1 className="text-2xl font-bold text-white mb-4">
                {post.title}
              </h1>
              <p className="text-gray-100 mb-4">{post.description}</p>
              <Slider {...sliderSettings}>
                {post.imageSet.map((image: any) => (
                  <div
                    key={image.imageId}
                    onClick={() => openModal(image.url)}
                    className="cursor-pointer"
                  >
                    <img
                      src={image.url}
                      alt="post image"
                      className="w-full h-auto rounded-lg"
                    />
                  </div>
                ))}
              </Slider>
            </div>

            <div className="mb-6">
              <p className="text-lg font-semibold text-white">
                Price:{" "}
                <span className="text-purple-400">
                  {post.propertyDetail.price
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}{" "}
                  / Tháng
                </span>
              </p>
              <p className="text-lg font-semibold text-white font-bolder">
                Acreage:{" "}
                <span className="text-light-1">
                  {post.propertyDetail.acreage}
                </span>
              </p>
              <p className="text-lg font-semibold text-white font-bolder">
                Capacity:{" "}
                <span className="text-light-1">
                  {post.propertyDetail.capacity}
                </span>
              </p>
              <p className="text-lg font-semibold text-white font-bolder">
                Address:{" "}
                <span className="text-light-1">
                  {post.location?.address}, {post.location?.district},{" "}
                  {post.location?.city}
                </span>
              </p>
              <p className="text-lg font-semibold text-white font-bolder">
                Coordinates:{" "}
                <span className="text-light-1">
                  {post.location?.latitude}, {post.location?.longitude}
                </span>
              </p>
            </div>
            <div id="map" style={{ width: "100%", height: "400px" }}></div>
            <div className="text-2xl font-bold my-4">Comments</div>
            <CommentList postId={post.postId} />

            {selectedImage && (
              <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Image Modal"
                className="flex items-center justify-center h-full"
                overlayClassName="fixed inset-0 bg-black bg-opacity-50"
              >
                <div className="p-4 rounded-lg max-w-4xl mx-auto">
                  <img
                    src={selectedImage}
                    alt="Selected post"
                    className="w-full h-auto rounded-lg"
                  />
                  <button
                    onClick={closeModal}
                    className="mt-4 px-4 py-2 bg-primary-500 text-white rounded-lg"
                  >
                    Close
                  </button>
                </div>
              </Modal>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PostDetails;
