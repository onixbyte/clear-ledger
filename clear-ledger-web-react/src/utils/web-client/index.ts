import axios from "axios"
import moment from "moment"

const webClient = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: moment.duration({ seconds: 10 }).asMilliseconds()
})

export default webClient