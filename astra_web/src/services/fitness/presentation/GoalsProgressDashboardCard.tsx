"use client";
import {
  Box,
  Button,
  Card,
  LoadingOverlay,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import type { FitnessGoal } from "../domain/fitness-goal";
import { FC } from "react";
import useSWR from "swr";
import { getFitnessGoals } from "../data/fitness-api";
import { IconPlus, IconTarget } from "@tabler/icons-react";
import Link from "next/link";

type FitnessGoalsListProps = {
  fitnessGoals: FitnessGoal[];
};

const FitnessGoals: FC<FitnessGoalsListProps> = ({ fitnessGoals }) => {
  if (fitnessGoals.length === 0) {
    return (
      <Stack w="100%" h="100%" align="center" py="md" gap="sm">
        <IconTarget size={120} color="gray" />
        <Title order={3}>No goals set</Title>
        <Text fw="lighter">Set your first fitness goal to track progress</Text>
        <Link
          passHref
          href="/main/fitness/goals"
          style={{ width: "100%", textDecoration: "none" }}
        >
          <Button color="black" leftSection={<IconPlus />} fullWidth>
            Create Goal
          </Button>
        </Link>
      </Stack>
    );
  }
  return <Stack></Stack>;
};

export default function GoalsProgressDashboardCard() {
  const { data, isLoading } = useSWR("/fitness/goals", getFitnessGoals);

  return (
    <Card withBorder shadow="md" h={550} radius="md">
      <Title order={2}>Goals Progress</Title>
      <Text fw="lighter">Track your fitness goals</Text>
      {isLoading ? (
        <Box pos="relative" h="100%">
          <LoadingOverlay visible />
        </Box>
      ) : (
        <FitnessGoals fitnessGoals={data ?? []} />
      )}
    </Card>
  );
}
