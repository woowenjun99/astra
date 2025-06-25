"use client";
import { Grid } from "@mantine/core";
import RecentWorkoutDashboardCard from "@/services/fitness/presentation/RecentWorkoutDashboardCard";
import GoalsProgressDashboardCard from "@/services/fitness/presentation/GoalsProgressDashboardCard";

export default function Page() {
  return (
    <main>
      <Grid mx="md">
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <RecentWorkoutDashboardCard />
        </Grid.Col>
        <Grid.Col span={{ sm: 12, md: 4 }}>
          <GoalsProgressDashboardCard />
        </Grid.Col>
      </Grid>
    </main>
  );
}
