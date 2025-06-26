"use client";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import useSWR from "swr";
import { getWorkouts } from "../data/fitness-repository";
import {
  IconClock,
  IconFlame,
  IconLoader,
  IconGauge,
} from "@tabler/icons-react";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import type { Workout } from "../domain/workout";
import type { FC } from "react";
import dayjs from "dayjs";
import { cn } from "@/lib/utils";

type WorkoutCardProps = {
  workouts: Workout[];
};

const WorkoutCards: FC<WorkoutCardProps> = ({ workouts }) => {
  function convertDate(date: string) {
    const today = dayjs();
    const givenDate = dayjs(date);
    if (today.diff(givenDate, "days") === 1) {
      return "Yesterday";
    } else if (today.diff(givenDate, "days") === -1) {
      return "Tomorrow";
    }
    return `${today.diff(givenDate, "days")} days ago`;
  }

  return workouts.map((workout) => {
    return (
      <div
        key={workout.id}
        className="w-full rounded-lg border border-gray-200 dark:border-gray-800 shadow-sm p-4 flex flex-col"
      >
        <div className="flex flex-row justify-between">
          <h4 className="font-semibold">{workout.title}</h4>
          <div className="text-sm text-gray-500 dark:text-gray-400">
            {convertDate(workout.date)}
          </div>
        </div>
        <div className="flex flex-wrap mt-4 gap-4">
          <div className="flex items-center gap-1.5">
            <IconClock className="h-4 w-4 text-gray-500" />
            <span className="text-sm">{workout.duration} min</span>
          </div>

          <div className="flex items-center gap-1.5">
            <IconFlame className="h-4 w-4 text-orange-500" />
            <span className="text-sm">{workout.caloriesBurnt} kcal</span>
          </div>

          <div className="flex items-center gap-1.5">
            <IconGauge
              className={cn(
                "h-4 w-4",
                workout.intensity === "Low" && "text-green-600",
                workout.intensity === "Medium" && "text-yellow-600",
                workout.intensity === "High" && "text-red-600"
              )}
            />
            <span className="text-sm">{workout.intensity}</span>
          </div>
        </div>
      </div>
    );
  });
};

export default function RecentWorkoutDashboardCard() {
  const { data, isLoading } = useSWR(["/fitness/workouts", 3, 0], getWorkouts);

  return (
    <Card className="h-[500px] shadow-md">
      <CardHeader>
        <CardTitle>Recent Workouts</CardTitle>
        <CardDescription>Your latest training sessions</CardDescription>
      </CardHeader>
      <CardContent className="space-y-4 items-center justify-center flex flex-col">
        {isLoading ? (
          <IconLoader className="animate-spin" />
        ) : (
          <WorkoutCards workouts={data?.workouts ?? []} />
        )}
        <Link passHref href="/main/workout" className="no-underline w-full">
          <Button className="w-full" variant="outline" type="button">
            View Workout History
          </Button>
        </Link>
      </CardContent>
    </Card>
  );
}
