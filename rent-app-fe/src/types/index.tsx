import { Timestamp } from "firebase/firestore";

export type INavLink = {
  imgURL: string;
  route: string;
  label: string;
};

export type IUpdateUser = {
  userId: string;
  name: string;
  bio: string;
  imageId: string;
  imageUrl: URL | string;
  file: File[];
};

export type IImage = {
  imageId: string;
  url: string;
  createDate: string;
};

export type IPostType = {
  typeId: string;
  name: string;
};

export type IPropertyDetail = {
  price: string;
  acreage: string;
  capacity: string;
};

export type IPostLocation = {
  longitude: string;
  latitude: string;
  address: string;
  district: string;
  city: string;
};

export type IPost = {
  postId: string;
  user: {
    userId: string;
    name: string;
    avatar: string;
  };
  title: string;
  description: string;
  imageSet: IImage[];
  location?: IPostLocation;
  created_date: string;
  typeId: IPostType;
  propertyDetail: IPropertyDetail;
};

export type INewPost = {
  postId: string;
  title: string;
  description: string;
  files: File[];
  price: string;
  capacity: string;
  acreage: string;
  address: string;
  district: string;
  city: string;
  latitude: string;
  longitude: string;
  imageUrl: string[];
};

export type IUpdatePost = {
  postId: string;
  userId: string;
  username: string;
  title: string;
  description: string;
  files: File[];
  price: string;
  capacity: string;
  acreage: string;
  address: string;
  district: string;
  city: string;
  latitude: string;
  longitude: string;
  imageId: string;
  imageUrl: string[];
  location?: string;
};

export type IUser = {
  id: number;
  name: string;
  username: string;
  phone: string;
  address: string;
  cccd: string;
  email: string;
  avatar: string;
  followers: any[];
  following: any[];
  role: string;
};

export type INewUser = {
  name: string;
  email: string;
  username: string;
  password: string;
  cccd: number;
  phone: number;
  address: string;
};

export interface Conversation {
  users: string[];
}

export interface IComment {
  commentId: number;
  content: string;
  createdDate: number;
  updatedDate: number;
  positive: number;
  user: {
    userId: string;
    name: string;
    avatar: string;
  };
}

export interface IMessage {
  id: string;
  conversation_id: string;
  text: string;
  img: string[];
  sent_at: string;
  user: string;
}

export interface ChatUser {
  email: string;
  lastSeen: Timestamp;
  avatar: string;
  name: string;
}
