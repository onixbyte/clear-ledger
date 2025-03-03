import { useEffect } from "react"
import { Outlet } from "react-router"
import { useNavigate } from "react-router-dom"
import { Button, Layout, Menu, message } from "antd"
import { Content, Header } from "antd/es/layout/layout"
import Sidebar from "antd/es/layout/Sider"
import { useAppDispatch, useAppSelector } from "@/hooks/store"
import { logout } from "@/store/auth-slice"
import * as LedgerApi from "@/api/ledger"
import "./index.scss"
import staticMenuItems from "./menu-item.ts"
import { setLedgers } from "@/store/ledger-slice.ts"
import { MenuItem } from "@/types"

export const DashboardLayout = () => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const _logout = async () => {
    dispatch(logout())
    message.success("登出成功")
    navigate("/login", { replace: true })
  }

  const ledgers = useAppSelector((state) => state.ledger.ledgers)

  // 在组件挂载时获取账本数据
  useEffect(() => {
    (async () => {
      try {
        const ledgerData = await LedgerApi.getLedgers()
        dispatch(setLedgers(ledgerData))
      } catch (error) {
        console.error("获取账本失败:", error)
      }
    })()
  }, [dispatch])

  // 动态生成菜单项：静态项 + 账本项
  const menuItems = [
    ...ledgers.map((ledger) => ({
      key: `ledger#${ledger.id}`,
      label: ledger.name
    }) as MenuItem),
    ...staticMenuItems,
  ]

  return (
    <Layout className="dashboard-wrapper">
      <Header className="dashboard-header">
        Clear Ledger
        <Button type="text" onClick={_logout}>注销</Button>
      </Header>
      <Layout className="dashboard-content-wrapper">
        <Sidebar className="dashboard-sidebar-wrapper">
          <Menu items={menuItems} className="sidebar-menu"/>
        </Sidebar>
        <Content className="dashboard-content">
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}
