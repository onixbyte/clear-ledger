import { AxiosError } from "axios"
import { Button, Form, Input, message, Modal } from "antd"
import * as LedgerApi from "@/api/ledger"
import { useAppDispatch } from "@/hooks/store"
import { setLedgers } from "@/store/ledger-slice"
import "./index.scss"

type JoinLedgerForm = {
  ledgerId: string
}

interface JoinLedgerModalProps {
  open: boolean
  onClose: () => void
}

export const JoinLedgerDialogue = ({ open, onClose }: JoinLedgerModalProps) => {
  const [form] = Form.useForm()
  const dispatch = useAppDispatch()

  const handleJoinLedger = async (values: JoinLedgerForm) => {
    try {
      await LedgerApi.joinLedger(values.ledgerId)
      const ledgerData = await LedgerApi.getLedgers()
      dispatch(setLedgers(ledgerData))
      message.success("成功加入账本")
      onClose()
      form.resetFields()
    } catch (error) {
      message.error(
        error instanceof AxiosError
          ? error.response?.data.message
          : "加入账本失败"
      )
    }
  }

  return (
    <Modal title="加入账本" open={open} onCancel={onClose} footer={null}>
      <Form<JoinLedgerForm>
        form={form}
        onFinish={handleJoinLedger}
        layout="vertical">
        <Form.Item<JoinLedgerForm>
          name="ledgerId"
          label="账本 ID"
          rules={[{ required: true, message: "请输入账本 ID" }]}>
          <Input />
        </Form.Item>
        <div className="join-ledger-controls">
          <Button onClick={onClose}>
            取消
          </Button>
          <Button type="primary" htmlType="submit">
            加入账本
          </Button>
        </div>
      </Form>
    </Modal>
  )
}
