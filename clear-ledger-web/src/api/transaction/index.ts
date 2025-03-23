import webClient from "@/utils/web-client"
import {
  CreateTransactionRequest,
  EditTransactionRequest,
  FilterTransactionParams,
  Pagination,
  Transaction,
} from "@/types"
import { Optional } from "@/utils/optional"

const getTransactions = async (
  ledgerId: string,
  pageNum: number,
  pageSize: number,
  filterParams?: FilterTransactionParams
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

  Optional.ofNullable<FilterTransactionParams>(filterParams)
    .map((filter) => filter.transactionDateStart)
    .ifPresent((transactionDateStart) => params.append("transactionDateStart", transactionDateStart))

  Optional.ofNullable<FilterTransactionParams>(filterParams)
    .map((filter) => filter.transactionDateEnd)
    .ifPresent((transactionDateEnd) => params.append("transactionDateEnd", transactionDateEnd))

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

const editTransaction = async (request: EditTransactionRequest): Promise<Transaction> => {
  console.log(`request in API: `, request)
  const { data } = await webClient.patch<Transaction>("/transactions", request)
  return data
}

export { getTransactions, createTransaction, editTransaction }
