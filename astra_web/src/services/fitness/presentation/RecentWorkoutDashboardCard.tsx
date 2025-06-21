"use client";
import {
  Card,
  Title,
  Text,
  Stack,
  Group,
  Button,
  Box,
  LoadingOverlay,
} from "@mantine/core";
import { IconBarbell, IconPlus } from "@tabler/icons-react";
import useSWR from "swr";
import { getRecentWorkouts } from "../data/fitness-api";
import type { FC } from "react";
import { Workout } from "../domain/workout";
import Link from "next/link";

type WorkoutCardsListProps = {
  workouts: Workout[];
};

const WorkoutCards: FC<WorkoutCardsListProps> = ({ workouts }) => {
  if (workouts.length === 0) {
    return (
      <Stack w="100%" h="100%" align="center" py="md" gap="sm">
        <IconBarbell size={160} color="gray" />
        <Title order={3}>No workouts yet</Title>
        <Text fw="lighter">
          Start your fitness journey by recording your first workout
        </Text>
        <Group align="start">
          <Button color="black" leftSection={<IconPlus />}>
            Record Workout
          </Button>
          <Button variant="outline" color="black">
            Browse Templates
          </Button>
        </Group>
      </Stack>
    );
  }

  return (
    <Stack>
      <Link passHref href="/fitness/workouts">
        <Button variant="outline" color="black">
          View Workout History
        </Button>
      </Link>
    </Stack>
  );
};

export default function RecentWorkoutDashboardCard() {
  const { data, isLoading } = useSWR(
    "/fitness/workouts/recent",
    getRecentWorkouts
  );

  return (
    <Card withBorder shadow="md" h={450} radius="md">
      <Title order={2}>Recent Workouts</Title>
      <Text fw="lighter">Your latest training sessions</Text>
      {isLoading ? (
        <Box pos="relative" h="100%">
          <LoadingOverlay visible />
        </Box>
      ) : (
        <WorkoutCards workouts={data ?? []} />
      )}
    </Card>
  );
}
