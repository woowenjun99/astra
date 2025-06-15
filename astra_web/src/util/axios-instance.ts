import axios, { AxiosError } from "axios";
import { env } from "@/env";

const axiosInstance = axios.create({
  baseURL: env.NEXT_PUBLIC_BASE_URL,
});

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => Promise.resolve((error as AxiosError).response)
);

export { axiosInstance };
