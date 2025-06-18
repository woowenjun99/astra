export interface Workout {
  caloriesBurnt: number;
  date: string;
  duration: string;
  id: number;
  intensity: "Low" | "Medium" | "High";
  title: string;
}
