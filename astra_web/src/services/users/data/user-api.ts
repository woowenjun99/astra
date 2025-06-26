import type { User } from "../domain/user";
import { axiosInstance, type BaseResponse } from "@/util/axios-instance";
import { getJwtToken } from "@/services/authentication/data/authentication-api";
import dayjs from "dayjs";

export async function getUser(): Promise<User> {
  const jwt = await getJwtToken();
  const response = await axiosInstance.get<null, BaseResponse<User>>("/users", {
    headers: { Authorization: jwt },
  });
  return response.data.data;
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
  const response = await axiosInstance.put<UpdateUserDTO, BaseResponse<null>>(
    "/users",
    body,
    {
      headers: { Authorization: jwt },
    }
  );
  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}

interface AddPushNotificationDTO {
  pushNotificationToken: string;
}

export async function addPushNotification(body: AddPushNotificationDTO) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.post<
    AddPushNotificationDTO,
    BaseResponse<null>
  >("/devices", body, {
    headers: { Authorization: jwt },
  });
  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}
