import { Exercise, Run } from "./workout";

export interface CreateWorkoutDTO {
  caloriesBurnt: number;
  date: Date;
  duration: number;
  exercises: Exercise[];
  id?: number;
  intensity: string;
  runs: Run[];
  title: string;
  workoutType: string;
}
