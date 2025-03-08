import webClient from "@/utils/web-client"
import { CreateTransactionRequest, Pagination, Transaction } from "@/types"

const getTransactions = async (
  ledgerId: string,
  pageNum: number,
  pageSize: number
): Promise<Pagination<Transaction>> => {
  // create instance of URLSearchParams
  const params = new URLSearchParams()

  // if pageNum exists, then add to query params
  if (pageNum !== undefined && pageNum !== null) {
    params.append("pageNum", pageNum.toString())
  }

  // if pageSize exists, then add to query params
  if (pageSize !== undefined && pageSize !== null) {
    params.append("pageSize", pageSize.toString())
  }

  // compose full URI
  const url = `/transactions/${ledgerId}${params.toString() ? `?${params.toString()}` : ""}`

  const { data } = await webClient.get(url)
  return data
}

const createTransaction = async (
  request: CreateTransactionRequest
): Promise<Transaction> => {
  const { data } = await webClient.post<Transaction>("/transactions", request)
  return data
}

export { getTransactions, createTransaction }
