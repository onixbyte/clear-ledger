import { Outlet } from "react-router"
import { Button, Layout, Menu, message } from "antd"
import { Content, Header } from "antd/es/layout/layout"
import Sidebar from "antd/es/layout/Sider"
import { useAppDispatch } from "@/hooks/store"
import { logout } from "@/store/auth-slice"
import "./index.scss"
import menuItems from "./menu-item.ts"
import { useNavigate } from "react-router-dom"

export const DashboardLayout = () => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const _logout = async () => {
    dispatch(logout())
    message.success("登出成功")
    navigate("/login", { replace: true })
  }

  return (
    <Layout className="dashboard-wrapper">
      <Header className="dashboard-header">
        Clear Ledger
        <Button type="text" onClick={_logout}>注销</Button>
      </Header>
      <Layout className="dashboard-content-wrapper">
        <Sidebar className="dashboard-sidebar-wrapper">
          <Menu items={menuItems} />
        </Sidebar>
        <Content className="dashboard-content">
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}
