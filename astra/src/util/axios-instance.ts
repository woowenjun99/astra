import axios, { AxiosError, type AxiosResponse } from "axios";
import { env } from "@/env";

export interface BaseResponse<T> extends AxiosResponse {
  data: {
    data: T;
    message: string;
    success: boolean;
  };
}

const axiosInstance = axios.create({
  baseURL: env.NEXT_PUBLIC_BASE_URL,
});

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => Promise.resolve((error as AxiosError).response)
);

export { axiosInstance };
