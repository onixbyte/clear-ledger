import { AxiosError } from "axios"
import { Button, Form, Input, Modal, message } from "antd"
import { useAppDispatch } from "@/hooks/store"
import * as LedgerApi from "@/api/ledger"
import { setLedgers } from "@/store/ledger-slice"
import { CreateLedgerRequest } from "@/types"
import "./index.css"

interface CreateLedgerDialogueProps {
  open: boolean
  onClose: () => void
}

export const CreateLedgerDialogue = ({
  open,
  onClose,
}: CreateLedgerDialogueProps) => {
  const [form] = Form.useForm()
  const dispatch = useAppDispatch()

  const handleCreateLedger = async (values: CreateLedgerRequest) => {
    try {
      await LedgerApi.createLedger(values)
      const ledgerData = await LedgerApi.getLedgers()
      dispatch(setLedgers(ledgerData))
      message.success("成功创建账本")
      onClose()
      form.resetFields()
    } catch (error) {
      message.error(
        error instanceof AxiosError
          ? error.response?.data.message
          : "创建账本失败"
      )
    }
  }

  return (
    <Modal title="创建账本" open={open} onCancel={onClose} footer={null}>
      <Form<CreateLedgerRequest>
        form={form}
        onFinish={handleCreateLedger}
        layout="vertical">
        <Form.Item<CreateLedgerRequest>
          name="name"
          label="账本名称"
          rules={[{ required: true, message: "请输入账本名称" }]}>
          <Input />
        </Form.Item>
        <Form.Item<CreateLedgerRequest> name="description" label="账本描述">
          <Input.TextArea />
        </Form.Item>
        <div className="create-ledger-controls">
          <Button onClick={onClose}>
            取消
          </Button>
          <Button type="primary" htmlType="submit">
            创建账本
          </Button>
        </div>
      </Form>
    </Modal>
  )
}
