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
import WorkoutDashboardCard from "./WorkoutDashboardCard";

type WorkoutCardsListProps = {
  workouts: Workout[];
};

const WorkoutCards: FC<WorkoutCardsListProps> = ({ workouts }) => {
  if (workouts.length === 0) {
    return (
      <Stack w="100%" h="100%" align="center" py="md" gap="sm">
        <IconBarbell size={120} color="gray" />
        <Title order={3}>No workouts yet</Title>
        <Text fw="lighter">
          Start your fitness journey by recording your first workout
        </Text>
        <Group align="start" visibleFrom="sm">
          <Link passHref href="/fitness/workouts/new">
            <Button color="black" leftSection={<IconPlus />}>
              Record Workout
            </Button>
          </Link>
          <Button variant="outline" color="black">
            Browse Templates
          </Button>
        </Group>

        <Group align="start" hiddenFrom="sm">
          <Link
            passHref
            href="/fitness/workouts/new"
            style={{ width: "100%", textDecoration: "none" }}
          >
            <Button color="black" leftSection={<IconPlus />} fullWidth>
              Record Workout
            </Button>
          </Link>
          <Button variant="outline" color="black" fullWidth>
            Browse Templates
          </Button>
        </Group>
      </Stack>
    );
  }

  return (
    <Stack mt="lg">
      {workouts.map((workout) => {
        return (
          <WorkoutDashboardCard
            calories={workout.caloriesBurnt}
            date={workout.date}
            duration={workout.duration}
            intensity={workout.intensity}
            key={workout.id}
            title={workout.title}
          />
        );
      })}
      <Link
        passHref
        href="/fitness/workouts"
        style={{ textDecoration: "none" }}
      >
        <Button variant="outline" color="black" fullWidth mt="lg">
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
    <Card withBorder shadow="md" h={550} radius="md">
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
