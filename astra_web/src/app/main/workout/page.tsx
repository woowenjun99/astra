"use client";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import WorkoutMetadataCards from "@/services/fitness/presentation/workout-metadata-cards";
import ListViewWorkoutPage from "@/services/fitness/presentation/list-view-workout-page";
import { IconPlus } from "@tabler/icons-react";
import Link from "next/link";

export default function WorkoutPage() {
  return (
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
            <IconPlus />
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
  );
}
