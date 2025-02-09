import { CreateLedgerRequest, Ledger } from "@/types"
import webClient from "@/utils/web-client"

/**
 * Get joined ledgers.
 */
const getLedgers = async (): Promise<Ledger[]> => {
  const { data } = await webClient.get<Ledger[]>("/ledgers")
  return data
}

/**
 * Join a ledger
 * @param ledgerId the id of the ledger to be joined
 */
const joinLedger = async (ledgerId: string): Promise<Ledger> => {
  const { data } = await webClient.post<Ledger>(`/ledgers/join/${ledgerId}`)
  return data
}

/**
 * Create a new ledger with given name and description.
 * @param request the info of the ledger to create
 */
const createLedger = async (request: CreateLedgerRequest): Promise<Ledger> => {
  const { data } = await webClient.post<Ledger>("/ledgers", request)
  return data
}

export { getLedgers, joinLedger, createLedger }
