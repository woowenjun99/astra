import { Card, Stack, Text } from "@mantine/core";
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
    <Card withBorder>
      <Stack>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <Text fw="bold">{title}</Text>
          <Text fw="lighter">{date}</Text>
        </div>

        <div style={{ display: "flex", flexWrap: "wrap", gap: 4 }}>
          <div style={{ display: "flex", alignItems: "center", gap: 1.5 }}>
            <IconClock />
            <span>{duration}</span>
          </div>

          <div style={{ display: "flex", alignItems: "center", gap: 1.5 }}>
            <IconFlame color="orange" />
            <span>{calories} kcal</span>
          </div>

          <div style={{ display: "flex", alignItems: "center", gap: 1.5 }}>
            <IconGauge />
            <span>{intensity}</span>
          </div>
        </div>
      </Stack>
    </Card>
  );
};

export default WorkoutCard;
