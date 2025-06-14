import { getJwtToken } from "@/api/user";
import type { BaseResponse } from "@/model/base-response";
import { axiosInstance } from "@/util/axios-instance";
import type { FitnessGoal } from "../domain/fitness-goal";

export interface CreateFitnessGoalDTO {
  category: string;
  description: string;
  targetDate: Date;
  targetValue: number;
  title: string;
}

export async function createFitnessGoal(payload: CreateFitnessGoalDTO) {
  const jwt = await getJwtToken();

  const response = await axiosInstance.post<
    CreateFitnessGoalDTO,
    BaseResponse<void>
  >("/fitness/goals", payload, {
    headers: { Authorization: jwt },
  });

  if (!response.success) {
    throw new Error(response.message);
  }
}

export async function getFitnessGoals(): Promise<FitnessGoal[]> {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get<
    CreateFitnessGoalDTO,
    BaseResponse<FitnessGoal[]>
  >("/fitness/goals", {
    headers: { Authorization: jwt },
  });
  return response.data;
}
