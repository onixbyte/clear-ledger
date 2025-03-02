import { Outlet } from "react-router"
import "./index.scss"

export const EmptyLayout = () => {
  return (
    <div className="layout-wrapper">
      <Outlet />
    </div>
  )
}