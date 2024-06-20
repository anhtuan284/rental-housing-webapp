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
import { IUser } from "@/types";
import { db } from '@/configs/firebase';

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
    minute: "2-digit",
  });

  return `${formattedDate} at ${time}`;
}

export const multiFormatDateString = (timestamp: string = ""): string => {
  const timestampNum = Math.round(new Date(timestamp).getTime() / 1000);
  const date: Date = new Date(timestampNum * 1000);
  const now: Date = new Date();

  const diff: number = now.getTime() - date.getTime();
  const diffInSeconds: number = diff / 1000;
  const diffInMinutes: number = diffInSeconds / 60;
  const diffInHours: number = diffInMinutes / 60;
  const diffInDays: number = diffInHours / 24;

  switch (true) {
    case Math.floor(diffInDays) >= 30:
      return formatDateString(timestamp);
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