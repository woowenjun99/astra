"use client";
import {
  Anchor,
  Button,
  Paper,
  PasswordInput,
  Text,
  TextInput,
  Title,
} from "@mantine/core";
import classes from "./AuthenticationImage.module.css";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { createUser, signin } from "@/api/user";
import { useState } from "react";
import { notifications } from "@mantine/notifications";

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

type Schema = z.infer<typeof schema>;

export default function AuthenticationImage() {
  const [isLogin, setIsLogin] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const {
    formState: { errors },
    register,
    handleSubmit,
  } = useForm<Schema>({
    resolver: zodResolver(schema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const submit = handleSubmit(async ({ email, password }) => {
    try {
      setIsLoading(true);
      if (!isLogin) await createUser(email, password);
      await signin(email, password);
    } catch (e) {
      notifications.show({
        color: "red",
        message: (e as Error).message,
        title: "Error",
      });
    } finally {
      setIsLoading(false);
    }
  });

  return (
    <form onSubmit={submit}>
      <div className={classes.wrapper}>
        <Paper className={classes.form}>
          <Title order={1}>Welcome back</Title>
          <Title
            order={2}
            style={{
              fontSize: 18,
              marginBottom: 16,
              opacity: 0.5,
              fontWeight: 400,
            }}
          >
            Sign in to your account to continue
          </Title>

          <TextInput
            label="Email address"
            placeholder="name@example.com"
            size="md"
            radius="md"
            error={errors.email ? errors.email.message : undefined}
            {...register("email")}
          />
          <PasswordInput
            label="Password"
            placeholder="Your password"
            mt="md"
            size="md"
            radius="md"
            error={errors.password ? errors.password.message : undefined}
            {...register("password")}
          />

          <Button
            fullWidth
            mt="xl"
            size="md"
            radius="md"
            type="submit"
            loading={isLoading}
          >
            {isLogin ? "Login" : "Register"}
          </Button>

          <Text ta="center" mt="md">
            {isLogin ? "Don't have an account? " : "Already have an account? "}
            <Anchor
              href="#"
              fw={500}
              onClick={(event) => {
                event.preventDefault();
                setIsLogin(!isLogin);
              }}
            >
              {isLogin ? "Register" : "Login"}
            </Anchor>
          </Text>
        </Paper>
      </div>
    </form>
  );
}
