import axios from "axios"
import moment from "moment"

/**
 * Web client provides the ability to send requests to backend server, which
 * enabled to exchange data between server and client. All requests will time
 * out after 10 seconds of being sent.
 */
const webClient = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_BASE_URL,
  timeout: moment.duration({ seconds: 10 }).asMilliseconds(),
})

export default webClient