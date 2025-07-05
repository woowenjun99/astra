"use client";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import type { FC } from "react";
import { Button } from "@/components/ui/button";
import { CalendarIcon } from "lucide-react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { toast } from "sonner";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Checkbox } from "@/components/ui/checkbox";
import { createScheduledWorkout } from "../data/schedule-repository";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { Calendar } from "@/components/ui/calendar";
import { Textarea } from "@/components/ui/textarea";

type ScheduledWorkoutDialogProps = {
  id?: string;
};

const schema = z.object({
  date: z.date(),
  duration: z.number().positive(),
  frequency: z.enum(["Daily", "Weekly", "Every 2 weeks", "Monthly"]).optional(),
  isRecurring: z.boolean(),
  remarks: z.string(),
  shouldSendReminder: z.boolean(),
  time: z.string().time(),
  title: z.string(),
  workoutType: z.enum(["Running", "Strength Training"]),
});

type Schema = z.infer<typeof schema>;

const ScheduledWorkoutDialog: FC<ScheduledWorkoutDialogProps> = () => {
  const form = useForm<Schema>({
    defaultValues: {
      date: new Date(),
      duration: 0,
      isRecurring: false,
      remarks: "",
      shouldSendReminder: false,
      time: "00:00:00",
      title: "",
      workoutType: "Strength Training",
    },
    resolver: zodResolver(schema),
  });

  const onSubmit = form.handleSubmit(async (values) => {
    try {
      await createScheduledWorkout(values);
    } catch (e) {
      toast.error((e as Error).message);
    }
  });

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>
          <CalendarIcon />
          Schedule Workout
        </Button>
      </DialogTrigger>
      <DialogContent className="max-w-[425px]">
        <Form {...form}>
          <form onSubmit={onSubmit} className="space-y-4">
            <DialogHeader>
              <DialogTitle>Schedule Workout</DialogTitle>
              <DialogDescription>
                Plan your upcoming training session
              </DialogDescription>
            </DialogHeader>

            <FormField
              control={form.control}
              name="title"
              render={({ field }) => {
                return (
                  <FormItem>
                    <FormLabel>Workout Title</FormLabel>
                    <FormControl>
                      <Input autoFocus type="text" {...field} />
                    </FormControl>
                  </FormItem>
                );
              }}
            />

            <FormField
              control={form.control}
              name="workoutType"
              render={({ field }) => {
                return (
                  <FormItem>
                    <FormLabel>Workout Type</FormLabel>
                    <Select
                      defaultValue={field.value}
                      onValueChange={field.onChange}
                      value={field.value}
                    >
                      <FormControl className="w-full">
                        <SelectTrigger>
                          <SelectValue placeholder="Please select a type of workout" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="Strength Training">
                          Strength Training
                        </SelectItem>
                        <SelectItem value="Running">Running</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                );
              }}
            />

            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-4">
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
                            disabled={(date) => date < new Date()}
                          />
                        </PopoverContent>
                      </Popover>
                      <FormMessage />
                    </FormItem>
                  );
                }}
              />

              <FormField
                control={form.control}
                name="time"
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Time</FormLabel>
                      <FormControl>
                        <Input
                          defaultValue="00:00:00"
                          step="30"
                          type="time"
                          {...field}
                        />
                      </FormControl>
                    </FormItem>
                  );
                }}
              />
            </div>

            <FormField
              control={form.control}
              name="duration"
              render={({ field }) => {
                return (
                  <FormItem>
                    <FormLabel>Duration (Min)</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        value={field.value}
                        onChange={(e) =>
                          field.onChange(
                            isNaN(parseInt(e.target.value))
                              ? 0
                              : parseInt(e.target.value)
                          )
                        }
                      />
                    </FormControl>
                  </FormItem>
                );
              }}
            />

            <FormField
              control={form.control}
              name="remarks"
              render={({ field }) => {
                return (
                  <FormItem>
                    <FormLabel>Notes (Optional)</FormLabel>
                    <FormControl>
                      <Textarea
                        placeholder="Any notes about this workout"
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                );
              }}
            />

            <FormField
              control={form.control}
              name="shouldSendReminder"
              render={({ field }) => {
                return (
                  <FormItem className="flex flex-row space-x-2">
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <FormLabel>
                      Send reminder notification 30 minutes before the workout
                    </FormLabel>
                  </FormItem>
                );
              }}
            />

            <FormField
              control={form.control}
              name="isRecurring"
              render={({ field }) => {
                return (
                  <FormItem className="flex flex-row space-x-2">
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <FormLabel>Make this a recurring workout</FormLabel>
                  </FormItem>
                );
              }}
            />

            {form.watch("isRecurring") && (
              <FormField
                control={form.control}
                name="frequency"
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Repeat</FormLabel>
                      <Select
                        onValueChange={field.onChange}
                        value={field.value}
                      >
                        <FormControl className="w-full">
                          <SelectTrigger>
                            <SelectValue placeholder="Select frequency" />
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          <SelectItem value="Daily">Daily</SelectItem>
                          <SelectItem value="Weekly">Weekly</SelectItem>
                          <SelectItem value="Every 2 weeks">
                            Every 2 weeks
                          </SelectItem>
                          <SelectItem value="Monthly">Monthly</SelectItem>
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  );
                }}
              />
            )}

            <DialogFooter>
              <Button className="bg-green-500 hover:bg-green-600" type="submit">
                Schedule Workout
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default ScheduledWorkoutDialog;
