// import { useToast } from "@/components/ui/use-toast";
import { Loader } from "@/components/shared";

const Home = () => {
  return (
    <div className="flex flex-1">
      <div className="home-container">
        <div className="home-posts">
          <h2 className="h3-bold md:h2-bold text-left w-full">Home Feed</h2>

          {/* <ul className="flex flex-col flex-1 gap-9 w-full ">
            {posts?.documents.map((post: Models.Document) => (
              <li key={post.$id} className="flex justify-center w-full">
                <PostCard post={post} />
              </li>
            ))}
          </ul> */}
        </div>
      </div>
    </div>
  );
};

export default Home;
