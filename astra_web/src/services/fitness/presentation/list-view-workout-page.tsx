"use client";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState, useCallback, type FC, useContext } from "react";
import useSWR from "swr";
import { deleteWorkout, getWorkouts } from "../data/fitness-repository";
import type { Workout } from "../domain/workout";
import { Card, CardContent } from "@/components/ui/card";
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
import { Formatter } from "@/util/formatter";
import { format, isToday, isTomorrow, isYesterday } from "date-fns";
import { Clock, Ellipsis, Filter, Flame, Loader } from "lucide-react";
import {
  type Intensity,
  WorkoutPageContext,
  type WorkoutType,
} from "./workout-page-context";

interface WorkoutCardProps {
  workout: Workout;
}

const WorkoutCard: FC<WorkoutCardProps> = ({ workout }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const getDateLabel = () => {
    if (isToday(workout.date)) return "Today";
    if (isTomorrow(workout.date)) return "Tomorrow";
    if (isYesterday(workout.date)) return "Yesterday";
    return format(workout.date, "MMMM d, yyyy");
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
                  <Ellipsis />
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
                <Clock />
                {Formatter.formatTimeElapsed(workout.duration)}
              </div>

              <div className="flex items-center gap-1">
                <Flame />
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
              {isLoading ? <Loader className="animate-spin" /> : "Continue"}
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default function ListViewWorkoutPage() {
  const context = useContext(WorkoutPageContext);
  const clearFilter = useCallback(() => {
    context?.setIntensity("All Intensity");
    context?.setWorkoutType("All Types");
  }, [context]);
  const { data, isLoading } = useSWR(
    ["/fitness/workouts", 20, 0, context?.workoutType, context?.intensity],
    getWorkouts
  );

  return (
    <div className="flex flex-col">
      <div className="flex flex-col md:flex-row gap-4 md:gap-x-4 mb-5">
        <Select
          value={context?.workoutType}
          onValueChange={(e) => context?.setWorkoutType(e as WorkoutType)}
        >
          <SelectTrigger className="w-full md:w-32">
            <SelectValue placeholder="All Types" />
          </SelectTrigger>

          <SelectContent>
            <SelectItem value="All Types">All Types</SelectItem>
            <SelectItem value="Running">Running</SelectItem>
            <SelectItem value="Strength Training">Strength Training</SelectItem>
          </SelectContent>
        </Select>

        <Select
          value={context?.intensity}
          onValueChange={(e) => context?.setIntensity(e as Intensity)}
        >
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
          <Filter />
          Clear Filters
        </Button>
      </div>

      {isLoading ? (
        <Loader className="animate-spin" />
      ) : (
        data?.workouts.map((workout) => {
          return <WorkoutCard key={workout.id} workout={workout} />;
        })
      )}
    </div>
  );
}
