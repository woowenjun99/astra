"use client";
import {
  Button,
  Card,
  Grid,
  Select,
  Text,
  Textarea,
  TextInput,
} from "@mantine/core";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { notifications } from "@mantine/notifications";
import { auth } from "@/firebase";
import { updateUser } from "@/api/user";
import { DateInput, DateStringValue } from "@mantine/dates";
import { useState } from "react";
import dayjs from "dayjs";

const schema = z.object({
  bio: z.string().max(500).nullable(),
  email: z.string().email("Invalid email address"),
  name: z.string().nullable(),
});

export type ProfileFormSchema = z.infer<typeof schema>;

export default function ProfileForm() {
  const [dob, setDob] = useState<DateStringValue | null>(null);
  const [gender, setGender] = useState<string | null>(null);
  const {
    handleSubmit,
    formState: { errors, isSubmitting },
    register,
  } = useForm<ProfileFormSchema>({
    resolver: zodResolver(schema),
    defaultValues: async () => {
      const email = auth.currentUser?.email ?? "";
      return {
        name: null,
        email,
        bio: null,
      };
    },
  });

  const onSubmit = handleSubmit(async ({ email, name, bio }) => {
    try {
      await updateUser({
        email,
        name,
        bio,
        dob: dayjs(dob, "YYYY-MM-DD").toDate(),
        gender,
      });
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
          <Grid.Col>
            <TextInput label="Full Name" {...register("name")} />
          </Grid.Col>
          <Grid.Col>
            <TextInput
              label="Email"
              required
              {...register("email")}
              error={
                errors.email === undefined ? undefined : errors.email.message
              }
            />
          </Grid.Col>
          <Grid.Col span={{ sm: 12, md: 6 }}>
            <DateInput label="Date of Birth" value={dob} onChange={setDob} />
          </Grid.Col>
          <Grid.Col span={{ sm: 12, md: 6 }}>
            <Select
              label="Gender"
              value={gender}
              onChange={setGender}
              data={[
                "Male",
                "Female",
                "Non-binary",
                "Others",
                "Prefer not to say",
              ]}
            />
          </Grid.Col>
          <Grid.Col>
            <Textarea label="Bio" mb="lg" {...register("bio")} />
          </Grid.Col>
        </Grid>
        <div style={{ display: "flex", justifyContent: "flex-end" }}>
          <Button type="submit" disabled={isSubmitting} loading={isSubmitting}>
            Save Changes
          </Button>
        </div>
      </form>
    </Card>
  );
}
