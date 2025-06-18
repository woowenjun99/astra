"use client";
import AddFitnessGoalModal from "@/services/fitness/presentation/AddFitnessGoalModal";
import { Grid, Text, Title } from "@mantine/core";

export default function FitnessGoalPage() {
  return (
    <>
      <Grid>
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <Title>Fitness Goals</Title>
          <Text>Track and manage your fitness objectives</Text>
        </Grid.Col>
        <Grid.Col span={{ sm: 12, md: 4 }}>
          <AddFitnessGoalModal />
        </Grid.Col>
      </Grid>
    </>
  );
}
