import type { Exercise, Run, Workout } from "./workout";

export interface GetWorkoutVO {
  exercises: Exercise[];
  runs: Run[];
  workout: Workout;
}
