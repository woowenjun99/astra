import { getJwtToken } from "@/services/authentication/data/authentication-repository";
import { axiosInstance } from "@/util/axios-instance";

export async function registerDevice(pushNotificationToken: string) {
  const jwt = await getJwtToken();
  const response = await axiosInstance.post(
    "/devices",
    {
      pushNotificationToken,
    },
    { headers: { Authorization: jwt } }
  );
  if (!response.data.success) {
    throw new response.data.message();
  }
}

export async function sendTestNotification() {
  const jwt = await getJwtToken();
  const response = await axiosInstance.post("/devices/test", null, {
    headers: { Authorization: jwt },
  });
  if (!response.data.success) {
    throw new response.data.message();
  }
}
