import { axiosInstance } from "@/util/axios-instance";
import { getJwtToken } from "./user";
import { env } from "@/env";
import { BaseResponse } from "@/model/base-response";
import { WorkoutPaginatedResponse } from "@/model/workout-paginated-response";

export async function getWorkoutLogs(endpoint: string) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get<
    BaseResponse<WorkoutPaginatedResponse>
  >(env.NEXT_PUBLIC_BASE_URL + endpoint, {
    params: {
      pageSize: 3,
      pageNo: 5,
    },
    headers: {
      Authorization: jwt,
    },
  });
  return response.data;
}
