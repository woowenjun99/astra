"use client";
import {
  Anchor,
  Button,
  Divider,
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
import {
  createUser,
  signin,
} from "@/services/authentication/data/authentication-api";
import { useCallback, useState } from "react";
import { notifications } from "@mantine/notifications";
import { IconBrandGoogle } from "@tabler/icons-react";
import { GoogleAuthProvider, signInWithPopup } from "firebase/auth";
import { auth } from "@/firebase";

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

type Schema = z.infer<typeof schema>;

export default function AuthenticationImage() {
  const [isLogin, setIsLogin] = useState(true);
  const {
    formState: { errors, isSubmitting },
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
      if (!isLogin) {
        await createUser({ email, password, provider: "password", uid: null });
      }
      await signin(email, password);
    } catch (e) {
      notifications.show({
        color: "red",
        message: (e as Error).message,
        title: "Error",
      });
    }
  });

  const signInWithGoogle = useCallback(async () => {
    const provider = new GoogleAuthProvider();
    const user = await signInWithPopup(auth, provider);
    await createUser({
      email: user.user.email ?? "",
      password: null,
      provider: "google.com",
      uid: user.user.uid ?? null,
    });
  }, []);

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
            loading={isSubmitting}
            color="black"
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

          <Divider my={16} />
          <Button
            fullWidth
            size="md"
            radius="md"
            variant="outline"
            color="black"
            type="button"
            leftSection={<IconBrandGoogle />}
            onClick={signInWithGoogle}
          >
            Sign In With Google
          </Button>
        </Paper>
      </div>
    </form>
  );
}
