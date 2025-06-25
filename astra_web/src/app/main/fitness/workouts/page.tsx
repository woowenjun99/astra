"use client";
import AllWorkouts from "@/services/fitness/presentation/AllWorkouts";
import WorkoutMetadataCards from "@/services/fitness/presentation/WorkoutMetadataCard";
import { Button, Group, Stack, Tabs, Text, Title } from "@mantine/core";
import { IconCalendar, IconPlus } from "@tabler/icons-react";
import Link from "next/link";

export default function WorkoutMainPage() {
  return (
    <main>
      <Group justify="space-between" align="center">
        <Stack gap="sm">
          <Title order={1}>My Workouts</Title>
          <Text>Track and manage all your fitness activities</Text>
        </Stack>
        <Group gap="md">
          <Link
            passHref
            href="/fitness/workouts/new"
            style={{ textDecoration: "none" }}
          >
            <Button leftSection={<IconPlus />} color="black">
              Record Workout
            </Button>
          </Link>

          <Link
            passHref
            href="/fitness/workouts/schedule"
            style={{ textDecoration: "none" }}
          >
            <Button
              color="black"
              variant="outline"
              leftSection={<IconCalendar />}
            >
              Schedule
            </Button>
          </Link>
        </Group>
      </Group>

      <WorkoutMetadataCards />
      <Tabs defaultValue="list" variant="pills">
        <Tabs.List>
          <Tabs.Tab value="list" color="black">
            List View
          </Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="list">
          <AllWorkouts />
        </Tabs.Panel>
      </Tabs>
    </main>
  );
}
