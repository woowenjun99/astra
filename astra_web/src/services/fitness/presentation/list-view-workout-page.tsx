"use client";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  IconClock,
  IconDots,
  IconFilter,
  IconFlame,
  IconLoader,
} from "@tabler/icons-react";
import { useState, useCallback, type FC } from "react";
import useSWR from "swr";
import { deleteWorkout, getWorkouts } from "../data/fitness-repository";
import type { Workout } from "../domain/workout";
import { Card, CardContent } from "@/components/ui/card";
import dayjs from "dayjs";
import { Badge } from "@/components/ui/badge";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
} from "@/components/ui/dropdown-menu";
import { DropdownMenuTrigger } from "@radix-ui/react-dropdown-menu";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { toast } from "sonner";
import { redirect } from "next/navigation";

interface WorkoutCardProps {
  workout: Workout;
}

const WorkoutCard: FC<WorkoutCardProps> = ({ workout }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const getDateLabel = () => {
    const today = dayjs();
    const givenDate = dayjs(workout.date);
    if (today.isSame(givenDate, "day")) return "Today";
    if (today.diff(givenDate, "day") === -1) return "Tomorrow";
    if (today.diff(givenDate, "day") === 1) return "Yesterday";
    return givenDate.format("MMMM D, YYYY");
  };

  const getIntensityColor = (intensity: string) => {
    switch (intensity) {
      case "Low":
        return "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-300";
      case "Medium":
        return "bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-300";
      case "High":
        return "bg-orange-100 text-orange-800 dark:bg-orange-900 dark:text-orange-300";
      case "Maximum":
        return "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-300";
      default:
        return "bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-300";
    }
  };

  const onClick = useCallback(async () => {
    try {
      setIsLoading(true);
      await deleteWorkout(workout.id);
      setIsOpen(false);
    } catch (e) {
      toast.error((e as Error).message);
    } finally {
      setIsLoading(false);
    }
  }, [workout.id]);

  return (
    <>
      <Card className="mb-4 overflow-hidden">
        <CardContent className="px-6">
          <div className="flex flex-col justify-start">
            <div className="flex flex-row justify-between">
              <div className="flex flex-col">
                <h1 className="font-semibold text-lg">{workout.title}</h1>
                <p className="text-sm text-gray-500 dark:text-gray-400">
                  {getDateLabel()} • {workout.workoutType}
                </p>
              </div>

              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <IconDots />
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuItem
                    onClick={() => redirect(`/main/workout/${workout.id}`)}
                  >
                    Edit
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={() => setIsOpen(true)}>
                    Delete
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>

            <div className="flex flex-wrap gap-4 text-sm text-gray-600 dark:text-gray-400 my-3">
              <div className="flex items-center gap-1">
                <IconClock />
                {workout.duration} min
              </div>

              <div className="flex items-center gap-1">
                <IconFlame />
                {workout.caloriesBurnt} cal
              </div>
            </div>

            <Badge
              className={getIntensityColor(workout.intensity)}
              variant="outline"
            >
              {workout.intensity}
            </Badge>
          </div>
        </CardContent>
      </Card>
      <AlertDialog open={isOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. This will permanently delete your
              workout and remove your data from our servers.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setIsOpen(false)}>
              Cancel
            </AlertDialogCancel>
            <AlertDialogAction disabled={isLoading} onClick={onClick}>
              {isLoading ? <IconLoader className="animate-spin" /> : "Continue"}
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default function ListViewWorkoutPage() {
  const [intensity, setIntensity] = useState("All Intensity");
  const [workoutType, setWorkoutType] = useState("All Types");
  const clearFilter = useCallback(() => {
    setIntensity("All Levels");
    setWorkoutType("All Intensity");
  }, []);
  const { data, isLoading } = useSWR(
    ["/fitness/workouts", 20, 0, workoutType, intensity],
    getWorkouts
  );

  return (
    <div className="flex flex-col">
      <div className="flex flex-col md:flex-row gap-4 md:gap-x-4 mb-5">
        <Select value={workoutType} onValueChange={setWorkoutType}>
          <SelectTrigger className="w-full md:w-32">
            <SelectValue placeholder="All Types" />
          </SelectTrigger>

          <SelectContent>
            <SelectItem value="All Types">All Types</SelectItem>
            <SelectItem value="Running">Running</SelectItem>
            <SelectItem value="Gym">Gym</SelectItem>
          </SelectContent>
        </Select>

        <Select value={intensity} onValueChange={setIntensity}>
          <SelectTrigger className="w-full md:w-32">
            <SelectValue placeholder="All Intensity" />
          </SelectTrigger>

          <SelectContent>
            <SelectItem value="All Intensity">All Intensity</SelectItem>
            <SelectItem value="Low">Low</SelectItem>
            <SelectItem value="Medium">Medium</SelectItem>
            <SelectItem value="High">High</SelectItem>
          </SelectContent>
        </Select>

        <Button onClick={clearFilter} variant="outline" type="button">
          <IconFilter />
          Clear Filters
        </Button>
      </div>

      {isLoading ? (
        <IconLoader className="animate-spin" />
      ) : (
        data?.workouts.map((workout) => {
          return <WorkoutCard key={workout.id} workout={workout} />;
        })
      )}
    </div>
  );
}
