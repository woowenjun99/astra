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
import { Button } from "@/components/ui/button";
import Link from "next/link";
import type { Workout } from "../domain/workout";
import type { FC } from "react";
import { cn } from "@/lib/utils";
import { Clock, Dumbbell, Flame, Gauge, Loader } from "lucide-react";
import { Formatter } from "@/util/formatter";
import {
  isToday,
  isTomorrow,
  isYesterday,
  differenceInCalendarDays,
} from "date-fns";

type WorkoutCardProps = {
  workouts: Workout[];
};

const WorkoutCards: FC<WorkoutCardProps> = ({ workouts }) => {
  function convertDate(date: Date) {
    if (isYesterday(date)) {
      return "Yesterday";
    } else if (isTomorrow(date)) {
      return "Tomorrow";
    } else if (isToday(date)) {
      return "Today";
    }
    return `${differenceInCalendarDays(new Date(), date)} days ago`;
  }

  if (workouts.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center h-full">
        <Dumbbell className="mx-auto h-16 w-16 text-gray-400 dark:text-gray-600" />
        <h3 className="mt-4 text-xl font-medium text-gray-900 dark:text-gray-100">
          No workouts yet
        </h3>
        <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">
          Start your fitness journey by recording your first workout
        </p>
        <Link passHref href="/main/workout/new">
          <Button
            className="mt-6 bg-green-600 hover:bg-green-700"
            type="button"
          >
            Create Workout
          </Button>
        </Link>
      </div>
    );
  }

  return (
    <div className="space-y-4 items-center justify-center flex flex-col">
      {workouts.map((workout) => {
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
                <Clock className="h-4 w-4 text-gray-500" />
                <span className="text-sm">
                  {Formatter.formatTimeElapsed(workout.duration)}
                </span>
              </div>

              <div className="flex items-center gap-1.5">
                <Flame className="h-4 w-4 text-orange-500" />
                <span className="text-sm">{workout.caloriesBurnt} kcal</span>
              </div>

              <div className="flex items-center gap-1.5">
                <Gauge
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
      })}

      <Link passHref href="/main/workout" className="no-underline w-full">
        <Button className="w-full" variant="outline" type="button">
          View Workout History
        </Button>
      </Link>
    </div>
  );
};

export default function RecentWorkoutDashboardCard() {
  const { data, isLoading } = useSWR(["/fitness/workouts", 3, 0], getWorkouts);

  return (
    <Card className="h-[500px] shadow-md">
      <CardHeader>
        <CardTitle>Recent Workouts</CardTitle>
        <CardDescription>Your latest training sessions</CardDescription>
      </CardHeader>
      <CardContent className="h-full">
        {isLoading ? (
          <div className="flex items-center justify-center h-full">
            <Loader className="animate-spin" />
          </div>
        ) : (
          <WorkoutCards workouts={data?.workouts ?? []} />
        )}
      </CardContent>
    </Card>
  );
}
