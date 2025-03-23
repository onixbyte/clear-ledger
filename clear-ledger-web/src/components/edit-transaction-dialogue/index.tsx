import { useState, useEffect } from "react"
import {
  Button,
  DatePicker,
  Form,
  Input,
  InputNumber,
  message,
  Modal,
} from "antd"
import { Dayjs } from "dayjs"
import dayjs from "@/utils/dayjs"
import { EditTransactionRequest, Transaction } from "@/types"
import * as TransactionApi from "@/api/transaction"
import { AxiosError } from "axios"

type EditTransactionForm = {
  amount?: number
  description?: string
  transactionDate?: Dayjs
}

type EditTransactionDialogueProps = {
  open: boolean
  onClose: () => void
  transaction: Transaction
  onSuccess: () => void
}

export const EditTransactionDialogue = ({
  open,
  onClose,
  transaction,
  onSuccess,
}: EditTransactionDialogueProps) => {
  const [form] = Form.useForm<EditTransactionForm>()
  const [loading, setLoading] = useState(false)

  // 初始化表单数据
  useEffect(() => {
    if (open && transaction) {
      form.setFieldsValue({
        amount: transaction.amount / 100, // 转换为元
        description: transaction.description,
        transactionDate: transaction.transactionDate
          ? dayjs(transaction.transactionDate)
          : void 0,
      })
    }
  }, [open, transaction, form])

  const onFinish = async (values: EditTransactionForm) => {
    setLoading(true)
    try {
      const amount = values.amount != void 0 ? Math.round(values.amount * 100) : void 0
      const request: EditTransactionRequest = {
        id: transaction.id,
        ledgerId: transaction.ledgerId,
        amount,
        description: values.description,
        transactionDate: values.transactionDate?.format("YYYY-MM-DDTHH:mm:ss"),
      }
      console.log(JSON.stringify(request))
      await TransactionApi.editTransaction(request)
      message.success("账目修改成功")
      onSuccess()
      onClose()
    } catch (error) {
      message.error(
        error instanceof AxiosError
          ? error.response?.data.message
          : "账目修改失败"
      )
    } finally {
      setLoading(false)
    }
  }

  return (
    <Modal title="修改账目" open={open} onCancel={onClose} footer={null}>
      <Form<EditTransactionForm>
        form={form}
        layout="vertical"
        onFinish={onFinish}>
        <Form.Item<EditTransactionForm> label="金额" name="amount">
          <InputNumber precision={2} addonBefore="¥" className="w-[100%]" />
        </Form.Item>
        <Form.Item<EditTransactionForm> label="描述" name="description">
          <Input />
        </Form.Item>
        <Form.Item<EditTransactionForm> label="交易时间" name="transactionDate">
          <DatePicker
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            className="w-[100%]"
          />
        </Form.Item>
        <Form.Item>
          <div className="flex justify-end gap-4">
            <Button onClick={onClose}>取消</Button>
            <Button type="primary" htmlType="submit" loading={loading}>
              保存
            </Button>
          </div>
        </Form.Item>
      </Form>
    </Modal>
  )
}
