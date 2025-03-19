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

const getVerificationCode = async (email: string) => {
  const urlSearchParam = new URLSearchParams()
  urlSearchParam.append("audience", email)
  await webClient.get(`/auth/verification-code?${urlSearchParam.toString()}`)
}

/**
 * User register api. If the username and email has not been taken, this user
 * will successfully register.
 * @param username username
 * @param email user's email address
 * @param password password
 * @param verificationCode email verification code
 */
const register = async (
  username: string,
  email: string,
  password: string,
  verificationCode: string
): Promise<UserLoginResponse> => {
  const { data, headers } = await webClient.post<User>("/auth/register", {
    username,
    email,
    password,
    verificationCode,
  })
  return {
    user: data,
    authorisation: headers["authorization"] as string,
  }
}

export { login, getVerificationCode, register }
