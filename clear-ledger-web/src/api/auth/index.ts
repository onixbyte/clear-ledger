import webClient from "@/utils/web-client"

const login = async (username: string, password: string) => {
  const response = await webClient.post("/auth/login", {
    username,
    password,
  })
  console.log(response)
  return response
}

export { login }
