"use client";
import { createWorkout } from "@/services/fitness/data/fitness-api";
import { Exercise, Run } from "@/services/fitness/domain/workout";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Button,
  Card,
  Grid,
  Group,
  NumberInput,
  Select,
  Stack,
  Table,
  Text,
  Textarea,
  TextInput,
  Title,
} from "@mantine/core";
import { DateInput } from "@mantine/dates";
import { notifications } from "@mantine/notifications";
import { IconCalendar, IconPlus } from "@tabler/icons-react";
import dayjs from "dayjs";
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

export default function WorkoutPage() {
  const {
    register,
    watch,
    setValue,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<Schema>({
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

  const onSubmit = handleSubmit(
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
        notifications.show({
          color: "red",
          message: (e as Error).message,
          title: "Error",
        });
      }
    }
  );

  return (
    <main>
      <Group justify="space-between">
        <Stack gap="sm">
          <Title order={1} fw="bold">
            Record Workout
          </Title>
          <Text fw="lighter">Log your workout details and exercises</Text>
        </Stack>
        <Card withBorder shadow="md">
          <form onSubmit={onSubmit}>
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
                  data={["Running", "Strength Training"]}
                  error={errors.workoutType?.message}
                  label="Workout Type"
                  onChange={(value) => {
                    setValue("workoutType", value ?? "");
                    setRuns([]);
                    setExercises([]);
                  }}
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
              {watch("workoutType") === "Strength Training" ? (
                <Grid>
                  <Grid.Col span={{ sm: 12, md: 4 }}>
                    <TextInput
                      label="Exercise Name"
                      onChange={(event) => {
                        setExercise({ ...exercise, name: event.target.value });
                      }}
                      placeholder="e.g. Bench Press"
                      value={exercise.name}
                    />
                  </Grid.Col>

                  <Grid.Col span={{ sm: 12, md: 2 }}>
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

                  <Grid.Col span={{ sm: 12, md: 2 }}>
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

                  <Grid.Col span={{ sm: 12, md: 2 }}>
                    <NumberInput
                      label="Weight (KG)"
                      onChange={(value) =>
                        setExercise({
                          ...exercise,
                          weight: typeof value === "string" ? 0 : value,
                        })
                      }
                      value={exercise.weight}
                    />
                  </Grid.Col>

                  <Grid.Col span={{ sm: 12, md: 2 }}>
                    <Button
                      fullWidth
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
                  </Grid.Col>
                </Grid>
              ) : (
                <Grid>
                  <Grid.Col span={{ sm: 12, md: 4 }}>
                    <NumberInput
                      allowDecimal
                      allowLeadingZeros={false}
                      allowNegative={false}
                      label="Distance (m)"
                      onChange={(val) =>
                        setRun({
                          ...run,
                          distance: typeof val === "string" ? 0 : val,
                        })
                      }
                      value={run.distance}
                    />
                  </Grid.Col>

                  <Grid.Col span={{ sm: 12, md: 4 }}>
                    <NumberInput
                      allowDecimal={false}
                      allowLeadingZeros={false}
                      allowNegative={false}
                      label="Duration (seconds)"
                      onChange={(val) =>
                        setRun({
                          ...run,
                          duration: typeof val === "string" ? 0 : val,
                        })
                      }
                      value={run.duration}
                    />
                  </Grid.Col>

                  <Grid.Col span={{ sm: 12, md: 4 }}>
                    <Button
                      fullWidth
                      color="black"
                      mt="lg"
                      onClick={() => {
                        runs.push(run);
                        setRuns(runs);
                        setRun({ distance: 0, duration: 0 });
                      }}
                    >
                      <IconPlus />
                    </Button>
                  </Grid.Col>
                </Grid>
              )}
            </Card>

            <Table my="lg" withTableBorder striped>
              <Table.Thead>
                {watch("workoutType") === "Strength Training" ? (
                  <Table.Tr>
                    <Table.Th>Exercise</Table.Th>
                    <Table.Th>Sets</Table.Th>
                    <Table.Th>Reps</Table.Th>
                    <Table.Th>Weights</Table.Th>
                    <Table.Th></Table.Th>
                  </Table.Tr>
                ) : (
                  <Table.Tr>
                    <Table.Th>Rep</Table.Th>
                    <Table.Th>Distance (m)</Table.Th>
                    <Table.Th>Duration (seconds)</Table.Th>
                    <Table.Th></Table.Th>
                  </Table.Tr>
                )}
              </Table.Thead>

              <Table.Tbody>
                {watch("workoutType") === "Strength Training"
                  ? exercises.map((ex) => {
                      return (
                        <Table.Tr key={ex.name}>
                          <Table.Td>{ex.name}</Table.Td>
                          <Table.Td>{ex.sets}</Table.Td>
                          <Table.Td>{ex.reps}</Table.Td>
                          <Table.Td>{ex.weight}</Table.Td>
                          <Table.Td></Table.Td>
                        </Table.Tr>
                      );
                    })
                  : runs.map((run, index) => {
                      return (
                        <Table.Tr key={index}>
                          <Table.Td>{index + 1}</Table.Td>
                          <Table.Td>{run.distance}</Table.Td>
                          <Table.Td>{run.duration}</Table.Td>
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
              <Button
                type="submit"
                color="black"
                disabled={isSubmitting}
                loading={isSubmitting}
              >
                Save Workout
              </Button>
            </Group>
          </form>
        </Card>
      </Group>
    </main>
  );
}
