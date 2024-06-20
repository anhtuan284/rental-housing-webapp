import * as z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";

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
import { useContext, useState } from "react";
import APIs, { authApi, endpoints } from "@/configs/APIs";
import { AxiosResponse } from "axios";
import UserContext from "@/context/UserContext";
import { transformToIUser } from "@/lib/utils";
import { ToastAction } from "@/components/ui/toast";
import Cookies from "js-cookie";
import { loginFirebase } from "@/configs/firebase";

const SigninForm = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [loading, setLoading] = useState<Boolean>(false);
  const { user, dispatch } = useContext(UserContext);

  const form = useForm<z.infer<typeof SigninValidation>>({
    resolver: zodResolver(SigninValidation),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const handleSignin = async (
    validated_user: z.infer<typeof SigninValidation>
  ) => {
    setLoading(true);
    try {
      let res: AxiosResponse = await APIs.post(endpoints["login"], {
        username: validated_user.username,
        password: validated_user.password,
      });

      Cookies.set("access_token", res.data, { expires: 3 });

      const res2 = await authApi(res.data).get(endpoints["current-user"]);
      const currentUser = res2.data;

      let transUser = transformToIUser(currentUser);
      loginFirebase(transUser);

      dispatch({
        type: "login",
        payload: transUser,
      });

      toast({
        title: "Logged in successfully",
        description: "Welcome back!",
        action: <ToastAction altText="OK">OK</ToastAction>,
      });

      navigate("/");
    } catch (ex: any) {
      if (ex.response && ex.response.status === 400) {
        toast({
          title: "Cannot login",
          description: "Please check your username and password !",
        });
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
          Log in to your account
        </h2>
        <p className="text-light-3 small-medium md:base-regular mt-2">
          Welcome back! Please enter your details.
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
                <FormLabel className="shad-form_label">Username</FormLabel>
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
                <FormLabel className="shad-form_label">Password</FormLabel>
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
                <Loader /> Loading...
              </div>
            ) : (
              "Log in"
            )}
          </Button>

          <p className="text-small-regular text-light-2 text-center mt-2">
            Don&apos;t have an account?
            <Link
              to="/sign-up"
              className="text-primary-500 text-small-semibold ml-1"
            >
              Sign up
            </Link>
          </p>
        </form>
      </div>
    </Form>
  );
};

export default SigninForm;
