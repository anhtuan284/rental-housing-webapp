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

export type IPost = {
  postId: string;
  creator: IUser;
  title: string;
  describe: string;
  files: string[];
  location?: string;
  tags?: string;
  created_date: string;
};

export type INewPost = {
  userId: string;
  caption: string;
  file: File[];
  location?: string;
  tags?: string;
};

export type IUpdatePost = {
  postId: string;
  caption: string;
  imageId: string;
  imageUrl: URL;
  file: File[];
  location?: string;
  tags?: string;
};

export type IUser = {
  id: number;
  name: string;
  username: string;
  email: string;
  avatar: string;
  followers: any[];
  following: any[];
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
export interface IMessage {
  id: string;
  conversation_id: string;
  text: string;
  sent_at: string;
  user: string;
}

export interface ChatUser {
  email: string;
  lastSeen: Timestamp;
  avatar: string;
}
