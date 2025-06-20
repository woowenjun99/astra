import { auth } from "@/firebase";
import type { BaseResponse } from "@/model/base-response";
import { axiosInstance } from "@/util/axios-instance";
import { signInWithEmailAndPassword, signOut } from "firebase/auth";

interface CreateUserDTO {
  email: string;
  password: string | null;
  provider: "password" | "google.com";
  uid: string | null;
}

export async function createUser(payload: CreateUserDTO) {
  const response = await axiosInstance.post<BaseResponse<void>>(
    "/users/register",
    payload
  );

  if (!response.data.success) {
    throw new Error(response.data.message);
  }
}

export async function signin(email: string, password: string) {
  const userCredential = await signInWithEmailAndPassword(
    auth,
    email,
    password
  );
  return userCredential.user;
}

export function signout() {
  return signOut(auth);
}

/**
 * Get the JWT token for the current user
 *
 * @returns The JWT token
 * @throws If the user is not logged in
 */
export async function getJwtToken(): Promise<string> {
  const token = await auth.currentUser?.getIdToken(true);
  if (token === undefined) {
    throw new Error("User is not logged in");
  }
  return token;
}
