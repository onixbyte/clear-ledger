import { Ledger } from "@/types"
import webClient from "@/utils/web-client"

/**
 * Get joined ledgers.
 */
const getLedgers = async (): Promise<Ledger[]> => {
  const { data } = await webClient.get<Ledger[]>("/ledgers")
  return data
}

export { getLedgers }