import { useEffect, useState } from "react"
import { useParams } from "react-router"
import { Transaction } from "@/types"
import * as TransactionApi from "@/api/transaction"
import { message } from "antd"
import { AxiosError } from "axios"

type PaginationParams = {
  pageNumber: number
  pageSize: number
  totalPage: number
  totalRow: number
}

export const LedgerPage = () => {
  const { ledgerId } = useParams<{ ledgerId: string }>()

  const [transactions, setTransactions] = useState<Transaction[]>([])
  const [paginationParam, setPaginationParams] = useState<PaginationParams>({
    pageNumber: 1,
    pageSize: 20,
    totalPage: 0,
    totalRow: 0,
  })

  useEffect(() => {
    const fetchTransactions = async () => {
      if (!ledgerId) return

      try {
        const response = await TransactionApi.getTransactions(
          ledgerId,
          paginationParam.pageNumber,
          paginationParam.pageSize
        )

        setTransactions(response.records)
        setPaginationParams((prev) => ({
          ...prev,
          totalRow: response.totalRow,
          totalPage: response.totalPage,
        }))
      } catch (error) {
        message.error(
          error instanceof AxiosError
            ? error.response?.data.message
            : "创建账本失败"
        )
      }
    }

    fetchTransactions()
  }, [ledgerId, paginationParam.pageNumber, paginationParam.pageSize])

  return <div>Ledger ID = {ledgerId}</div>
}
