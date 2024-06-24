// Import necessary Firebase modules
import { IUser } from '@/types';
import { getApps, initializeApp } from "firebase/app";
import { doc, getFirestore, serverTimestamp, setDoc } from "firebase/firestore";
import { getAuth } from "firebase/auth";
import { getStorage } from 'firebase/storage';

// Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCiteD1RB5cw-OahuVDXbcd6xBV_yWNtIQ",
  authDomain: "rental-website-2f47b.firebaseapp.com",
  projectId: "rental-website-2f47b",
  storageBucket: "rental-website-2f47b.appspot.com",
  messagingSenderId: "718272320788",
  appId: "1:718272320788:web:6e48ef492a54d485b8de01"
};

// Initialize Firebase
let app;
if (!getApps().length) {
  app = initializeApp(firebaseConfig);
} else {
  app = getApps()[0];
}

// Initialize Firebase services
const db = getFirestore(app);
const auth = getAuth(app);
const storage = getStorage(app);

// Function to login and set user in Firestore
export const loginFirebase = async (currentUser: IUser) => {
  try {
    if (!currentUser || !currentUser.id) {
      throw new Error("Invalid user object");
    }
    const userId = String(currentUser.id);
    await setDoc(
      doc(db, "users", userId),
      {
        id: currentUser.id,
        name: currentUser.name,
        email: currentUser.email,
        lastSeen: serverTimestamp(),
        avatar: currentUser.avatar,
      },
      { merge: true }
    );
    console.log("Document successfully written!");
  } catch (error) {
    console.error("ERROR setting user in db firebase", error);
  }
};

// Export Firebase services
export { db, auth, storage };
