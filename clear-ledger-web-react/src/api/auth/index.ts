import webClient from "@/utils/web-client"
import { User, UserLoginResponse } from "@/types"

/**
 * User login api. If username and password matched, the user's detail and his
 * authentication token.
 * @param username username
 * @param password password
 */
const login = async (
  username: string,
  password: string
): Promise<UserLoginResponse> => {
  const { data, headers } = await webClient.post<User>("/auth/login", {
    username,
    password,
  })
  return {
    user: data,
    authorisation: headers["authorization"] as string,
  }
}

/**
 * User register api. If the username and email has not been taken, this user
 * will successfully register.
 * @param username username
 * @param email user's email address
 * @param password password
 */
const register = async (
  username: string,
  email: string,
  password: string
): Promise<User> => {
  const { data } = await webClient.post<User>("/auth/register", {
    username,
    email,
    password,
  })
  return data
}

export { login, register }
