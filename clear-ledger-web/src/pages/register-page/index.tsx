import { useState } from "react"
import { NavLink } from "react-router"
import { useLocation, useNavigate } from "react-router-dom"
import { AxiosError } from "axios"
import { Button, Form, Input, message } from "antd"
import { useAppDispatch } from "@/hooks/store"
import * as AuthApi from "@/api/auth"
import { setUser } from "@/store/auth-slice.ts"
import "./index.css"

type UserRegisterForm = {
  username: string
  password: string
  email: string
}

export const RegisterPage = () => {
  const [form] = Form.useForm<UserRegisterForm>()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const location = useLocation()

  const from =
    (location.state as { from?: { pathname: string } })?.from?.pathname || "/"

  const [loading, setLoading] = useState(false)

  const onFinish = async (values: UserRegisterForm) => {
    setLoading(true)
    try {
      const response = await AuthApi.register(
        values.username,
        values.email,
        values.password
      )
      dispatch(setUser({ user: response.user, token: response.authorisation }))
      message.success("登录成功")
      navigate(from, { replace: true })
    } catch (error) {
      if (error instanceof AxiosError) {
        message.error(error.response?.data.message)
      } else {
        message.error("登录失败，请检查用户名或密码")
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="register-page-wrapper">
      <Form<UserRegisterForm>
        form={form}
        labelCol={{ span: 4 }}
        onFinish={onFinish}
        initialValues={{ username: "", password: "" }}>
        <Form.Item<UserRegisterForm>
          label="用户名"
          name="username"
          rules={[{ required: true, message: "请输入用户名!" }]}>
          <Input />
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
          label="密码"
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}>
          <Input.Password />
        </Form.Item>
        <div className="register-controls-wrapper">
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
