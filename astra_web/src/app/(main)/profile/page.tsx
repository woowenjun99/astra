"use client";
import ProfileForm from "@/component/user/ProfileForm";
import { zodResolver } from "@hookform/resolvers/zod";
import { Tabs } from "@mantine/core";
import { useForm } from "react-hook-form";
import { z } from "zod";

const profileFormSchema = z.object({
  name: z.string().min(1, "Name is required").optional(),
  email: z.string().email("Invalid email address"),
  bio: z.string().max(500).optional(),
  gender: z.string().optional(),
  dateOfBirth: z.string().optional(),
});

export type ProfileFormSchema = z.infer<typeof profileFormSchema>;

export default function Page() {
  const {} = useForm<ProfileFormSchema>({
    resolver: zodResolver(profileFormSchema),
    defaultValues: {
      name: "",
      email: "",
      bio: "",
      gender: "",
      dateOfBirth: "",
    },
  });

  return (
    <div style={{ width: "100%", margin: 16 }}>
      <Tabs defaultValue="personal" variant="pills">
        <Tabs.List justify="center" grow>
          <Tabs.Tab value="personal">Personal</Tabs.Tab>
          <Tabs.Tab value="stats">Physical Stats</Tabs.Tab>
          <Tabs.Tab value="fitness">Fitness Goals</Tabs.Tab>
          <Tabs.Tab value="account">Account</Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="personal">
          <ProfileForm />
        </Tabs.Panel>
      </Tabs>
    </div>
  );
}
