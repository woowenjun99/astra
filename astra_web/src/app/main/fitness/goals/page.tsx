"use client";
import AddFitnessGoalModal from "@/services/fitness/presentation/AddFitnessGoalModal";
import { Group, Stack, Text, Title } from "@mantine/core";

export default function FitnessGoalPage() {
  return (
    <main>
      <Group justify="space-between" visibleFrom="md">
        <Stack>
          <Title order={1} fw="bold">
            Fitness Goals
          </Title>
          <Text fw="lighter">Track and manage your fitness objectives</Text>
        </Stack>
        <AddFitnessGoalModal />
      </Group>

      <Stack hiddenFrom="sm">
        <Title order={1} fw="bold">
          Fitness Goals
        </Title>
        <Text fw="lighter">Track and manage your fitness objectives</Text>
        <AddFitnessGoalModal />
      </Stack>
    </main>
  );
}
