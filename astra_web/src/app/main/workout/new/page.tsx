"use client";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { Card, CardContent } from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { cn } from "@/lib/utils";
import { createWorkout } from "@/services/fitness/data/fitness-repository";
import { Exercise, Run } from "@/services/fitness/domain/workout";
import { zodResolver } from "@hookform/resolvers/zod";
import { format } from "date-fns";
import dayjs from "dayjs";
import { CalendarIcon } from "lucide-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";

const schema = z.object({
  caloriesBurnt: z.number().int().nonnegative(),
  date: z.date(),
  duration: z.number().int().nonnegative(),
  intensity: z.string(),
  remarks: z.string(),
  title: z.string(),
  workoutType: z.string(),
});

type Schema = z.infer<typeof schema>;

export default function NewWorkoutPage() {
  const form = useForm<Schema>({
    defaultValues: {
      workoutType: "Running",
    },
    resolver: zodResolver(schema),
  });
  const [exercise, setExercise] = useState<Exercise>({
    name: "",
    sets: 3,
    reps: 10,
    weight: 0,
  });
  const [run, setRun] = useState<Run>({
    distance: 0,
    duration: 0,
  });
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const [runs, setRuns] = useState<Run[]>([]);

  const onSubmit = form.handleSubmit(
    async ({
      title,
      caloriesBurnt,
      date,
      duration,
      intensity,
      remarks,
      workoutType,
    }) => {
      try {
        await createWorkout({
          caloriesBurnt,
          date: dayjs(date, "YYYY-MM-DD").toDate(),
          duration,
          exercises,
          intensity,
          remarks,
          runs,
          title,
          workoutType,
        });
      } catch (e) {
        toast.error((e as Error).message);
      }
    }
  );

  return (
    <div className="flex flex-col">
      <div className="mb-6">
        <h1 className="text-3xl font-bold">Record Workout</h1>
        <p className="text-gray-500 dark:text-gray-400">
          Log your workout details and exercises
        </p>
      </div>

      <Card>
        <CardContent>
          <Form {...form}>
            <form onSubmit={onSubmit} className="space-y-4">
              <FormField
                control={form.control}
                name="title"
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Workout Title</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="e.g., Morning Run, Upper Body Strength..."
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  );
                }}
              />

              <div className="grid grid-cols-1 md:grid-cols-2 gap-y-4 md:gap-x-6">
                <FormField
                  control={form.control}
                  name="workoutType"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Workout Type</FormLabel>
                        <Select
                          onValueChange={field.onChange}
                          defaultValue={field.value}
                        >
                          <FormControl className="w-full">
                            <SelectTrigger>
                              <SelectValue placeholder="Select workout type" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="Gym">Gym</SelectItem>
                            <SelectItem value="Running">Running</SelectItem>
                          </SelectContent>
                        </Select>
                      </FormItem>
                    );
                  }}
                />

                <FormField
                  control={form.control}
                  name="date"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Date</FormLabel>
                        <Popover>
                          <PopoverTrigger asChild>
                            <Button
                              variant={"outline"}
                              className={cn(
                                "w-full pl-3 text-left font-normal",
                                !field.value && "text-muted-foreground"
                              )}
                            >
                              {field.value ? (
                                format(field.value, "PPP")
                              ) : (
                                <span>Pick a date</span>
                              )}
                              <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                            </Button>
                          </PopoverTrigger>
                          <PopoverContent className="w-auto p-0" align="start">
                            <Calendar
                              autoFocus
                              mode="single"
                              selected={field.value}
                              onSelect={field.onChange}
                              disabled={(date) =>
                                date > new Date() ||
                                date < new Date("1900-01-01")
                              }
                            />
                          </PopoverContent>
                        </Popover>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
