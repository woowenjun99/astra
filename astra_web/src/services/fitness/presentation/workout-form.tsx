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
import { Label } from "@/components/ui/label";
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
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Textarea } from "@/components/ui/textarea";
import { cn } from "@/lib/utils";
import {
  createWorkout,
  getWorkout,
} from "@/services/fitness/data/fitness-repository";
import { Exercise, Run } from "@/services/fitness/domain/workout";
import { zodResolver } from "@hookform/resolvers/zod";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { FC, useState } from "react";
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

function ExerciseRunTable({
  exercises,
  workoutType,
  runs,
}: {
  exercises: Exercise[];
  runs: Run[];
  workoutType: string;
}) {
  if (workoutType === "Running") {
    return (
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Lap</TableHead>
            <TableHead>Duration</TableHead>
            <TableHead>Distance</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {runs.map((run, index) => {
            return (
              <TableRow key={index}>
                <TableCell>{index}</TableCell>
                <TableCell>{run.duration}</TableCell>
                <TableCell>{run.distance}</TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    );
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Exercise</TableHead>
          <TableHead>Sets</TableHead>
          <TableHead>Reps</TableHead>
          <TableHead>Weight</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {exercises.map((exercise, index) => {
          return (
            <TableRow key={index}>
              <TableCell>{index}</TableCell>
              <TableCell>{exercise.name}</TableCell>
              <TableCell>{exercise.sets}</TableCell>
              <TableCell>{exercise.reps}</TableCell>
              <TableCell>{exercise.weight}</TableCell>
            </TableRow>
          );
        })}
      </TableBody>
    </Table>
  );
}

interface WorkoutFormProps {
  id?: number;
}

const WorkoutForm: FC<WorkoutFormProps> = ({ id }) => {
  const form = useForm<Schema>({
    defaultValues: async () => {
      if (id === undefined) {
        return {
          caloriesBurnt: 0,
          date: new Date(),
          duration: 0,
          intensity: "Low",
          remarks: "",
          title: "",
          workoutType: "Running",
        };
      }
      const data = await getWorkout(id);
      setExercises(data.exercises);
      setRuns(data.runs);
      return {
        caloriesBurnt: data.workout.caloriesBurnt,
        date: data.workout.date,
        duration: data.workout.duration,
        intensity: data.workout.intensity,
        remarks: "",
        title: data.workout.title,
        workoutType: data.workout.workoutType,
      };
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
          date,
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
      <div className="mb-6 flex flex-row justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold">Record Workout</h1>
          <p className="text-gray-500 dark:text-gray-400">
            Log your workout details and exercises
          </p>
        </div>

        <Button
          onClick={() => window.history.back()}
          type="button"
          variant="outline"
        >
          Cancel
        </Button>
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
                          value={field.value}
                        >
                          <FormControl className="w-full">
                            <SelectTrigger>
                              <SelectValue placeholder="Select workout type" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="Strength Training">
                              Strength Training
                            </SelectItem>
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

              <div className="grid grid-cols-1 md:grid-cols-3 gap-y-4 md:gap-x-6">
                <FormField
                  control={form.control}
                  name="duration"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Duration (Minutes)</FormLabel>
                        <FormControl>
                          <Input {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />

                <FormField
                  control={form.control}
                  name="intensity"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Intensity</FormLabel>
                        <Select
                          onValueChange={field.onChange}
                          value={field.value}
                        >
                          <FormControl className="w-full">
                            <SelectTrigger>
                              <SelectValue placeholder="Select intensity" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="Low">Low</SelectItem>
                            <SelectItem value="Medium">Medium</SelectItem>
                            <SelectItem value="High">High</SelectItem>
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />

                <FormField
                  control={form.control}
                  name="caloriesBurnt"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Calories Burnt</FormLabel>
                        <FormControl>
                          <Input {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    );
                  }}
                />
              </div>

              <div>
                <h3 className="mb-4 text-lg font-medium">Exercises</h3>
                <div className="mb-4 rounded-md border bg-gray-50 p-4 dark:border-gray-700 dark:bg-gray-800 w-full">
                  {form.watch("workoutType") === "Running" ? (
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-y-4 md:gap-x-6">
                      <div className="flex flex-col space-y-2">
                        <Label>Distance (m)</Label>
                        <Input
                          onChange={(e) =>
                            setRun({
                              ...run,
                              distance: parseInt(e.target.value),
                            })
                          }
                          value={run.distance}
                        />
                      </div>

                      <div className="flex flex-col space-y-2">
                        <Label>Duration (seconds)</Label>
                        <Input
                          onChange={(e) =>
                            setRun({
                              ...run,
                              duration: parseInt(e.target.value),
                            })
                          }
                          value={run.duration}
                        />
                      </div>

                      <Button
                        className="mt-5"
                        onClick={() => {
                          runs.push(run);
                          setRuns(runs);
                          setRun({
                            distance: 0,
                            duration: 0,
                          });
                        }}
                        variant="outline"
                        type="button"
                      >
                        Add
                      </Button>
                    </div>
                  ) : (
                    <div className="grid grid-cols-1 md:grid-cols-5 gap-y-4 md:gap-x-6">
                      <div className="flex flex-col space-y-2">
                        <Label>Exercise</Label>
                        <Input
                          onChange={(e) =>
                            setExercise({
                              ...exercise,
                              name: e.target.value,
                            })
                          }
                          value={exercise.name}
                        />
                      </div>

                      <div className="flex flex-col space-y-2">
                        <Label>Sets</Label>
                        <Input
                          onChange={(e) =>
                            setExercise({
                              ...exercise,
                              sets: parseInt(e.target.value),
                            })
                          }
                          value={exercise.sets}
                        />
                      </div>

                      <div className="flex flex-col space-y-2">
                        <Label>Reps</Label>
                        <Input
                          onChange={(e) =>
                            setExercise({
                              ...exercise,
                              reps: parseInt(e.target.value),
                            })
                          }
                          value={exercise.reps}
                        />
                      </div>

                      <div className="flex flex-col space-y-2">
                        <Label>Weight (kg)</Label>
                        <Input
                          onChange={(e) =>
                            setExercise({
                              ...exercise,
                              weight: parseInt(e.target.value),
                            })
                          }
                          value={exercise.weight}
                        />
                      </div>

                      <Button
                        className="mt-5"
                        onClick={() => {
                          exercises.push(exercise);
                          setExercises(exercises);
                          setExercise({
                            name: "",
                            sets: 3,
                            reps: 10,
                            weight: 0,
                          });
                        }}
                        variant="outline"
                        type="button"
                      >
                        Add
                      </Button>
                    </div>
                  )}
                </div>
                <ExerciseRunTable
                  exercises={exercises}
                  runs={runs}
                  workoutType={form.watch("workoutType")}
                />
              </div>

              <FormField
                control={form.control}
                name="remarks"
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Notes</FormLabel>
                      <FormControl>
                        <Textarea
                          placeholder="Any additional notes about your workout..."
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  );
                }}
              />

              <div className="md:flex md:flex-row md:justify-end mt-5">
                <Button className="w-full md:w-fit">Save Workout</Button>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
};

export default WorkoutForm;
