import { Layout, Menu } from "antd"
import { Content, Header } from "antd/es/layout/layout"
import Sider from "antd/es/layout/Sider"
import { Outlet } from "react-router"
import "./index.scss"
import menuItems from "./menu-item.ts"

export const DashboardLayout = () => {
  return (
    <Layout className="dashboard-wrapper">
      <Header className="dashboard-header">
        Clear Ledger
      </Header>
      <Layout className="dashboard-content-wrapper">
        <Sider className="dashboard-sidebar-wrapper">
          <Menu items={menuItems}/>
        </Sider>
        <Content className="dashboard-content">
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}