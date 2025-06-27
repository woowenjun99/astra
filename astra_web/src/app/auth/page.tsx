"use client";
import ThemeToggle from "@/components/theme-toggle";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { IconEye, IconEyeClosed, IconBrandGoogle } from "@tabler/icons-react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  signinWithGoogle,
  createUser,
  signin,
} from "@/services/authentication/data/authentication-repository";

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

type Schema = z.infer<typeof schema>;

export default function AuthPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [isLogin, setIsLogin] = useState(true);

  const form = useForm<Schema>({
    defaultValues: { email: "", password: "" },
    resolver: zodResolver(schema),
  });

  const onSubmit = form.handleSubmit(async ({ email, password }) => {
    if (!isLogin) {
      await createUser({ email, password, provider: "password", uid: null });
    }
    await signin(email, password);
  });

  return (
    <main>
      <div className="min-h-screen bg-gradient-to-br from-green-50 to-gray-100 dark:from-gray-900 dark:to-gray-800">
        <ThemeToggle />

        <div className="min-h-screen flex">
          <div className="flex flex-col md:w-[450px] p-8 pt-10 border w-full dark:bg-transparent bg-white">
            <h1 className="text-4xl font-bold">Welcome Back</h1>
            <h2 className="mb-4 font-light text-lg">
              Sign in to your account to continue
            </h2>
            <Form {...form}>
              <form onSubmit={onSubmit}>
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel htmlFor="Email" className="mb-0.5">
                          Email Address
                        </FormLabel>
                        <FormControl>
                          <Input
                            autoFocus
                            className="mb-4"
                            placeholder="name@example.com"
                            required
                            type="email"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />

                <FormField
                  control={form.control}
                  name="password"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel htmlFor="Password" className="mb-0.5">
                          Password
                        </FormLabel>
                        <FormControl>
                          <div className="relative mb-8">
                            <Input
                              placeholder="Your password"
                              required
                              type={showPassword ? "text" : "password"}
                              {...field}
                            />
                            <Button
                              className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
                              onClick={() => setShowPassword(!showPassword)}
                              type="button"
                              variant="ghost"
                            >
                              {showPassword ? (
                                <IconEye className="text-gray-400 h-4 w-4" />
                              ) : (
                                <IconEyeClosed className="text-gray-400 h-4 w-4" />
                              )}
                            </Button>
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />

                <Button
                  className="w-full bg-green-600 hover:bg-green-700 text-white font-bold text-md"
                  disabled={form.formState.isSubmitting}
                >
                  {isLogin ? "Login" : "Register"}
                </Button>
              </form>
            </Form>

            <div
              className="flex flex-row items-center justify-center mt-4"
              onClick={() => setIsLogin(!isLogin)}
            >
              <div>
                {isLogin
                  ? "Don't have an account? "
                  : "Already have an account? "}
              </div>
              <div className="hover:underline pl-2 font-bold">
                {isLogin ? "Register" : "Login"}
              </div>
            </div>

            <hr className="my-7" />

            <Button
              className="flex flex-row w-full py-2"
              onClick={signinWithGoogle}
              type="button"
              variant="outline"
            >
              <IconBrandGoogle />
              <div className="font-bold text-md">Sign In With Google</div>
            </Button>
          </div>
        </div>
      </div>
    </main>
  );
}
