"use client";
import { zodResolver } from "@hookform/resolvers/zod";
import { Card, Grid, NumberInput, Select, TextInput } from "@mantine/core";
import { DateInput } from "@mantine/dates";
import { IconCalendar } from "@tabler/icons-react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const schema = z.object({
  caloriesBurnt: z.number(),
  date: z.string().nullable(),
  duration: z.number(),
  intensity: z.string(),
  title: z.string(),
  workoutType: z.string(),
});

type Schema = z.infer<typeof schema>;

export default function WorkoutPage() {
  const { register, watch, setValue } = useForm<Schema>({
    resolver: zodResolver(schema),
  });

  return (
    <main>
      <Card withBorder shadow="md">
        <form>
          <Grid>
            <Grid.Col span={12}>
              <TextInput
                label="Workout Title"
                required
                {...register("title")}
              />
            </Grid.Col>

            <Grid.Col span={{ sm: 12, md: 6 }}>
              <Select
                allowDeselect={false}
                data={[
                  "Strength Training",
                  "Cardio",
                  "HIIT",
                  "Yoga",
                  "Pilates",
                  "Cycling",
                  "Running",
                  "Swimming",
                  "Others",
                ]}
                label="Workout Type"
                onChange={(value) => setValue("workoutType", value ?? "")}
                placeholder="Select workout type"
                required
                value={watch("workoutType")}
              />
            </Grid.Col>

            <Grid.Col span={{ sm: 12, md: 6 }}>
              <DateInput
                label="Date"
                onChange={(date) => setValue("date", date)}
                rightSection={<IconCalendar />}
                required
                value={watch("date")}
              />
            </Grid.Col>

            <Grid.Col span={{ sm: 12, md: 4 }}>
              <NumberInput
                allowNegative={false}
                allowDecimal={false}
                label="Duration (Minutes)"
                onChange={(value) =>
                  setValue("duration", typeof value === "string" ? 0 : value)
                }
                required
                value={watch("duration")}
              />
            </Grid.Col>

            <Grid.Col span={{ sm: 12, md: 4 }}>
              <Select
                allowDeselect={false}
                data={["Low", "Medium", "High"]}
                label="Intensity"
                onChange={(value) => setValue("intensity", value ?? "")}
                placeholder="Select intensity"
                required
                value={watch("intensity")}
              />
            </Grid.Col>

            <Grid.Col span={{ sm: 12, md: 4 }}>
              <NumberInput
                allowNegative={false}
                allowDecimal={false}
                label="Calories Burnt"
                onChange={(value) =>
                  setValue(
                    "caloriesBurnt",
                    typeof value === "string" ? 0 : value
                  )
                }
                placeholder="Optional"
                value={watch("caloriesBurnt")}
              />
            </Grid.Col>
          </Grid>
        </form>
      </Card>
    </main>
  );
}
