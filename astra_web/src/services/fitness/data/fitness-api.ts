import { getJwtToken } from "@/services/authentication/data/authentication-api";
import { axiosInstance } from "@/util/axios-instance";
import type { FitnessGoal } from "../domain/fitness-goal";
import type { Workout } from "../domain/workout";
import type { BaseResponse } from "@/model/base-response";

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

// TODO: Implement /workouts
export async function getRecentWorkouts(endpoint: string) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get(endpoint, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<Workout[]>;
  return data.data;
}
