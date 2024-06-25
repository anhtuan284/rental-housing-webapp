import React, { useState, useContext } from "react";
import GoogleLogin, {
  GoogleLoginResponse,
  GoogleLoginResponseOffline,
} from "react-google-login";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";
import * as z from "zod";

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Loader } from "@/components/shared/";
import { useToast } from "@/components/ui/use-toast";

import { SigninValidation } from "@/lib/validation";
import APIs, { authApi, endpoints } from "@/configs/APIs";
import { AxiosResponse } from "axios";
import UserContext from "@/context/UserContext";
import { transformToIUser } from "@/lib/utils";
import { ToastAction } from "@/components/ui/toast";
import Cookies from "js-cookie";
import { loginFirebase } from "@/configs/firebase";
import GoogleLoginButton from "@/components/shared/GoogleLoginButton";

const SigninForm = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(false);
  const { dispatch } = useContext(UserContext);

  const form = useForm<z.infer<typeof SigninValidation>>({
    resolver: zodResolver(SigninValidation),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const handleGoogleLoginSuccess = async (token: any) => {
    Cookies.set("access_token", token, { expires: 3 });
    console.log(token);
    const res2 = await authApi(token).get(endpoints["current-user"]);
    const currentUser = res2.data;
    console.log("12123123123");
    console.log(currentUser);
    const transformedUser = transformToIUser(currentUser);
    loginFirebase(transformedUser);

    dispatch({
      type: "login",
      payload: transformedUser,
    });

    toast({
      title: "Đăng nhập thành công",
      description: "Chào mừng bạn trở lại!",
      action: <ToastAction altText="OK">OK</ToastAction>,
    });

    navigate("/");
  };

  const handleGoogleLoginFailure = (error: any) => {
    // Handle failed Google login
    toast({
      title: "Không thể đăng nhập",
      description: "Đăng nhập bằng Google thất bại!",
    });
    console.error("Failed to login with Google:", error);
  };

  const handleSignin = async (
    validatedUser: z.infer<typeof SigninValidation>
  ) => {
    setLoading(true);
    try {
      const res: AxiosResponse = await APIs.post(endpoints["login"], {
        username: validatedUser.username,
        password: validatedUser.password,
      });

      Cookies.set("access_token", res.data, { expires: 3 });

      const res2 = await authApi(res.data).get(endpoints["current-user"]);
      const currentUser = res2.data;

      const transformedUser = transformToIUser(currentUser);
      loginFirebase(transformedUser);

      dispatch({
        type: "login",
        payload: transformedUser,
      });

      toast({
        title: "Đăng nhập thành công",
        description: "Chào mừng bạn trở lại!",
        action: <ToastAction altText="OK">OK</ToastAction>,
      });

      navigate("/");
    } catch (ex: any) {
      if (ex.response && ex.response.status === 400) {
        toast({
          title: "Không thể đăng nhập",
          description: "Vui lòng kiểm tra tên người dùng và mật khẩu của bạn!",
        });
      } else {
        console.error("Lỗi trong quá trình đăng nhập:", ex);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Form {...form}>
      <div className="sm:w-420 flex-center flex-col">
        <img src="/assets/images/logo.svg" alt="logo" />

        <h2 className="h3-bold md:h2-bold pt-5 sm:pt-12">
          Đăng nhập vào tài khoản của bạn
        </h2>
        <p className="text-light-3 small-medium md:base-regular mt-2">
          Chào mừng bạn trở lại! Vui lòng nhập thông tin của bạn.
        </p>
        <form
          onSubmit={form.handleSubmit(handleSignin)}
          className="flex flex-col gap-5 w-full mt-4"
        >
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormLabel className="shad-form_label">
                  Tên người dùng
                </FormLabel>
                <FormControl>
                  <Input type="text" className="shad-input" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormLabel className="shad-form_label">Mật khẩu</FormLabel>
                <FormControl>
                  <Input type="password" className="shad-input" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <Button type="submit" className="shad-button_primary">
            {loading ? (
              <div className="flex-center gap-2">
                <Loader /> Đang tải...
              </div>
            ) : (
              "Đăng nhập"
            )}
          </Button>

          <GoogleLoginButton
            onSuccess={handleGoogleLoginSuccess}
            onFailure={handleGoogleLoginFailure}
          />
          <p className="text-small-regular text-light-2 text-center mt-2">
            Chưa có tài khoản?
            <Link
              to="/sign-up"
              className="text-primary-500 text-small-semibold ml-1"
            >
              Đăng ký
            </Link>
          </p>
        </form>
      </div>
    </Form>
  );
};

export default SigninForm;
