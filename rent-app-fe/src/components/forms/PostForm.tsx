import * as z from "zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { zodResolver } from "@hookform/resolvers/zod";

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Button,
  Input,
  Textarea,
  useToast,
  SelectItem,
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
} from "@/components/ui";
import { PostValidation } from "@/lib/validation";
import { FileUploader, Loader } from "@/components/shared";
import { useEffect, useState } from "react";
import { INewPost, IPost, IUpdatePost } from "@/types";
import { authApi, endpoints } from "@/configs/APIs";
import Cookies from "js-cookie";

type PostFormProps = {
  post?: INewPost | IUpdatePost;
  action: "Create" | "Update";
};

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

// Declare the global variable on window
declare global {
  interface Window {
    initializeMap: () => void;
  }
}

// Declare Microsoft object
declare var Microsoft: any;

const PostForm = ({ post, action }: PostFormProps) => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [cities, setCities] = useState<City[]>([]);
  const [districts, setDistricts] = useState<District[]>([]);

  const form = useForm<z.infer<typeof PostValidation>>({
    resolver: zodResolver(PostValidation),
    defaultValues: {
      postId: post ? post?.postId : undefined,
      title: post ? post?.title : "",
      description: post ? post?.description : "",
      files: [],
      acreage: post ? post?.acreage : "",
      price: post ? post?.price : "",
      capacity: post ? post?.capacity : "",
      address: post ? post?.address : "",
      city: post ? post?.city : "",
      district: post ? post?.district : "",
      latitude: post ? post?.latitude : "",
      longitude: post ? post?.longitude : "",
      imageUrl: post ? post?.imageUrl : "",
    },
  });

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

  // const handleCityChange = (event: React.FormEvent<HTMLSelectElement>) => {
  //   const selectedCity = event.currentTarget.value;
  //   form.setValue("city", selectedCity);
  //   fetchDistricts(selectedCity);
  // };

  const fetchCoordinates = async (fullAddress: string) => {
    const encodedAddress = encodeURIComponent(fullAddress);
    console.log(fullAddress);
    const response = await fetch(
      `http://dev.virtualearth.net/REST/v1/Locations?q=${encodedAddress}&key=AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P`
    );
    const data = await response.json();
    if (data.resourceSets[0].resources.length > 0) {
      const coordinates = data.resourceSets[0].resources[0].point.coordinates;
      form.setValue("latitude", coordinates[0].toString());
      form.setValue("longitude", coordinates[1].toString());
      updateMap(coordinates[0], coordinates[1]);
    }
  };

  const updateMap = (latitude: string | number, longitude: string | number) => {
    var map = new Microsoft.Maps.Map(document.getElementById("map"), {
      credentials:
        "AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P",
      center: new Microsoft.Maps.Location(latitude, longitude),
      zoom: 30,
    });

    var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
      draggable: true,
    });
    map.entities.push(pushpin);

    Microsoft.Maps.Events.addHandler(pushpin, "dragend", function (e: any) {
      var location = e.target.getLocation();
      form.setValue("latitude", location.latitude.toString());
      form.setValue("longitude", location.longitude.toString());
    });

    var initialLocation = new Microsoft.Maps.Location(latitude, longitude);
    pushpin.setLocation(initialLocation);
    map.setView({ center: initialLocation });
  };

  useEffect(() => {
    fetchCities();
  }, []);

  useEffect(() => {
    // Initialize Bing Maps
    const mapScript = document.createElement("script");
    mapScript.src =
      "https://www.bing.com/api/maps/mapcontrol?callback=initializeMap";
    mapScript.async = true;
    mapScript.defer = true;
    document.body.appendChild(mapScript);

    window.initializeMap = () => {
      updateMap(
        post?.latitude || 10.735307748069317,
        post?.longitude || 106.70096272563886
      );
    };

    return () => {
      document.body.removeChild(mapScript);
    };
  }, []);

  useEffect(() => {
    const fullAddress = `${form.getValues("address")}, ${
      districts.find(
        (district) =>
          district.district_id.toString() === form.getValues("district")
      )?.district_name
    }`;
    fetchCoordinates(fullAddress);
  }, [form.watch("address"), form.watch("district")]);

  // Handler
  const handleSubmit = async (value: z.infer<typeof PostValidation>) => {
    // ACTION = UPDATE
    if (value.files.length < 3) {
      toast({
        title: "Fail to upload.",
        description: "Please upload at least 3 photos",
      });
      return;
    }

    const selectedCity = cities.find(
      (city) => city.province_id.toString() === value.city
    );
    const selectedDistrict = districts.find(
      (district) => district.district_id.toString() === value.district
    );

    const formData = new FormData();
    formData.append("title", value.title);
    formData.append("description", value.description);
    formData.append("price", value.price);
    formData.append("acreage", value.acreage);
    formData.append("capacity", value.capacity);
    formData.append("address", value.address);
    formData.append(
      "district",
      selectedDistrict ? selectedDistrict.district_name : ""
    );
    formData.append("city", selectedCity ? selectedCity.province_name : "");
    formData.append("latitude", value.latitude);
    formData.append("longitude", value.longitude);

    value.files.forEach((file) => {
      formData.append(`files`, file);
    });

    let token = Cookies.get("access_token");
    if (!token || token === undefined || token === null) {
      toast({
        title: "Unauthorized",
        description: "Please login to continue.",
      });
      return navigate("/sign-in");
    }

    if (post && action === "Update") {
      const updatedPost: IPost = await authApi(token).post(
        endpoints["create-post-landlord"],
        formData
      );
      if (!updatedPost) {
        toast({
          title: `${action} post failed. Please try again.`,
        });
      }
      return navigate(`/posts/${updatedPost?.postId}`);
    }

    // ACTION = CREATE
    const newPost: IPost = await authApi(token).post(
      endpoints["create-post-landlord"],
      formData
    );

    if (!newPost) {
      toast({
        title: `${action} post failed. Please try again.`,
      });
    } else {
      toast({
        title: `${action} post successfully.`,
      });
      navigate("/");
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(handleSubmit)}
        className="flex flex-col gap-9 w-full  max-w-5xl"
      >
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Title</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="description"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Description</FormLabel>
              <FormControl>
                <Textarea
                  className="shad-textarea custom-scrollbar"
                  {...field}
                />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="files"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Add Photos</FormLabel>
              <FormControl>
                <FileUploader
                  fieldChange={field.onChange}
                  mediaUrl={post?.imageUrl || []}
                />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="city"
          render={({ field }) => (
            <FormItem>
              <FormLabel>City</FormLabel>
              <Select
                onValueChange={(value) => {
                  form.setValue("city", value);
                  fetchDistricts(value);
                }}
                defaultValue={field.value}
              >
                <FormControl>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a city" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent className="bg-gray-950">
                  {cities &&
                    cities.map((city) => (
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
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="district"
          render={({ field }) => (
            <FormItem>
              <FormLabel>District</FormLabel>
              <Select onValueChange={field.onChange} defaultValue={field.value}>
                <FormControl>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a district" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent className="bg-gray-950">
                  {districts &&
                    districts.map((district) => (
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
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="address"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Address</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <div
          id="map"
          style={{
            height: "400px",
            width: "100%",
            display: `${
              form.getValues("address") &&
              form.getValues("city") &&
              form.getValues("district")
                ? "block"
                : "none"
            }`,
          }}
        ></div>

        <FormField
          control={form.control}
          name="latitude"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Latitude</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="longitude"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Longitude</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="price"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Price</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="acreage"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Acreage</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="capacity"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="shad-form_label">Capacity</FormLabel>
              <FormControl>
                <Input type="text" className="shad-input" {...field} />
              </FormControl>
              <FormMessage className="shad-form_message" />
            </FormItem>
          )}
        />

        <div className="flex gap-4 items-center justify-end">
          <Button
            type="button"
            className="shad-button_dark_4"
            onClick={() => navigate(-1)}
          >
            Cancel
          </Button>
          <Button
            type="submit"
            className="shad-button_primary whitespace-nowrap"
            disabled={form.formState.isSubmitting}
          >
            {form.formState.isSubmitting && <Loader />}
            {action} Post
          </Button>
        </div>
      </form>
    </Form>
  );
};

export default PostForm;
