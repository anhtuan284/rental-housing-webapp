import * as z from "zod";

const MAX_FILE_SIZE = 50000000;
const ACCEPTED_IMAGE_TYPES = ["image/jpeg", "image/jpg", "image/png", "image/webp"];

// ============================================================
// USER
// ============================================================
export const SignupValidation = z.object({
  name: z.string().min(2, { message: "Name must be at least 2 characters." }),
  username: z.string().min(2, { message: "Name must be at least 2 characters." }),
  email: z.string().email(),
  password: z.string().min(8, { message: "Password must be at least 8 characters." }),
  cccd: z.string().min(9, { message: "CCDD must be at least 9 characters." }).max(12, { message: "CCDD has max 12 characters." }),
  phone: z.string().min(8, { message: "Phone number must be at least 10 characters." }).max(12, { message: "Phone number must be at least 10 characters" }),
  address: z.string().min(5, { message: "Address must be at least 5 characters." }),
  files: z.any(),
  role: z.string(),
});

export const SigninValidation = z.object({
  username: z.string().min(3, { message: "Username must be at least 3 characters." }),
  password: z.string().min(3, { message: "Password must be at least 3 characters." }),
});

export const ProfileValidation = z.object({
  file: z.any(),
  name: z.string().min(5, { message: "Name must be at least 2 characters." }),
  username: z.string().min(4, { message: "Username must be at least 2 characters." }),
  email: z.string().email(),
  cccd: z.string().min(9, { message: "CCCD must be at least 9 characters." }).max(12, { message: "CCCD must be at least 12 characters." }),
  phone: z.string().min(8, { message: "Phone number must be at least 10 characters." }).max(12, { message: "Phone number must be at least 10 characters" }),
  address: z.string().min(5, { message: "Address must be at least 5 characters." }),
});

// ============================================================
// POST
// ============================================================
export const PostValidation = z.object({
  postId: z.any(),
  title: z.string().min(5, { message: "Minimum 5 characters." }).max(2200, { message: "Maximum 2,200 caracters" }),
  files: z.custom<File[]>(),
  address: z.string().min(1, { message: "This field is required" }).max(1000, { message: "Maximum 1000 characters." }),
  userId: z.any(),
  description: z.string().min(1, { message: "This field is required"}),
  price: z.string().min(1, { message: "Price invalid"}).max(1000, { message: "Price to large"}),
  capacity: z.string().min(1, { message: "This field is required"}),
  acreage: z.string().min(1, { message: "This field is required"}),
  district: z.any(),
  city: z.any(),
  latitude: z.any(),
  longitude: z.any(),
  imageUrl: z.any(),
});

export const RenterPostValidation = z.object({
  postId: z.any(),
  title: z.string().min(5, { message: "Minimum 5 characters." }).max(2200, { message: "Maximum 2,200 caracters" }),
  description: z.string().min(1, { message: "This field is required"}),
  files: z.custom<File[]>(),
  address: z.string().min(1, { message: "This field is required" }).max(1000, { message: "Maximum 1000 characters." }),
  userId: z.any(),
  district: z.any(),
  city: z.any(),
  latitude: z.any(),
  longitude: z.any(),
  imageUrl: z.any(),
});