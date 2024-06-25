import APIs, { BASE_URL, CLIENT_URL, endpoints } from "@/configs/APIs";
import { IPost } from "@/types";
import React, { useEffect, useRef } from "react";

interface BingMapProps {
  posts: IPost[];
}

const BingMap: React.FC<BingMapProps> = ({ posts }) => {
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const script = document.createElement("script");
    script.src = `http://www.bing.com/api/maps/mapcontrol?callback=loadMapScenario&key=AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P`;
    script.async = true;
    script.defer = true;
    document.body.appendChild(script);

    (window as any).loadMapScenario = () => {
      const map = new (window as any).Microsoft.Maps.Map(mapRef.current, {});

      const infobox = new (window as any).Microsoft.Maps.Infobox(
        map.getCenter(),
        {
          visible: false,
        }
      );
      infobox.setMap(map);

      posts.forEach((post) => {
        const location = new (window as any).Microsoft.Maps.Location(
          post.location?.latitude,
          post.location?.longitude
        );
        const pin = new (window as any).Microsoft.Maps.Pushpin(location);

        // Store some metadata with the pushpin
        pin.metadata = {
          title: `${post.location?.address}`,
          description: `<a target="blank" style="color:blue" href="${CLIENT_URL}/posts/${post.postId}">View Post</a>`,
        };

        (window as any).Microsoft.Maps.Events.addHandler(
          pin,
          "click",
          (e: any) => {
            if (e.target.metadata) {
              infobox.setOptions({
                location: e.target.getLocation(),
                title: e.target.metadata.title,
                description: e.target.metadata.description,
                visible: true,
              });
            }
          }
        );

        // Add pushpin to the map
        map.entities.push(pin);
      });
    };

    return () => {
      // Clean up the script
      document.body.removeChild(script);
    };
  }, [posts]);

  return (
    <div className="relative w-full h-200 z-0">
      <div ref={mapRef} className="w-full h-full z-0"></div>
    </div>
  );
};

export default BingMap;
