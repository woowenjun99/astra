export interface WorkoutLog {
  id: number;
  caloriesBurnt: number;
}

export interface WorkoutPaginatedResponse {
  total: number;
  workoutLogs: WorkoutLog[];
}
