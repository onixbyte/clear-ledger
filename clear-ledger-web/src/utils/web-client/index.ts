import axios from "axios"
import dayjs from "@/utils/dayjs"
import { store } from "@/store"

const webClient = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: dayjs.duration({ seconds: 10 }).asMilliseconds()
})

webClient.interceptors.request.use(
  (config) => {
    const token = store.getState().auth.token
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error) => Promise.reject(error)
)

export default webClient