import dayjs from "dayjs"
import duration from "dayjs/plugin/duration"

dayjs.extend(duration)

export default dayjs

const formatDatetime = (datetime: string) => {
  return dayjs(datetime).format("YYYY-MM-DD HH:mm:ss")
}

export { formatDatetime }