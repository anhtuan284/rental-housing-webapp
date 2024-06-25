// components/GoogleLoginButton.tsx
import React, { useEffect } from "react";
import { gapi } from "gapi-script";
import GoogleLogin, {
  GoogleLoginResponse,
  GoogleLoginResponseOffline,
} from "react-google-login";
import axios, { AxiosResponse } from "axios";
import APIs, { endpoints } from "@/configs/APIs";

interface GoogleLoginButtonProps {
  onSuccess: (
    response: GoogleLoginResponse | GoogleLoginResponseOffline
  ) => void;
  onFailure: (error: any) => void;
}

const GoogleLoginButton: React.FC<GoogleLoginButtonProps> = ({
  onSuccess,
  onFailure,
}) => {
  const clientId =
    "183279363920-jtmi73pgfp5cssr906tf23ndeuf0doji.apps.googleusercontent.com";

  useEffect(() => {
    gapi.load("client:auth2", () => {
      gapi.auth2.init({ clientId: clientId });
    });
  }, []);

  const handleSuccess = async (
    response: GoogleLoginResponse | GoogleLoginResponseOffline
  ) => {
    try {
      if ("tokenId" in response) {
        const idToken = response.tokenId;
        const profile = response.profileObj;
        console.log(77777777);

        // Send idToken to your backend
        const res: AxiosResponse = await APIs.post(endpoints["login-google"], {
          idToken: idToken,
          email: profile.email,
          name: profile.name,
          imageUrl: profile.imageUrl,
        });
        console.log(res.data);
        onSuccess(res.data); // Assuming res.data contains the token or relevant information
      } else {
        // Response is GoogleLoginResponseOffline
        onFailure("Offline login is not supported.");
      }
    } catch (error) {
      console.error("Failed to send ID token to backend:", error);
      onFailure(error);
    }
  };

  const handleFailure = (error: any) => {
    console.error("Google login failed:", error);
    onFailure(error);
  };

  return (
    <GoogleLogin
      clientId={clientId}
      buttonText="Đăng nhập với Google"
      onSuccess={handleSuccess}
      onFailure={handleFailure}
      cookiePolicy={"single_host_origin"}
      redirectUri="http://localhost:5173/sign-in"
    />
  );
};

export default GoogleLoginButton;
