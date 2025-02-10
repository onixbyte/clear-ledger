import { Layout } from "antd"
import { Content, Header } from "antd/es/layout/layout"
import Sider from "antd/es/layout/Sider"
import { Outlet } from "react-router"

export const DashboardLayout = () => {
  return (
    <Layout className="dashboard-wrapper">
      <Header className="dashboard-header">
      </Header>
      <Layout className="dashboard-content-wrapper">
        <Sider className="dashboard-sidebar"></Sider>
        <Content className="dashboard-content">
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}