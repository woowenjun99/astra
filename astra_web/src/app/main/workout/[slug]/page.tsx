"use client";
import WorkoutForm from "@/services/fitness/presentation/workout-form";
import { useParams } from "next/navigation";

export default function EditWorkoutFormPage() {
  const params = useParams();

  return <WorkoutForm id={parseInt(params?.slug as string)} />;
}
