"use client";
import { Grid } from "@mantine/core";
import RecentWorkoutDashboardCard from "@/services/fitness/presentation/RecentWorkoutDashboardCard";
import GoalsProgressDashboardCard from "@/services/fitness/presentation/GoalsProgressDashboardCard";
import WeeklyActivityDashboardCard from "@/services/fitness/presentation/WeeklyActivityDashboardCard";
import UpcomingWorkoutDashboardCard from "@/services/fitness/presentation/UpcomingWorkoutDashboardCard";

export default function Page() {
  return (
    <main>
      <Grid mx="md">
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <WeeklyActivityDashboardCard />
        </Grid.Col>
        <Grid.Col span={{ sm: 12, md: 4 }}>
          <UpcomingWorkoutDashboardCard />
        </Grid.Col>
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
