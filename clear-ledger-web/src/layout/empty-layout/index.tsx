import { Outlet } from "react-router"

export const EmptyLayout = () => {
  return (
    <div className="w-[100vw] h-[100vh] flex justify-center items-center m-0">
      <Outlet />
    </div>
  )
}