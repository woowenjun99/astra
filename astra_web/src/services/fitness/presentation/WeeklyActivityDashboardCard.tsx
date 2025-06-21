"use client";
import {
  Box,
  Button,
  Card,
  Group,
  LoadingOverlay,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import { BarChart } from "@mantine/charts";
import useSWR from "swr";
import { getWeeklyActvity } from "../data/fitness-api";
import type { DailyActivity } from "../domain/daily-activity";
import { FC } from "react";

type WeeklyActivityBarChartProps = {
  dailyActivities: DailyActivity[];
};

const WeeklyActivityBarChart: FC<WeeklyActivityBarChartProps> = ({
  dailyActivities,
}) => {
  return (
    <BarChart
      data={dailyActivities}
      dataKey="date"
      series={[{ name: "caloriesBurnt", color: "blue" }]}
    />
  );
};

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

        {isLoading ? (
          <Box pos="relative" h="100%">
            <LoadingOverlay visible />
          </Box>
        ) : (
          <WeeklyActivityBarChart dailyActivities={data ?? []} />
        )}
      </Group>
    </Card>
  );
}
