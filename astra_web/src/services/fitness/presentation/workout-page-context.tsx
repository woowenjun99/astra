import { createContext, type Dispatch, type SetStateAction } from "react";

export type WorkoutType = "All Types" | "Strength Training" | "Running";

export type Intensity = "All Intensity" | "Low" | "Medium" | "High";

export interface WorkoutPageContext {
  intensity: Intensity;
  setIntensity: Dispatch<SetStateAction<Intensity>>;
  setWorkoutType: Dispatch<SetStateAction<WorkoutType>>;
  workoutType: WorkoutType;
}

export const WorkoutPageContext = createContext<WorkoutPageContext | null>(
  null
);
