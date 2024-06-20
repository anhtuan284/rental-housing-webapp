import {
  CollectionReference,
  DocumentData,
  Query,
  collection,
  query,
  where,
  orderBy,
  Timestamp,
  QueryDocumentSnapshot,
} from 'firebase/firestore';import { Conversation, IMessage } from './../types/index';
import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import { db } from '@/configs/firebase';
import { IImage, IPost, IUser } from "@/types";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export const transformToIUser = (data: any): IUser => {
  return {
    id: data.id,
    name: data.name,
    username: data.username,
    email: data.email,
    avatar: data.avatar,
    following: data.followSet,
    followers: data.followSet1,
  };
};
export const getRecipientEmail = (conversationUsers: Conversation['users'], loggedInUser?: IUser | null) => conversationUsers.find(userEmail => userEmail !== loggedInUser?.email)
export const generateQueryGetMessages = (conversationId?: string) =>
	query(
		collection(db, 'messages'),
		where('conversation_id', '==', conversationId),
		orderBy('sent_at', 'asc')
	)

export const transformMessage = (
	message: QueryDocumentSnapshot<DocumentData>
) =>
	({
		id: message.id,
		...message.data(), // spread out conversation_id, text, sent_at, user
		sent_at: message.data().sent_at
			? convertFirestoreTimestampToString(message.data().sent_at as Timestamp)
			: null
	} as IMessage)

  export const convertFirestoreTimestampToString = (timestamp: Timestamp) =>
	new Date(timestamp.toDate().getTime()).toLocaleString()

// export const transformMessage = (
// 	message: QueryDocumentSnapshot<DocumentData>
// ) =>
// 	({
// 		id: message.id,
// 		...message.data(), // spread out conversation_id, text, sent_at, user
// 		sent_at: message.data().sent_at
// 			? convertFirestoreTimestampToString(message.data().sent_at as Timestamp)
// 			: null
// 	} as IMessage)


export const convertToIPost = (data: any): IPost => {
  return {
    typeId: data.typeId ? {
      typeId: data.typeId.typeId,
      name: data.typeId.name,
    } : { typeId: "", name: "" },
    postId: data.postId || "",
    title: data.title || "",
    description: data.description || "",
    imageSet: data.imageSet ? data.imageSet.map((image: any): IImage => ({
      imageId: image.imageId || "",
      url: image.url || "",
      createDate: image.createDate || "",
    })) : [],
    created_date: data.createdDate || "",
    user: data.userId ? {
      userId: data.userId.id || "",
      name: data.userId.name || "",
      avatar: data.userId.avatar || "/assets/icons/profile-placeholder.svg",
    } : { userId: "", name: "", avatar: "/assets/icons/profile-placeholder.svg" },
    propertyDetail: data.propertyDetail ? {
      price: data.propertyDetail.price || "",
      acreage: data.propertyDetail.acreage || "",
      capacity: data.propertyDetail.capacity || "",
    } : { price: "", acreage: "", capacity: "" },
    location: data.location ? {
      longitude: data.location.longitude || "",
      latitude: data.location.latitude || "",
      address: data.location.address || "",
      district: data.location.district || "",
      city: data.location.city || "",
    } : { longitude: "", latitude: "", address: "", district: "", city: "" },
  };
};

export const convertFileToUrl = (file: File) => URL.createObjectURL(file);

export function formatDateString(dateString: string) {
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "short",
    day: "numeric",
  };

  const date = new Date(dateString);
  const formattedDate = date.toLocaleDateString("en-US", options);

  const time = date.toLocaleTimeString([], {
    hour: "numeric",
    month: "short",
    minute: "2-digit",
  });

  return `${formattedDate} at ${time}`;
}

export const multiFormatDateString = (timestamp: string | number = ""): string => {
  let timestampNum: number;
  
  // Handle numeric timestamps directly (assuming they are in milliseconds)
  if (typeof timestamp === "number") {
    timestampNum = timestamp;
  } else if (/^\d+$/.test(timestamp)) {
    timestampNum = Number(timestamp);
  } else {
    const parsedDate = new Date(timestamp);
    if (isNaN(parsedDate.getTime())) {
      return "Invalid Date";
    }
    timestampNum = parsedDate.getTime();
  }

  const date = new Date(timestampNum);
  const now = new Date();

  const diff = now.getTime() - date.getTime();
  const diffInSeconds = diff / 1000;
  const diffInMinutes = diffInSeconds / 60;
  const diffInHours = diffInMinutes / 60;
  const diffInDays = diffInHours / 24;

  switch (true) {
    case Math.floor(diffInDays) >= 30:
      return formatDateString(date.toISOString());
    case Math.floor(diffInDays) === 1:
      return `${Math.floor(diffInDays)} day ago`;
    case Math.floor(diffInDays) > 1 && diffInDays < 30:
      return `${Math.floor(diffInDays)} days ago`;
    case Math.floor(diffInHours) >= 1:
      return `${Math.floor(diffInHours)} hours ago`;
    case Math.floor(diffInMinutes) >= 1:
      return `${Math.floor(diffInMinutes)} minutes ago`;
    default:
      return "Just now";
  }


};