"use client";
import {
  Card,
  Title,
  Text,
  Button,
  LoadingOverlay,
  Progress,
  Stack,
  Group,
} from "@mantine/core";
import { getFitnessGoals } from "../data/fitness-api";
import useSWR from "swr";
import { IconTrendingUp } from "@tabler/icons-react";
import Link from "next/link";

export default function FitnessGoalDashboardCard() {
  const { data, isLoading } = useSWR("/fitness/goals", getFitnessGoals);

  function displayProgressBars() {
    if (isLoading || data === undefined) return <LoadingOverlay />;
    if (data.length === 0) return <>No data found</>;
    return data.map((goal) => {
      return (
        <div key={goal.title}>
          <Stack gap="sm" my="md">
            <Group justify="space-between" align="center">
              <Group gap="xs">
                <IconTrendingUp />
                <Text fw="bolder">{goal.title}</Text>
              </Group>

              <Text fw="bold">
                {goal.currentValue} / {goal.targetValue} {goal.unit}
              </Text>
            </Group>
            <Progress
              value={(goal.currentValue * 100) / goal.targetValue}
              color="black"
            />
          </Stack>
        </div>
      );
    });
  }

  return (
    <Card withBorder>
      <Title order={2}>Goals Progress</Title>
      <Text>Track your fitness goals</Text>
      {displayProgressBars()}
      <Link passHref href="/goals" style={{ textDecoration: "none" }}>
        <Button variant="outline" fullWidth color="black" mt="md">
          Manage Goals
        </Button>
      </Link>
    </Card>
  );
}
