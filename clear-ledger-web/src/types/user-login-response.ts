import { User } from "./user.ts"

export type UserLoginResponse = {
  user: User,
  authorisation: string
}