import webClient from "@/utils/web-client"
import { User, UserLoginResponse } from "@/types"

const login = async (username: string, password: string): Promise<UserLoginResponse> => {
  const { data, headers } = await webClient.post<User>("/auth/login", {
    username,
    password,
  })
  return {
    user: data,
    authorisation: headers["authorization"] as string
  }
}

const register = async (username: string, email: string, password: string) => {
  return await webClient.post("/auth/register", {
    username,
    email,
    password,
  })
}

export { login, register }
