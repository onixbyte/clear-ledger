import { useState } from "react"
import { NavLink } from "react-router"
import { useLocation, useNavigate } from "react-router-dom"
import { Button, Form, Input, message } from "antd"
import { AxiosError } from "axios"
import { useAppDispatch } from "@/hooks/store"
import * as AuthApi from "@/api/auth"
import { setUser } from "@/store/auth-slice"

type UserLoginForm = {
  username: string
  password: string
}

export const LoginPage = () => {
  const [form] = Form.useForm<UserLoginForm>()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const location = useLocation()

  const from =
    (location.state as { from?: { pathname: string } })?.from?.pathname || "/"

  const [loading, setLoading] = useState(false)

  const onFinish = async (values: UserLoginForm) => {
    setLoading(true)
    try {
      const response = await AuthApi.login(values.username, values.password)
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
    <div className="w-[480px]">
      <Form<UserLoginForm>
        form={form}
        labelCol={{ span: 4 }}
        onFinish={onFinish}
        initialValues={{ username: "", password: "" }}>
        <Form.Item<UserLoginForm>
          label="用户名"
          name="username"
          rules={[{ required: true, message: "请输入用户名!" }]}>
          <Input />
        </Form.Item>
        <Form.Item<UserLoginForm>
          label="密码"
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}>
          <Input.Password />
        </Form.Item>
        <div className="flex justify-end items-baseline gap-[10px]">
          <NavLink to="/register">还没有账号？立即注册</NavLink>
          <Button htmlType="reset">重置</Button>
          <Button type="primary" htmlType="submit" loading={loading}>
            登录
          </Button>
        </div>
      </Form>
    </div>
  )
}
