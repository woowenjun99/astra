"use client";
import { Card, Title, Text, Button } from "@mantine/core";
import { getFitnessGoals } from "../data/fitness-api";
import useSWR from "swr";

export default function FitnessGoalDashboardCard() {
  const {} = useSWR("/fitness/goals", getFitnessGoals);

  return (
    <Card withBorder>
      <Title order={2}>Goals Progress</Title>
      <Text>Track your fitness goals</Text>
      <Button variant="outline" fullWidth color="black" mt="md">
        Manage Goals
      </Button>
    </Card>
  );
}
