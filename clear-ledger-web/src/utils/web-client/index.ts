import axios from "axios"
import moment from "moment"
import { useUserStore } from "@/store"

const webClient = axios.create({
  baseURL: import.meta.env.VITE_SERVER_BASE_URL,
  timeout: moment.duration({
    seconds: 10
  }).asMilliseconds()
})

webClient.interceptors.request.use((request) => {
  const { isAuthenticated, authorisation } = useUserStore()
  if (isAuthenticated) {
    request.headers.Authorization = authorisation
  }
  return request
}, (request) => {
  return Promise.reject(request)
})

webClient.interceptors.response.use((response) => {
  return response
}, (response) => {
  return Promise.reject(response)
})

export default webClient