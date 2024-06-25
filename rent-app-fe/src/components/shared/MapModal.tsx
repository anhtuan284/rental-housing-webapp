import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import {
  Button,
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
  toast,
  Input,
} from "@/components/ui";
import BingMap from "./BingMap";
import { IPost } from "@/types";
import { AxiosResponse } from "axios";
import { authApi, endpoints } from "@/configs/APIs";
import { convertToIPost } from "@/lib/utils";

type City = {
  province_id: number;
  province_name: string;
  province_type: string;
};

type District = {
  district_name: string;
  district_id: number;
  district_type: string;
  lat: string;
  lng: string;
  province_id: string;
};

declare global {
  interface Window {
    initializeMap: () => void;
  }
}

declare var Microsoft: any;

const MapModal: React.FC = () => {
  const [cities, setCities] = useState<City[]>([]);
  const [districts, setDistricts] = useState<District[]>([]);
  const [selectedCity, setSelectedCity] = useState<string>("");
  const [selectedDistrict, setSelectedDistrict] = useState<string>("");
  const [latitude, setLatitude] = useState<string>("");
  const [longitude, setLongitude] = useState<string>("");
  const [dist, setDist] = useState<string>("");
  const [posts, setPosts] = useState<IPost[]>([]);
  const [isOpen, setIsOpen] = useState<boolean>(false);

  useEffect(() => {
    fetchCities();
  }, []);

  useEffect(() => {
    if (selectedCity) {
      fetchDistricts(selectedCity);
    }
  }, [selectedCity]);

  const fetchPostByPin = async () => {
    setIsOpen(false);
    console.log(longitude, latitude);
    try {
      let res: AxiosResponse = await authApi().post(
        endpoints["get-post-near-pin"],
        {
          longitude: longitude,
          latitude: latitude,
          dist: dist != "" ? dist : 5,
        }
      );
      const rawPost: Array<any> = res.data;
      const transPost = rawPost.map((post) => convertToIPost(post));
      setPosts(transPost);
      console.log(transPost);
    } catch (ex: any) {
      toast({ title: "Error", description: "Error while fetching post" });
    } finally {
      setSelectedCity("");
      setSelectedDistrict("");
    }
  };

  const fetchCities = async () => {
    const response = await fetch("https://vapi.vnappmob.com/api/province/");
    const data = await response.json();
    setCities(data.results);
  };

  const fetchDistricts = async (cityId: string) => {
    const response = await fetch(
      `https://vapi.vnappmob.com/api/province/district/${cityId}`
    );
    const data = await response.json();
    setDistricts(data.results);
  };

  const fetchCoordinates = async (fullAddress: string) => {
    const encodedAddress = encodeURIComponent(fullAddress);
    const response = await fetch(
      `http://dev.virtualearth.net/REST/v1/Locations?q=${encodedAddress}&key=AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P`
    );
    const data = await response.json();
    if (data.resourceSets[0].resources.length > 0) {
      const coordinates = data.resourceSets[0].resources[0].point.coordinates;
      setLatitude(coordinates[0].toString());
      setLongitude(coordinates[1].toString());
      updateMap(coordinates[0], coordinates[1]);
    }
  };

  const updateMap = (latitude: string | number, longitude: string | number) => {
    const map = new Microsoft.Maps.Map(document.getElementById("map"), {
      credentials:
        "AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P",
      center: new Microsoft.Maps.Location(latitude, longitude),
      zoom: 15,
    });

    const pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
      draggable: true,
    });
    map.entities.push(pushpin);

    Microsoft.Maps.Events.addHandler(pushpin, "dragend", function (e: any) {
      const location = e.target.getLocation();
      setLatitude(location.latitude.toString());
      setLongitude(location.longitude.toString());
    });

    const initialLocation = new Microsoft.Maps.Location(latitude, longitude);
    pushpin.setLocation(initialLocation);
    map.setView({ center: initialLocation });
  };

  useEffect(() => {
    if (selectedCity && selectedDistrict) {
      const city = cities.find(
        (city) => city.province_id.toString() === selectedCity
      );
      const district = districts.find(
        (district) => district.district_id.toString() === selectedDistrict
      );
      if (city && district) {
        fetchCoordinates(`${district.district_name}, ${city.province_name}`);
      }
    }
  }, [selectedCity, selectedDistrict]);

  return (
    <div className="flex flex-1 relative ">
      <Button
        onClick={() => setIsOpen(true)}
        className="shad-button_primary absolute z-30 shadow-2xl m-5"
      >
        Pick location
      </Button>
      <Modal
        isOpen={isOpen}
        onRequestClose={() => setIsOpen(false)}
        contentLabel="Image Modal"
        className="flex items-center justify-center h-full w-full"
        overlayClassName="fixed inset-0 bg-black bg-opacity-70 z-10"
      >
        <div className="p-4 rounded-lg max-w-4xl mx-auto bg-black">
          <div className="modal-content">
            <h2>Select Location</h2>
            <div className="flex flex-1 my-5 gap-3">
              <Select
                onValueChange={(value) => {
                  setSelectedCity(value);
                }}
                defaultValue=""
              >
                <SelectTrigger>
                  <SelectValue placeholder="Select a city" />
                </SelectTrigger>
                <SelectContent className="bg-gray-950">
                  {cities.map((city) => (
                    <SelectItem
                      className="hover:bg-gray-800"
                      key={city.province_id}
                      value={city.province_id.toString()}
                    >
                      {city.province_name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>

              <Select
                onValueChange={(value) => {
                  setSelectedDistrict(value);
                }}
                defaultValue=""
              >
                <SelectTrigger>
                  <SelectValue placeholder="Select a district" />
                </SelectTrigger>
                <SelectContent className="bg-gray-950">
                  {districts.map((district) => (
                    <SelectItem
                      className="hover:bg-gray-800"
                      key={district.district_id}
                      value={district.district_id.toString()}
                    >
                      {district.district_name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>

              <Input
                type="text"
                id="dist"
                name="dist"
                placeholder="Distance"
                value={dist}
                className="shad-input"
                onChange={(e) => setDist(e.target.value)}
              />
            </div>

            <div
              id="map"
              style={{
                height: "400px",
                width: "1000px",
                display: selectedCity && selectedDistrict ? "block" : "none",
              }}
            ></div>

            <div className="modal-footer my-4 flex flex-1 ">
              <Button
                className="shad-button_dark_4"
                onClick={() => setIsOpen(false)}
              >
                Close
              </Button>

              <Button className="shad-button_primary" onClick={fetchPostByPin}>
                OK
              </Button>
            </div>
          </div>
        </div>
      </Modal>
      {posts && <BingMap posts={posts} />}
    </div>
  );
};

export default MapModal;
