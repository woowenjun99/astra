"use client";
import {
  Box,
  Card,
  Grid,
  Group,
  LoadingOverlay,
  Text,
  Title,
} from "@mantine/core";
import type { FC, ReactElement } from "react";
import useSWR from "swr";
import { getWorkoutMetadata } from "../data/fitness-api";
import { IconBarbell, IconClock } from "@tabler/icons-react";

type WorkoutMetadataCardProps = {
  icon: ReactElement;
  isLoading: boolean;
  title: string;
  units?: string;
  value?: number;
};

const WorkoutMetadataCard: FC<WorkoutMetadataCardProps> = ({
  icon,
  isLoading,
  title,
  value,
  units,
}) => {
  return (
    <Card withBorder shadow="md">
      <Group>
        {icon}
        <div>
          <Title order={4} fw="bold" size="md">
            {title}
          </Title>
          {isLoading ? (
            <Box pos="relative" h="100%">
              <LoadingOverlay />
            </Box>
          ) : (
            <Text fw="bolder" size="xl">
              {value} {units}
            </Text>
          )}
        </div>
      </Group>
    </Card>
  );
};

const WorkoutMetadataCards = () => {
  const { data, isLoading } = useSWR(
    "/fitness/workouts/metadata",
    getWorkoutMetadata
  );

  return (
    <Grid my="lg">
      <Grid.Col span={{ sm: 12, md: 4 }}>
        <WorkoutMetadataCard
          icon={<IconBarbell size={40} />}
          isLoading={isLoading}
          title="Total Workouts"
          value={data?.totalWorkouts}
        />
      </Grid.Col>

      <Grid.Col span={{ sm: 12, md: 4 }}>
        <WorkoutMetadataCard
          icon={<IconClock size={40} />}
          isLoading={isLoading}
          title="Total Hours"
          value={data?.totalHours}
        />
      </Grid.Col>

      <Grid.Col span={{ sm: 12, md: 4 }}>
        <WorkoutMetadataCard
          icon={<IconBarbell size={40} />}
          isLoading={isLoading}
          title="Average Duration"
          units="Minutes"
          value={data?.averageDuration}
        />
      </Grid.Col>
    </Grid>
  );
};

export default WorkoutMetadataCards;
