import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import { IUser } from "@/types";

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
  };
};