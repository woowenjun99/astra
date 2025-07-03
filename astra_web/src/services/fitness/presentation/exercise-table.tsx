"use client";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import type { Exercise, Run } from "@/services/fitness/domain/workout";
import { Formatter } from "@/util/formatter";
import { TrashIcon } from "lucide-react";
import type { Dispatch, SetStateAction } from "react";

type ExerciseRunTableProps = {
  exercises: Exercise[];
  runs: Run[];
  setExercises: Dispatch<SetStateAction<Exercise[]>>;
  setRuns: Dispatch<SetStateAction<Run[]>>;
  workoutType: string;
};

export default function ExerciseRunTable({
  exercises,
  workoutType,
  runs,
  setExercises,
  setRuns,
}: ExerciseRunTableProps) {
  if (workoutType === "Running") {
    return (
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Lap</TableHead>
            <TableHead>Duration</TableHead>
            <TableHead>Distance</TableHead>
            <TableHead>Delete Row</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {runs.map((run, index) => {
            return (
              <TableRow key={index}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>
                  {Formatter.formatTimeElapsed(run.duration)}
                </TableCell>
                <TableCell>{Formatter.formatDistance(run.distance)}</TableCell>
                <TableCell>
                  <Button
                    onClick={() => {
                      const newRun = runs.filter((_, idx) => index !== idx);
                      setRuns(newRun);
                    }}
                    variant="ghost"
                    type="button"
                  >
                    <TrashIcon />
                  </Button>
                </TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    );
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Exercise</TableHead>
          <TableHead>Sets</TableHead>
          <TableHead>Reps</TableHead>
          <TableHead>Weight</TableHead>
          <TableHead>Delete Row</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {exercises.map((exercise, index) => {
          return (
            <TableRow key={index}>
              <TableCell>{exercise.name}</TableCell>
              <TableCell>{exercise.sets}</TableCell>
              <TableCell>{exercise.reps}</TableCell>
              <TableCell>{exercise.weight}</TableCell>
              <TableCell>
                <Button
                  onClick={() => {
                    const newExercises = exercises.filter(
                      (_, idx) => index !== idx
                    );
                    setExercises(newExercises);
                  }}
                  variant="ghost"
                  type="button"
                >
                  <TrashIcon />
                </Button>
              </TableCell>
            </TableRow>
          );
        })}
      </TableBody>
    </Table>
  );
}
