import { useState, useEffect } from "react"
import { NavLink } from "react-router"
import { useLocation, useNavigate } from "react-router-dom"
import { AxiosError } from "axios"
import { Button, Form, Input, message, Space } from "antd"
import { useAppDispatch } from "@/hooks/store"
import * as AuthApi from "@/api/auth"
import { setUser } from "@/store/auth-slice.ts"

type UserRegisterForm = {
  username: string
  password: string
  email: string
  verificationCode: string
}

export const RegisterPage = () => {
  const [form] = Form.useForm<UserRegisterForm>()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const location = useLocation()

  const from =
    (location.state as { from?: { pathname: string } })?.from?.pathname || "/"

  const [loading, setLoading] = useState<boolean>(false)
  const [email, setEmail] = useState<string>("")
  const [countdown, setCountdown] = useState<number>(0)

  const onFinish = async (values: UserRegisterForm) => {
    setLoading(true)
    try {
      const response = await AuthApi.register(
        values.username,
        values.email,
        values.password,
        values.verificationCode
      )
      dispatch(setUser({ user: response.user, token: response.authorisation }))
      message.success("注册成功")
      navigate(from, { replace: true })
    } catch (error) {
      message.error(
        error instanceof AxiosError
          ? error.response?.data.message
          : "注册失败，请检查输入信息"
      )
    } finally {
      setLoading(false)
    }
  }

  const getVerificationCode = async () => {
    try {
      await AuthApi.getVerificationCode(email)
      message.success("邮件验证码已发送")
      setCountdown(60) // 设置 60 秒倒计时
    } catch (error) {
      message.error("验证码发送失败，请稍后重试")
    }
  }

  // 倒计时逻辑
  useEffect(() => {
    if (countdown > 0) {
      const timer = setInterval(() => {
        setCountdown((prev) => prev - 1)
      }, 1000)

      // 清理定时器
      return () => clearInterval(timer)
    }
  }, [countdown])

  return (
    <div className="w-[480px]">
      <Form<UserRegisterForm>
        form={form}
        labelCol={{ span: 4 }}
        onFinish={onFinish}
        onValuesChange={(_, values) => setEmail(values.email || "")}
        initialValues={{
          username: "",
          password: "",
          email: "",
          verificationCode: "",
        }}>
        <Form.Item<UserRegisterForm>
          label="用户名"
          name="username"
          rules={[{ required: true, message: "请输入用户名!" }]}>
          <Input />
        </Form.Item>
        <Form.Item<UserRegisterForm>
          label="密码"
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}>
          <Input.Password />
        </Form.Item>
        <Form.Item<UserRegisterForm>
          label="电子邮箱"
          name="email"
          rules={[
            { required: true, message: "请输入电子邮箱!" },
            {
              pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/gm,
              message: "电子邮箱格式不正确！",
            },
          ]}>
          <Input />
        </Form.Item>
        <Form.Item<UserRegisterForm>
          label="验证码"
          name="verificationCode"
          rules={[
            { required: true, message: "请输入邮箱验证码!" },
            { len: 6, message: "邮箱验证码格式错误！" },
          ]}>
          <Space.Compact className="w-[100%]">
            <Input />
            <Button
              type="primary"
              disabled={!email || countdown > 0} // 禁用条件：无 email 或倒计时未结束
              onClick={getVerificationCode}>
              {countdown > 0 ? `${countdown}s 后重试` : "获取验证码"}
            </Button>
          </Space.Compact>
        </Form.Item>
        <div className="flex justify-end items-baseline gap-[10px]">
          <NavLink to="/login">已有账号？立即登录</NavLink>
          <Button htmlType="reset">重置</Button>
          <Button type="primary" htmlType="submit" loading={loading}>
            注册
          </Button>
        </div>
      </Form>
    </div>
  )
}
