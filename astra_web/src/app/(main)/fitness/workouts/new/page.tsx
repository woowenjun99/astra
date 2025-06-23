"use client";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Button,
  Card,
  Grid,
  Group,
  NumberInput,
  Select,
  Table,
  Textarea,
  TextInput,
  Title,
} from "@mantine/core";
import { DateInput } from "@mantine/dates";
import { IconCalendar, IconPlus } from "@tabler/icons-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const schema = z.object({
  caloriesBurnt: z.number().int().nonnegative(),
  date: z.string().nullable(),
  duration: z.number().int().nonnegative(),
  intensity: z.string(),
  remarks: z.string(),
  title: z.string(),
  workoutType: z.string(),
});

type Schema = z.infer<typeof schema>;

type Exercise = {
  name: string;
  reps: number;
  sets: number;
  weight: number;
};

export default function WorkoutPage() {
  const {
    register,
    watch,
    setValue,
    formState: { errors },
  } = useForm<Schema>({
    resolver: zodResolver(schema),
  });
  const [exercise, setExercise] = useState<Exercise>({
    name: "",
    sets: 3,
    reps: 10,
    weight: 0,
  });
  const [exercises, setExercises] = useState<Exercise[]>([]);

  return (
    <main>
      <Card withBorder shadow="md">
        <form>
          <Grid>
            <Grid.Col span={12}>
              <TextInput
                error={errors.title?.message}
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
                error={errors.workoutType?.message}
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

          <Title order={3} my="md">
            Exercises
          </Title>
          <Card withBorder bg="#f9fafb">
            <Grid>
              <Grid.Col span={{ sm: 12, md: 3 }}>
                <TextInput
                  label="Exercise Name"
                  onChange={(event) => {
                    setExercise({ ...exercise, name: event.target.value });
                  }}
                  placeholder="e.g. Bench Press"
                  value={exercise.name}
                />
              </Grid.Col>

              <Grid.Col span={{ sm: 12, md: 3 }}>
                <NumberInput
                  allowDecimal={false}
                  allowNegative={false}
                  label="Sets"
                  onChange={(value) =>
                    setExercise({
                      ...exercise,
                      sets: typeof value === "string" ? 0 : value,
                    })
                  }
                  value={exercise.sets}
                />
              </Grid.Col>

              <Grid.Col span={{ sm: 12, md: 3 }}>
                <NumberInput
                  label="Reps"
                  onChange={(value) =>
                    setExercise({
                      ...exercise,
                      reps: typeof value === "string" ? 0 : value,
                    })
                  }
                  value={exercise.reps}
                />
              </Grid.Col>

              <Grid.Col span={{ sm: 12, md: 3 }}>
                <Group>
                  <NumberInput
                    label="Weight (In KG)"
                    onChange={(value) =>
                      setExercise({
                        ...exercise,
                        weight: typeof value === "string" ? 0 : value,
                      })
                    }
                    value={exercise.weight}
                  />
                  <Button
                    color="black"
                    mt="lg"
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
                  >
                    <IconPlus />
                  </Button>
                </Group>
              </Grid.Col>
            </Grid>
          </Card>

          <Table my="lg" withTableBorder striped>
            <Table.Thead>
              <Table.Tr>
                <Table.Th>Exercise</Table.Th>
                <Table.Th>Sets</Table.Th>
                <Table.Th>Reps</Table.Th>
                <Table.Th>Weights</Table.Th>
                <Table.Th></Table.Th>
              </Table.Tr>
            </Table.Thead>

            <Table.Tbody>
              {exercises.map((ex) => {
                return (
                  <Table.Tr key={ex.name}>
                    <Table.Td>{ex.name}</Table.Td>
                    <Table.Td>{ex.sets}</Table.Td>
                    <Table.Td>{ex.reps}</Table.Td>
                    <Table.Td>{ex.weight}</Table.Td>
                    <Table.Td></Table.Td>
                  </Table.Tr>
                );
              })}
            </Table.Tbody>
          </Table>

          <Textarea
            label="Notes"
            my="md"
            placeholder="Any additional notes about your workout..."
            {...register("remarks")}
          />

          <Group justify="end">
            <Button type="submit" color="black">
              Save Workout
            </Button>
          </Group>
        </form>
      </Card>
    </main>
  );
}
