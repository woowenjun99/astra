import type { BaseResponse } from "@/model/base-response";
import type { User } from "../domain/user";
import { axiosInstance } from "@/util/axios-instance";
import { getJwtToken } from "@/api/user";
import dayjs from "dayjs";

/**
 * Get the current logged in user info from the backend.
 *
 * @returns The current logged in user info
 */
export async function getUser(): Promise<User> {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get("/users", {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<User>;
  return data.data;
}

export interface UpdateUserDTO {
  dob: Date;
  bio: string | null;
  email: string;
  name: string | null;
  gender: string | null;
}

export async function updateUser(payload: UpdateUserDTO) {
  const body = { ...payload, dob: dayjs(payload.dob, "").toDate() };
  const jwt = await getJwtToken();
  const response = await axiosInstance.put("/users", body, {
    headers: { Authorization: jwt },
  });
  const data = response.data as BaseResponse<null>;
  if (!data.success) {
    throw new Error(data.message);
  }
}
