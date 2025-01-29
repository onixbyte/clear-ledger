import webClient from "@/utils/web-client"

const login = async (username: string, password: string) => {
  return await webClient.post("/auth/login", {
    username,
    password,
  })
}

const register = async (
  username: string,
  emailAddress: string,
  password: string
) => {
  return await webClient.post("/auth/register", {
    username,
    email: emailAddress,
    password,
  })
}

export { login, register }
