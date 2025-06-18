import { Card, Group, Stack, Text } from "@mantine/core";
import { IconClock, IconFlame, IconGauge } from "@tabler/icons-react";

interface WorkoutCardProps {
  title: string;
  date: string;
  duration: string;
  calories: number;
  intensity: "Low" | "Medium" | "High";
}

const WorkoutCard: React.FC<WorkoutCardProps> = ({
  date,
  intensity,
  title,
  duration,
  calories,
}) => {
  return (
    <Card withBorder h={100}>
      <Stack>
        <Group justify="space-between">
          <Text fw="bold">{title}</Text>
          <Text fw="lighter">{date}</Text>
        </Group>

        <Group flex="wrap" gap={4}>
          <Group align="center" gap={1.5}>
            <IconClock />
            <span>{duration}</span>
          </Group>

          <Group flex="wrap" gap={1.5}>
            <IconFlame color="orange" />
            <span>{calories} kcal</span>
          </Group>

          <Group flex="wrap" gap={1.5}>
            <IconGauge />
            <span>{intensity}</span>
          </Group>
        </Group>
      </Stack>
    </Card>
  );
};

export default WorkoutCard;
