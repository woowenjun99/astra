export interface CreateUserDTO {
  email: string;
  password: string | null;
  provider: "password" | "google.com";
  uid: string | null;
}
