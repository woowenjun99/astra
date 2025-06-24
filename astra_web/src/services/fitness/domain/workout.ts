export interface Workout {
  caloriesBurnt: number;
  date: string;
  duration: string;
  id: number;
  intensity: "Low" | "Medium" | "High";
  title: string;
  workoutType: string;
}

export interface Exercise {
  name: string;
  reps: number;
  sets: number;
  weight: number;
}

export interface Run {
  distance: number;
  duration: number;
}
