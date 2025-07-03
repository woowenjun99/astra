"use client";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import WorkoutMetadataCards from "@/services/fitness/presentation/workout-metadata-cards";
import ListViewWorkoutPage from "@/services/fitness/presentation/list-view-workout-page";
import Link from "next/link";
import { Plus } from "lucide-react";
import {
  Intensity,
  WorkoutPageContext,
  WorkoutType,
} from "@/services/fitness/presentation/workout-page-context";
import { useState } from "react";

export default function WorkoutPage() {
  const [workoutType, setWorkoutType] = useState<WorkoutType>("All Types");
  const [intensity, setIntensity] = useState<Intensity>("All Intensity");

  return (
    <WorkoutPageContext.Provider
      value={{ workoutType, setWorkoutType, intensity, setIntensity }}
    >
      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row justify-between mb-5">
          <div className="mb-5 md:mb-2">
            <h1 className="text-3xl font-bold">My Workouts</h1>
            <p className="text-gray-500 dark:text-gray-400">
              Track and manage all your fitness activities
            </p>
          </div>

          <Link passHref href="/main/workout/new">
            <Button className="bg-green-600 hover:bg-green-700" type="button">
              <Plus />
              Record Workout
            </Button>
          </Link>
        </div>

        <WorkoutMetadataCards />

        <Tabs defaultValue="list" className="w-full mt-5">
          <div className="flex items-center justify-between mb-6">
            <TabsList>
              <TabsTrigger value="list">List View</TabsTrigger>
              <TabsTrigger value="calendar">Calendar View</TabsTrigger>
              <TabsTrigger value="analytics">Analytics View</TabsTrigger>
            </TabsList>
          </div>
          <TabsContent value="list">
            <ListViewWorkoutPage />
          </TabsContent>
        </Tabs>
      </div>
    </WorkoutPageContext.Provider>
  );
}
