import { auth } from "@/firebase";
import type { BaseResponse } from "@/model/base-response";
import { axiosInstance } from "@/util/axios-instance";
import { signInWithEmailAndPassword, signOut } from "firebase/auth";

export async function createUser(email: string, password: string) {
  const response = await axiosInstance.post<BaseResponse<void>>(
    "/users/register",
    {
      email,
      password,
    }
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

type UpdateUserPayload = {
  email: string;
  name: string | null;
  bio: string | null;
  dob: Date | null;
  gender: string | null;
};

export async function updateUser(payload: UpdateUserPayload) {
  const jwt = await auth.currentUser?.getIdToken(true);
  if (jwt === undefined) {
    throw new Error("User is not authenticated");
  }

  // Send the updated user data to the server
  const { data } = await axiosInstance.put<BaseResponse<void>>(
    "/users",
    payload,
    {
      headers: { Authorization: jwt },
    }
  );
  if (!data.success) {
    throw new Error(data.message);
  }
}

export function signout() {
  return signOut(auth);
}
