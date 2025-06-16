"use client";
import {
  Box,
  Button,
  Card,
  Grid,
  LoadingOverlay,
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
import { DateInput, DateStringValue } from "@mantine/dates";
import { useState } from "react";
import dayjs from "dayjs";
import { getUser, updateUser } from "@/services/users/data/user-api";

const schema = z.object({
  bio: z.string().max(500).nullable(),
  email: z.string().email("Invalid email address"),
  name: z.string().nullable(),
});

export type ProfileFormSchema = z.infer<typeof schema>;

export default function ProfileForm() {
  const [dob, setDob] = useState<DateStringValue | null>(null);
  const [gender, setGender] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const {
    handleSubmit,
    formState: { errors, isSubmitting },
    register,
  } = useForm<ProfileFormSchema>({
    resolver: zodResolver(schema),
    defaultValues: async () => {
      const email = auth.currentUser?.email ?? "";
      const user = await getUser();
      setGender(user.gender);
      setDob(user.dateOfBirth);
      setIsLoading(false);
      return {
        name: user.fullName,
        email,
        bio: user.bio,
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
      notifications.show({
        color: "lime",
        message: "You have successfully updated your profile",
        title: "Success",
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
    <div
      style={{
        marginLeft: "auto",
        marginRight: "auto",
        marginTop: 16,
      }}
    >
      <form onSubmit={onSubmit}>
        <Card withBorder mt="lg">
          {isLoading ? (
            <Box pos="relative" h={400}>
              <LoadingOverlay visible={isLoading} />
            </Box>
          ) : (
            <>
              <Text fw={500} mb="md" fs="lg">
                Personal Information
              </Text>
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
                      errors.email === undefined
                        ? undefined
                        : errors.email.message
                    }
                  />
                </Grid.Col>
                <Grid.Col span={{ sm: 12, md: 6 }}>
                  <DateInput
                    label="Date of Birth"
                    value={dob}
                    onChange={setDob}
                  />
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
                <Button
                  type="submit"
                  disabled={isSubmitting}
                  loading={isSubmitting}
                >
                  Save Changes
                </Button>
              </div>
            </>
          )}
        </Card>
      </form>
    </div>
  );
}
