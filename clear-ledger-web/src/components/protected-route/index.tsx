import { useAppSelector } from "@/hooks/store"
import { Navigate, Outlet, useLocation } from "react-router-dom"

export const ProtectedRoute = () => {
  const isAuthenticated = useAppSelector((state) => state.auth.isAuthenticated)
  const location = useLocation()

  if (!isAuthenticated) {
    // 未登录时跳转到 /login，并保存原路径
    return <Navigate to="/login" state={{ from: location }} replace />
  }

  // 已登录时渲染子路由
  return <Outlet />
}