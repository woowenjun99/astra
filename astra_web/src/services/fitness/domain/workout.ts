export interface Workout {
  caloriesBurnt: number;
  date: Date;
  duration: number;
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
