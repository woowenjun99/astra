"use client";
import type { FC, ReactElement } from "react";
import useSWR from "swr";
import { getWorkoutMetadata } from "../data/fitness-repository";
import { Card, CardContent } from "@/components/ui/card";
import { Formatter } from "@/util/formatter";
import { Clock, Dumbbell, Loader } from "lucide-react";

type WorkoutMetadataCardProps = {
  icon: ReactElement;
  isLoading: boolean;
  title: string;
  value?: number | string;
};

const WorkoutMetadataCard: FC<WorkoutMetadataCardProps> = ({
  icon,
  isLoading,
  title,
  value,
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
              <Loader className="animate-spin" />
            ) : (
              <p className="text-2xl font-bold">{value}</p>
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
        icon={<Dumbbell size={40} />}
        isLoading={isLoading}
        title="Total Workouts"
        value={data?.totalWorkouts}
      />

      <WorkoutMetadataCard
        icon={<Clock size={40} />}
        isLoading={isLoading}
        title="Total Duration"
        value={Formatter.formatTimeElapsed(data?.totalDurationInSeconds ?? 0)}
      />

      <WorkoutMetadataCard
        icon={<Dumbbell size={40} />}
        isLoading={isLoading}
        title="Average Duration"
        value={Formatter.formatTimeElapsed(data?.averageDurationInSeconds ?? 0)}
      />
    </div>
  );
}
