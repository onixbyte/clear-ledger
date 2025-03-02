import React from "react"
import { createRoot } from "react-dom/client"
import { Provider } from "react-redux"
import { BrowserRouter, Route, Routes } from "react-router"
import "./index.scss"
import { store } from "@/store"
import { DashboardLayout } from "@/layout/dashboard-layout"
import { EmptyLayout } from "@/layout/empty-layout"
import { LoginPage } from "@/pages/login-page"
import { RegisterPage } from "@/pages/register-page"

createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<DashboardLayout />}>
          </Route>
          <Route path="/login" element={<EmptyLayout />}>
            <Route index element={<LoginPage />} />
          </Route>
          <Route path="/register" element={<EmptyLayout />}>
            <Route index element={<RegisterPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>,
  </React.StrictMode>
)