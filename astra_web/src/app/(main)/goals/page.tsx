"use client";
import AddFitnessGoalModal from "@/services/fitness/presentation/add-fitness-goal-modal";
import { Stack, Text, Title } from "@mantine/core";

export default function FitnessGoalPage() {
  return (
    <>
      <Stack>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <div>
            <Title>Fitness Goals</Title>
            <Text>Track and manage your fitness objectives</Text>
          </div>
          <AddFitnessGoalModal />
        </div>
      </Stack>
    </>
  );
}
