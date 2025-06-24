"use client";
import { FC } from "react";
import useSWR from "swr";
import { deleteWorkout, getWorkouts } from "../data/fitness-api";
import { Workout } from "../domain/workout";
import {
  Box,
  Button,
  Card,
  Group,
  LoadingOverlay,
  Menu,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import dayjs from "dayjs";
import { IconClock, IconDots, IconGraph, IconTrash } from "@tabler/icons-react";
import { modals } from "@mantine/modals";
import { notifications } from "@mantine/notifications";

type WorkoutCardProps = {
  workout: Workout;
};

const WorkoutCard: FC<WorkoutCardProps> = ({ workout }) => {
  const computeDateString = () => {
    const today = dayjs();
    const givenDate = dayjs(workout.date);
    if (today.diff(givenDate, "days") === 1) {
      return "Yesterday";
    } else if (today.diff(givenDate, "days") === -1) {
      return "Tomorrow";
    }
    return givenDate.format("MMMM D, YYYY");
  };

  const showDeleteModal = (id: number) => {
    modals.openConfirmModal({
      children: (
        <Text>
          Once you delete the workout, you might need to log it again.
        </Text>
      ),
      labels: { confirm: "Delete", cancel: "Cancel" },
      onConfirm: async () => {
        try {
          await deleteWorkout(id);
        } catch (e) {
          notifications.show({
            color: "red",
            message: (e as Error).message,
            title: "Oops, something went wrong when deleting",
          });
        }
      },
      title: "Destructive Action",
    });
  };

  return (
    <Card withBorder shadow="md" my="lg">
      <Group justify="space-between" align="start">
        <Stack gap="sm">
          <Title order={3} size="lg">
            {workout.title}
          </Title>
          <Text>
            {computeDateString()} • {workout.workoutType}
          </Text>

          <Group align="center" gap="sm">
            <IconClock />
            {workout.duration} min
            <IconGraph />
            {workout.caloriesBurnt} cal
          </Group>
        </Stack>

        <Menu>
          <Menu.Target>
            <Button variant="transparent">
              <IconDots />
            </Button>
          </Menu.Target>

          <Menu.Dropdown>
            <Menu.Item
              leftSection={<IconTrash color="red" />}
              onClick={() => showDeleteModal(workout.id)}
            >
              Delete Workout
            </Menu.Item>
          </Menu.Dropdown>
        </Menu>
      </Group>
    </Card>
  );
};

const WorkoutCards: FC<{ workouts: Workout[] }> = ({ workouts }) => {
  return workouts.map((workout) => (
    <WorkoutCard key={workout.id} workout={workout} />
  ));
};

export default function AllWorkouts() {
  const pagination = { pageSize: 20, pageNo: 0 };
  const { data, isLoading } = useSWR(
    ["/fitness/workouts", pagination.pageSize, pagination.pageNo],
    getWorkouts
  );

  return (
    <div>
      {isLoading ? (
        <Box h="100%" pos="relative">
          <LoadingOverlay />
        </Box>
      ) : (
        <WorkoutCards workouts={data ?? []} />
      )}
    </div>
  );
}
