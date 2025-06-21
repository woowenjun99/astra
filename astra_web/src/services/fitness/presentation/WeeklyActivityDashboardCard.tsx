"use client";
import { Button, Card, Group, Stack, Text, Title } from "@mantine/core";
import { BarChart } from "@mantine/charts";
import useSWR from "swr";
import { getWeeklyActvity } from "../data/fitness-api";

export default function WeeklyActivityDashboardCard() {
  const { data, isLoading } = useSWR(
    "/fitness/weekly-activity",
    getWeeklyActvity
  );

  return (
    <Card withBorder shadow="md" h={450} radius="md">
      <Group justify="space-between" align="center">
        <Stack gap="sm">
          <Title order={2}>Weekly Activity</Title>
          <Text fw="lighter">Your latest training sessions</Text>
        </Stack>

        <Button variant="outline" color="black">
          View Details
        </Button>
      </Group>
    </Card>
  );
}
