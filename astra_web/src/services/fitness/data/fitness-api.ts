import { getJwtToken } from "@/services/authentication/data/authentication-api";
import { axiosInstance } from "@/util/axios-instance";
import type { FitnessGoal } from "../domain/fitness-goal";
import type { Exercise, Run, Workout } from "../domain/workout";
import type { BaseResponse } from "@/model/base-response";
import { DailyActivity } from "../domain/daily-activity";
import { WorkoutMetadata } from "../domain/workout-metadata";

export interface CreateFitnessGoalDTO {
  category: string;
  description: string | null;
  targetDate: Date;
  targetValue: number;
  title: string;
}

export async function createFitnessGoal(payload: CreateFitnessGoalDTO) {
  const jwt = await getJwtToken();

  const response = await axiosInstance.post("/fitness/goals", payload, {
    headers: { Authorization: jwt },
  });

  const data = response.data as BaseResponse<void>;

  if (!data.success) {
    throw new Error(data.message);
  }
}

export async function getFitnessGoals(
  endpoint: string
): Promise<FitnessGoal[]> {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get(endpoint, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<FitnessGoal[]>;
  return data.data;
}

interface GetWorkoutVO {
  workouts: Workout[];
  total: number;
}

export async function getWorkouts([endpoint, ...params]: string[]) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get(endpoint, {
    headers: { Authorization: jwt },
    params: {
      intensity:
        params.length >= 3 && params[3] === "All Intensity" ? null : params[3],
      pageSize: params[0],
      pageNo: params[1],
      workoutType:
        params.length >= 3 && params[2] === "All Types" ? null : params[2],
    },
  });
  const data = response.data as BaseResponse<GetWorkoutVO>;
  return data.data;
}

export async function getWeeklyActvity(endpoint: string) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get(endpoint, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<DailyActivity[]>;
  return data.data;
}

interface CreateWorkoutDTO {
  caloriesBurnt: number;
  date: Date;
  duration: number;
  exercises: Exercise[];
  intensity: string;
  remarks: string;
  runs: Run[];
  title: string;
  workoutType: string;
}

export async function createWorkout(payload: CreateWorkoutDTO) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.post("/fitness/workouts", payload, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<void>;
  if (!data.success) {
    throw new Error(data.message);
  }
}

export async function getWorkoutMetadata(
  endpoint: string
): Promise<WorkoutMetadata> {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get(endpoint, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<WorkoutMetadata>;
  return data.data;
}

export async function deleteWorkout(id: number) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.delete(`/fitness/workouts/${id}`, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<null>;
  if (!data.success) {
    throw new Error(data.message);
  }
}
