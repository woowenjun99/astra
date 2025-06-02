"use client";
import { Button, Card, Grid, Text, Textarea, TextInput } from "@mantine/core";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { notifications } from "@mantine/notifications";
import { auth } from "@/firebase";
import { updateUser } from "@/api/user";

const schema = z.object({
  bio: z.string().max(500).optional(),
  dateOfBirth: z.string().optional(),
  email: z.string().email("Invalid email address"),
  gender: z.string().optional(),
  name: z.string().optional(),
});

export type ProfileFormSchema = z.infer<typeof schema>;

export default function ProfileForm() {
  const {
    handleSubmit,
    formState: { errors, isSubmitting },
    register,
  } = useForm<ProfileFormSchema>({
    resolver: zodResolver(schema),
    defaultValues: async () => {
      const email = auth.currentUser?.email ?? "";
      return {
        name: "",
        email,
        bio: "",
      };
    },
  });

  const onSubmit = handleSubmit(async ({ email, name, bio }) => {
    try {
      await updateUser({ email, name, bio });
    } catch (error) {
      notifications.show({
        color: "red",
        message: (error as Error).message,
        title: "Error",
      });
    }
  });

  return (
    <Card withBorder mt="lg">
      <Text fw={500} mb="md" fs="lg">
        Personal Information
      </Text>
      <form onSubmit={onSubmit}>
        <Grid>
          <Grid.Col span={6}>
            <TextInput label="Name" {...register("name")} />
          </Grid.Col>
          <Grid.Col span={6}>
            <TextInput
              label="Email"
              required
              {...register("email")}
              error={
                errors.email === undefined ? undefined : errors.email.message
              }
            />
          </Grid.Col>
          <Grid.Col>
            <Textarea label="Bio" />
          </Grid.Col>
        </Grid>
        <Button type="submit" disabled={isSubmitting} loading={isSubmitting}>
          Save Changes
        </Button>
      </form>
    </Card>
  );
}
