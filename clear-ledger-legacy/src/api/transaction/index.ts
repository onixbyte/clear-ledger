import { Page, Transaction } from "@/types"
import webClient from "@/utils/web-client"

const getTransactions = async (ledgerId: string, pageNum: number = 1, pageSize: number = 20): Promise<Page<Transaction>> => {
  const searchParams = new URLSearchParams()
  searchParams.append("pageNum", pageNum.toString())
  searchParams.append("pageSize", pageSize.toString())

  const { data } = await webClient.get<Page<Transaction>>(`/transactions/${ledgerId}?${searchParams.toString()}`)
  return data
}

export { getTransactions }