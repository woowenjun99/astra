"use client";
import { Card, Text, Title } from "@mantine/core";

export default function UpcomingWorkoutDashboardCard() {
  return (
    <Card withBorder shadow="md" h={450} radius="md">
      <Title order={2}>Upcoming Workouts</Title>
      <Text fw="lighter">Your latest training sessions</Text>
    </Card>
  );
}
