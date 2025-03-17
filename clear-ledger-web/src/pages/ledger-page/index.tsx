import { useCallback, useEffect, useState } from "react"
import { useParams } from "react-router"
import { AxiosError } from "axios"
import { Button, Card, DatePicker, Form, Input, message, Table } from "antd"
import { Dayjs } from "dayjs"
import { FilterTransactionParams, Transaction } from "@/types"
import * as TransactionApi from "@/api/transaction"
import { CreateTransactionDialogue } from "@/components/create-transaction-dialogue"
import { currencyFormatter } from "@/utils/formatter"
import { useAppSelector } from "@/hooks/store"
import { formatDatetime } from "@/utils/dayjs"

type PaginationParams = {
  pageNumber: number
  pageSize: number
  totalPage: number
  totalRow: number
}

type _FilterTransactionParams = {
  transactionDate?: Dayjs[]
}

export const LedgerPage = () => {
  const { ledgerId } = useParams<{ ledgerId: string }>()

  const user = useAppSelector((state) => state.auth.user)

  const [transactions, setTransactions] = useState<Transaction[]>([])
  const [paginationParam, setPaginationParams] = useState<PaginationParams>({
    pageNumber: 1,
    pageSize: 10,
    totalPage: 0,
    totalRow: 0,
  })
  const [isCreateTransactionDialogueOpen, setIsCreateTransactionDialogueOpen] =
    useState<boolean>(false)
  const [filterParams, setFilterParams] = useState<FilterTransactionParams>({})
  const [form] = Form.useForm<_FilterTransactionParams>()

  const onPageChange = (pageNum: number, pageSize: number) => {
    setPaginationParams((prev) => ({
      ...prev,
      pageNumber: pageNum,
      pageSize,
    }))
  }

  const fetchTransactions = useCallback(async () => {
    if (!ledgerId) return

    try {
      const response = await TransactionApi.getTransactions(
        ledgerId,
        paginationParam.pageNumber,
        paginationParam.pageSize,
        filterParams
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
  }, [
    ledgerId,
    paginationParam.pageNumber,
    paginationParam.pageSize,
    filterParams,
  ])

  const onFilterSubmit = (values: _FilterTransactionParams) => {
    setFilterParams({
      transactionDateStart:
        values.transactionDate?.[0].format("YYYY-MM-DD") ?? undefined,
      transactionDateEnd:
        values.transactionDate?.[1].format("YYYY-MM-DD") ?? undefined,
    })
    setPaginationParams((prev) => ({ ...prev, pageNumber: 1 }))
  }

  // 重置筛选条件
  const onFilterReset = () => {
    form.resetFields()
    setFilterParams({})
    setPaginationParams((prev) => ({ ...prev, pageNumber: 1 }))
  }

  useEffect(() => {
    fetchTransactions().then()
  }, [fetchTransactions])

  return (
    <div className="flex gap-[10px] flex-col">
      <Card>
        <Form<_FilterTransactionParams>
          className="flex justify-between"
          form={form}
          layout="inline"
          onFinish={onFilterSubmit}>
          <Form.Item<_FilterTransactionParams>
            name="transactionDate"
            label="交易时间">
            <DatePicker.RangePicker format="YYYY-MM-DD" />
          </Form.Item>
          <div className="flex gap-4">
            <Button type="primary" htmlType="submit">
              筛选
            </Button>
            <Button onClick={onFilterReset}>重置</Button>
          </div>
        </Form>
      </Card>
      <Table<Transaction>
        dataSource={transactions}
        rowKey="id"
        pagination={{
          pageSize: paginationParam.pageSize,
          current: paginationParam.pageNumber,
          total: paginationParam.totalRow,
          onChange: onPageChange,
        }}
        rowClassName={(record) =>
          record.username == (user?.username ?? "")
            ? "bg-[#00FF6633]"
            : "bg-[#00FFFF33]"
        }
        title={() => (
          <div className="flex justify-between items-baseline">
            <h1 className="text-[16px]">交易记录</h1>
            <Button
              onClick={() => {
                setIsCreateTransactionDialogueOpen(true)
              }}
              type="primary">
              添加账单
            </Button>
          </div>
        )}>
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
            return currencyFormatter.format(value.amount / 100)
          }}
        />
        <Table.Column<Transaction>
          title="账单描述"
          dataIndex="description"
          key="amount"
        />
        <Table.Column<Transaction>
          title="交易时间"
          key="transactionDate"
          render={(value: Transaction) => {
            return formatDatetime(value.transactionDate)
          }}
        />
      </Table>

      <CreateTransactionDialogue
        open={isCreateTransactionDialogueOpen}
        onClose={() => {
          setIsCreateTransactionDialogueOpen(false)
        }}
        ledgerId={ledgerId!}
        onSuccess={fetchTransactions}
      />
    </div>
  )
}
