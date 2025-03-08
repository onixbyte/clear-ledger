import { AxiosError } from "axios"
import {
  Button,
  DatePicker,
  Form,
  Input,
  InputNumber,
  message,
  Modal,
} from "antd"
import { useAppDispatch } from "@/hooks/store"
import * as TransactionApi from "@/api/transaction"
import { CreateTransactionRequest } from "@/types"
import dayjs from "@/utils/dayjs"

type CreateTransactionDialogueProps = {
  open: boolean
  onClose: () => void
  ledgerId: string
  onSuccess: () => void
}

export const CreateTransactionDialogue = ({
  open,
  onClose,
  ledgerId,
  onSuccess,
}: CreateTransactionDialogueProps) => {
  const [form] = Form.useForm()
  const dispatch = useAppDispatch()

  const handleCreateTransaction = async (values: CreateTransactionRequest) => {
    const formattedTransactionDate = values.transactionDate
      ? dayjs(values.transactionDate).format("YYYY-MM-DD HH:mm:ss")
      : dayjs().format("YYYY-MM-DD HH:mm:ss")
    try {
      await TransactionApi.createTransaction({
        ledgerId,
        transactionDate: formattedTransactionDate,
        amount: values.amount * 100,
        description: values.description,
      } as CreateTransactionRequest)
      message.success("成功创建账本")
      onClose()
      form.resetFields()
      onSuccess()
    } catch (error) {
      message.error(
        error instanceof AxiosError
          ? error.response?.data.message
          : "创建账单失败"
      )
    }
  }

  return (
    <Modal title="创建账单" open={open} onCancel={onClose} footer={null}>
      <Form<CreateTransactionRequest>
        form={form}
        onFinish={handleCreateTransaction}
        layout="vertical">
        <Form.Item<CreateTransactionRequest>
          name="amount"
          label="账单金额"
          rules={[{ required: true, message: "请输入账单金额" }]}>
          <InputNumber precision={2} addonBefore="¥" />
        </Form.Item>
        <Form.Item<CreateTransactionRequest>
          name="description"
          label="账本描述">
          <Input.TextArea />
        </Form.Item>
        <Form.Item<CreateTransactionRequest>
          name="transactionDate"
          label="交易日期">
          <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
        </Form.Item>
        <div className="flex gap-[8px] justify-end">
          <Button onClick={onClose}>取消</Button>
          <Button type="primary" htmlType="submit">
            创建账单
          </Button>
        </div>
      </Form>
    </Modal>
  )
}
