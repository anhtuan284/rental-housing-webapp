import { CommentList } from "@/components/shared";
import { usePosts } from "@/context/PostsContext";
import UserContext from "@/context/UserContext";
import { multiFormatDateString } from "@/lib/utils";
import { useContext, useState } from "react";
import { useParams, Link } from "react-router-dom";
import Slider from "react-slick";
import Modal from "react-modal";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const PostDetails = () => {
  const { user } = useContext(UserContext);
  const { postId } = useParams<{ postId: string }>();
  const { posts } = usePosts();
  const post = posts?.find((post) => post.postId == postId);

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

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
  };

  return (
    <div className="flex flex-1">
      <div className="home-container">
        <div className="flex flex-col items-center p-4">
          <div className="w-full max-w-4xl  shadow-md rounded-lg p-6">
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
                    <p className="text-lg font-semibold text-gray-800">
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
              <h1 className="text-2xl font-bold text-gray-800 mb-4">
                {post.title}
              </h1>
              <p className="text-gray-700 mb-4">{post.description}</p>
              <Slider {...sliderSettings}>
                {post.imageSet.map((image) => (
                  <div key={image.imageId} onClick={() => openModal(image.url)}>
                    <img
                      src={image.url}
                      alt="post image"
                      className="w-full h-auto rounded-lg cursor-pointer"
                    />
                  </div>
                ))}
              </Slider>
            </div>

            <div className="mb-6">
              <p className="text-lg font-semibold text-gray-800">
                Price:{" "}
                <span className="text-purple-400">
                  {post.propertyDetail.price
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}{" "}
                  / Tháng
                </span>
              </p>
              <p className="text-lg font-semibold text-gray-800">
                Acreage:{" "}
                <span className="text-gray-600">
                  {post.propertyDetail.acreage}
                </span>
              </p>
              <p className="text-lg font-semibold text-gray-800">
                Capacity:{" "}
                <span className="text-gray-600">
                  {post.propertyDetail.capacity}
                </span>
              </p>
              <p className="text-lg font-semibold text-gray-800">
                Address:{" "}
                <span className="text-gray-600">
                  {post.location?.address}, {post.location?.district},{" "}
                  {post.location?.city}
                </span>
              </p>
              <p className="text-lg font-semibold text-gray-800">
                Coordinates:{" "}
                <span className="text-gray-600">
                  {post.location?.latitude}, {post.location?.longitude}
                </span>
              </p>
            </div>

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
