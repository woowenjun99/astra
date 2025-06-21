"use client";
import { Grid } from "@mantine/core";
import RecentWorkoutDashboardCard from "@/services/fitness/presentation/RecentWorkoutDashboardCard";

export default function Page() {
  return (
    <main>
      <Grid>
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <RecentWorkoutDashboardCard />
        </Grid.Col>
        <Grid.Col span={{ sm: 12, md: 4 }}></Grid.Col>
      </Grid>
    </main>
  );
}
