import { getJwtToken } from "@/services/authentication/data/authentication-repository";
import { axiosInstance, type BaseResponse } from "@/util/axios-instance";
import type { WorkoutMetadata } from "../domain/workout-metadata";
import type { CreateWorkoutDTO } from "../domain/create-workout-dto";
import type { GetWorkoutsVO } from "../domain/get-workouts-vo";
import { GetWorkoutVO } from "../domain/get-workout-vo";

export async function getWorkouts([endpoint, ...params]: string[]) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get<null, BaseResponse<GetWorkoutsVO>>(
    endpoint,
    {
      headers: { Authorization: jwt },
      params: {
        intensity:
          params.length >= 3 && params[3] === "All Intensity"
            ? null
            : params[3],
        pageSize: params[0],
        pageNo: params[1],
        workoutType:
          params.length >= 3 && params[2] === "All Types" ? null : params[2],
      },
    }
  );
  return response.data.data;
}

export async function modifyWorkout(payload: CreateWorkoutDTO) {
  const jwt = await getJwtToken();
  let response: BaseResponse<null>;
  if (payload.id === undefined) {
    response = await axiosInstance.post<CreateWorkoutDTO, BaseResponse<null>>(
      "/fitness/workouts",
      payload,
      {
        headers: { Authorization: jwt },
      }
    );
  } else {
    response = await axiosInstance.put<CreateWorkoutDTO, BaseResponse<null>>(
      "/fitness/workouts",
      payload,
      {
        headers: { Authorization: jwt },
      }
    );
  }
  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}

export async function getWorkoutMetadata([
  endpoint,
  ...params
]: string[]): Promise<WorkoutMetadata> {
  const jwt = await getJwtToken();
  console.log(params);
  const response = await axiosInstance.get<null, BaseResponse<WorkoutMetadata>>(
    endpoint,
    {
      headers: { Authorization: jwt },
      params: {
        intensity: params[0] === "All Intensity" ? undefined : params[0],
        workoutType: params[1] === "All Types" ? undefined : params[1],
      },
    }
  );
  return response.data.data;
}

export async function deleteWorkout(id: number) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.delete<null, BaseResponse<null>>(
    `/fitness/workouts/${id}`,
    {
      headers: { Authorization: jwt },
    }
  );
  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}

export async function getWorkout(id: number) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get<null, BaseResponse<GetWorkoutVO>>(
    `/fitness/workouts/${id}`,
    { headers: { Authorization: jwt } }
  );
  return response.data.data;
}
