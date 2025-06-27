import { Workout } from "./workout";

export interface GetWorkoutsVO {
  workouts: Workout[];
  total: number;
}
