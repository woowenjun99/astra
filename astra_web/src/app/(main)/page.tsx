"use client";
import WorkoutCard from "@/services/fitness/presentation/WorkoutCard";
import FitnessGoalDashboardCard from "@/services/fitness/presentation/FitnessGoalDashboardCard";
import {
  Card,
  Grid,
  Title,
  Text,
  Button,
  Stack,
  LoadingOverlay,
  Box,
} from "@mantine/core";
import useSWR from "swr";
import { getRecentWorkouts } from "@/services/fitness/data/fitness-api";
import Link from "next/link";

export default function Page() {
  const { data, isLoading, error } = useSWR(
    "/fitness/workouts/recent",
    getRecentWorkouts
  );

  function displayWorkoutCards() {
    if (isLoading) {
      return (
        <Box pos="relative" h={400}>
          <LoadingOverlay visible />
        </Box>
      );
    }

    if (error !== undefined) {
      return <Text my="lg">{(error as Error).message}</Text>;
    }

    if (data !== undefined && data.length === 0) {
      return <Text my="md">You currently do not have any workouts</Text>;
    }
    return (
      <Stack my="lg">
        {data?.map((d) => {
          return (
            <WorkoutCard
              calories={d.caloriesBurnt}
              date={d.date}
              duration={d.duration}
              intensity={d.intensity}
              key={d.id}
              title={d.title}
            />
          );
        })}
      </Stack>
    );
  }

  return (
    <div>
      <Grid>
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <Card withBorder mah={550}>
            <Title order={2}>Recent Workouts</Title>
            <Text>Your latest training sessions</Text>
            {displayWorkoutCards()}
            <Link passHref href="/fitness/workouts">
              <Button fullWidth color="black">
                View Workout History
              </Button>
            </Link>
          </Card>
        </Grid.Col>

        <Grid.Col span={{ sm: 12, md: 4 }}>
          <FitnessGoalDashboardCard />
        </Grid.Col>
      </Grid>
    </div>
  );
}
