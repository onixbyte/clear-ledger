import { Ledger } from "@/types"
import webClient from "@/utils/web-client"

/**
 * Get joined ledgers.
 */
const getLedgers = async (): Promise<Ledger[]> => {
  const { data } = await webClient.get<Ledger[]>("/ledgers")
  return data
}

const joinLedger = async (ledgerId: string): Promise<Ledger> => {
  const { data } = await webClient.post<Ledger>(`/ledgers/join/${ledgerId}`)
  return data
}

export { getLedgers, joinLedger }