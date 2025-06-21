"use client";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@mantine/core";
import { IconPlus } from "@tabler/icons-react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const schema = z.object({
  category: z.string(),
  description: z.string().nullable(),
  targetDate: z.string(),
  targetValue: z.number(),
  title: z.string(),
});

type Schema = z.infer<typeof schema>;

export default function FitnessGoalPage() {
  const form = useForm<Schema>({
    resolver: zodResolver(schema),
  });

  const onSubmit = form.handleSubmit(async () => {});

  return (
    <main>
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-bold">Fitness Goals</h1>
          <p className="text-gray-500 dark:text-gray-400">
            Track and manage your fitness objectives
          </p>
        </div>

        <Dialog>
          <DialogTrigger asChild>
            <Button className="bg-green-600 hover:bg-green-700">
              <IconPlus />
              Add New Goal
            </Button>
          </DialogTrigger>

          <DialogContent className="sm:max-w-[500px]">
            <DialogTitle>Create New Goal</DialogTitle>
            <DialogDescription>
              Set a new fitness goal to track your progress
            </DialogDescription>

            <Form {...form}>
              <form onSubmit={onSubmit} className="space-y-6">
                <FormField
                  control={form.control}
                  name="title"
                  render={({ field }) => {
                    return (
                      <FormItem>
                        <FormLabel>Goal Title</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="e.g. Weight Loss, Run 5 KM"
                            required
                            {...field}
                          />
                        </FormControl>
                      </FormItem>
                    );
                  }}
                />

                <div className="grid sm:grid-cols-2 gap-6">
                  <FormField
                    control={form.control}
                    name="category"
                    render={({ field }) => {
                      return (
                        <FormItem>
                          <FormLabel>Category</FormLabel>
                          <Select
                            onValueChange={field.onChange}
                            defaultValue={field.value}
                          >
                            <SelectTrigger>
                              <SelectValue placeholder="Select category" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="Distance">Distance</SelectItem>
                              <SelectItem value="Weight">Weight</SelectItem>
                              <SelectItem value="Frequency">
                                Frequency
                              </SelectItem>
                            </SelectContent>
                          </Select>
                        </FormItem>
                      );
                    }}
                  />

                  <FormField
                    control={form.control}
                    name="targetDate"
                    render={({ field }) => {}}
                  />
                </div>
              </form>
            </Form>
          </DialogContent>
        </Dialog>
      </div>
    </main>
  );
}
