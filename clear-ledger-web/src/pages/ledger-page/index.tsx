import { useEffect, useState } from "react"
import { useParams } from "react-router"
import { Transaction } from "@/types"
import * as TransactionApi from "@/api/transaction"
import { message, Table } from "antd"
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
    pageSize: 10,
    totalPage: 0,
    totalRow: 0,
  })

  const onPageChange = (pageNum: number, pageSize: number) => {
    setPaginationParams((prev) => ({
      ...prev,
      pageNumber: pageNum,
      pageSize,
    }))
  }

  useEffect(() => {
    ;(async () => {
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
    })()
  }, [ledgerId, paginationParam.pageNumber, paginationParam.pageSize])

  return (
    <div>
      <Table<Transaction>
        dataSource={transactions}
        pagination={{
          pageSize: paginationParam.pageSize,
          current: paginationParam.pageNumber,
          total: paginationParam.totalRow,
          onChange: onPageChange,
        }}>
        <Table.Column<Transaction> title="ID" dataIndex="id" key="id" />
        <Table.Column<Transaction>
          title="用户名"
          dataIndex="username"
          key="username"
        />
        <Table.Column<Transaction>
          title="金额"
          key="amount"
          render={(value: Transaction) => {
            return `¥ ${value.amount / 100}`
          }}
        />
        <Table.Column<Transaction>
          title="账单描述"
          dataIndex="description"
          key="amount"
        />
        <Table.Column<Transaction>
          title="交易时间"
          dataIndex="transactionDate"
          key="transactionDate"
        />
      </Table>
    </div>
  )
}
