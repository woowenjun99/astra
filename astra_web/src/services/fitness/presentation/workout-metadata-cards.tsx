"use client";
import type { FC, ReactElement } from "react";
import useSWR from "swr";
import { getWorkoutMetadata } from "../data/fitness-repository";
import { Card, CardContent } from "@/components/ui/card";
import { IconBarbell, IconClock, IconLoader } from "@tabler/icons-react";

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
    <Card>
      <CardContent className="px-4">
        <div className="flex flex-row gap-1 items-center">
          {icon}
          <div className="ml-4">
            <h3 className="text-sm font-medium text-gray-500 dark:text-gray-400">
              {title}
            </h3>
            {isLoading ? (
              <IconLoader className="animate-spin" />
            ) : (
              <p className="text-2xl font-bold">
                {value} {units}
              </p>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

export default function WorkoutMetadataCards() {
  const { data, isLoading } = useSWR(
    "/fitness/workouts/metadata",
    getWorkoutMetadata
  );

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
      <WorkoutMetadataCard
        icon={<IconBarbell size={40} />}
        isLoading={isLoading}
        title="Total Workouts"
        value={data?.totalWorkouts}
      />

      <WorkoutMetadataCard
        icon={<IconClock size={40} />}
        isLoading={isLoading}
        title="Total Hours"
        value={data?.totalHours}
      />

      <WorkoutMetadataCard
        icon={<IconBarbell size={40} />}
        isLoading={isLoading}
        title="Average Duration"
        units="Minutes"
        value={data?.averageDuration}
      />
    </div>
  );
}
