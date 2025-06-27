import { auth } from "@/util/firebase";
import { axiosInstance, type BaseResponse } from "@/util/axios-instance";
import {
  signInWithEmailAndPassword,
  signOut,
  signInWithPopup,
  GoogleAuthProvider,
} from "firebase/auth";
import type { CreateUserDTO } from "../domain/create-user-dto";

export async function createUser(payload: CreateUserDTO) {
  const response = await axiosInstance.post<null, BaseResponse<void>>(
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

export async function signinWithGoogle() {
  const user = await signInWithPopup(auth, new GoogleAuthProvider());
  await createUser({
    email: user.user.email as string,
    password: null,
    provider: "google.com",
    uid: user.user.uid,
  });
}

export function signout() {
  return signOut(auth);
}

export async function getJwtToken(): Promise<string> {
  const token = await auth.currentUser?.getIdToken(true);
  if (token === undefined) {
    throw new Error("User is not logged in");
  }
  return token;
}
