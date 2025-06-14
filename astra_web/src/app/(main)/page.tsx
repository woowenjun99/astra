"use client";
import WorkoutCard from "@/component/WorkoutCard";
import FitnessGoalDashboardCard from "@/services/fitness/presentation/fitness-goal-dashboard-card";
import { Card, Grid, Title, Text, Button } from "@mantine/core";

export default function Page() {
  return (
    <div
      style={{
        width: "70%",
        marginLeft: "auto",
        marginRight: "auto",
        paddingTop: 20,
      }}
    >
      <Grid>
        <Grid.Col span={{ sm: 12, md: 8 }}>
          <Card withBorder>
            <Title order={2}>Recent Workouts</Title>
            <Text>Your latest training sessions</Text>
            <WorkoutCard
              title="Lower Body Strength"
              date="Yesterday"
              duration="45 min"
              calories={320}
              intensity="High"
            />
            <Button fullWidth mt={20}>
              View Workout History
            </Button>
          </Card>
        </Grid.Col>

        <Grid.Col span={{ sm: 12, md: 4 }}>
          <FitnessGoalDashboardCard />
        </Grid.Col>
      </Grid>
    </div>
  );
}
