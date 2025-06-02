"use client";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

const profileFormSchema = z.object({
  // Personal information
  name: z.string().min(1, "Name is required").optional(),
  email: z.string().email("Invalid email address"),
  bio: z.string().max(500).optional(),
  gender: z.string().optional(),
  dateOfBirth: z.string().optional(), // Use a string for date input

  // Physical Stats
});

export type ProfileFormSchema = z.infer<typeof profileFormSchema>;

export default function Page() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ProfileFormSchema>({
    resolver: zodResolver(profileFormSchema),
    defaultValues: {
      name: "",
      email: "",
      bio: "",
    },
  });

  const onSubmit = handleSubmit(async (data) => {});

  return <div></div>;
}
