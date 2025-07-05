import { axiosInstance, BaseResponse } from "@/util/axios-instance";
import { CreateScheduledWorkoutDTO } from "../domain/create-scheduled-workout-dto";
import { getJwtToken } from "@/services/authentication/data/authentication-repository";

export async function createScheduledWorkout(
  payload: CreateScheduledWorkoutDTO
) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.post<
    CreateScheduledWorkoutDTO,
    BaseResponse<null>
  >("/schedule/workout", payload, {
    headers: { Authorization: jwt },
  });
  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}
