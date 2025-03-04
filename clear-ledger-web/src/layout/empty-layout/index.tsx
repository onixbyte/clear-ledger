import { Outlet } from "react-router"
import "./index.css"

export const EmptyLayout = () => {
  return (
    <div className="layout-wrapper">
      <Outlet />
    </div>
  )
}