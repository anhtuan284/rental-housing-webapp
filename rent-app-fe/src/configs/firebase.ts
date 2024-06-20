import { IUser } from '@/types';
// Import the functions you need from the SDKs you need
import { getApps, initializeApp } from "firebase/app";
import { doc, getFirestore, serverTimestamp, setDoc } from "firebase/firestore";
import { getAuth, GoogleAuthProvider } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
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

const db = getFirestore(app);
const auth = getAuth(app);
export {db, auth,}

