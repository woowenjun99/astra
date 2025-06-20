"use client";
import { getFitnessGoals } from "@/services/fitness/data/fitness-api";
import AddFitnessGoalModal from "@/services/fitness/presentation/AddFitnessGoalModal";
import {
  Box,
  Card,
  Grid,
  Group,
  LoadingOverlay,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import { IconTrash } from "@tabler/icons-react";
import dayjs from "dayjs";
import useSWR from "swr";

export default function FitnessGoalPage() {
  const { data, isLoading } = useSWR("/fitness/goals", getFitnessGoals);

  function displayFitnessGoal() {
    if (isLoading) {
      return (
        <Grid.Col>
          <Box h={400} pos="relative">
            <LoadingOverlay />
          </Box>
        </Grid.Col>
      );
    }

    if (data !== undefined) {
      return data.map((d) => {
        return (
          <Grid.Col key={d.category} span={{ sm: 12, md: 6 }}>
            <Card withBorder>
              <Group justify="space-between">
                <Stack gap="sm">
                  <Text>{d.title}</Text>
                  <Text>
                    Target date: {dayjs(d.targetDate).format("MMMM D, YYYY")}
                  </Text>
                </Stack>
                <IconTrash color="red" />
              </Group>

              <Text>{d.description}</Text>
            </Card>
          </Grid.Col>
        );
      });
    }
  }

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
        {displayFitnessGoal()}
      </Grid>
    </>
  );
}
