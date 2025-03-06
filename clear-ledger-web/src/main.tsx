import React from "react"
import { createRoot } from "react-dom/client"
import { BrowserRouter, Route, Routes } from "react-router"
import { Provider } from "react-redux"
import "@ant-design/v5-patch-for-react-19"
import "./index.css"
import { store } from "@/store"
import { DashboardLayout } from "@/layout/dashboard-layout"
import { EmptyLayout } from "@/layout/empty-layout"
import { LoginPage } from "@/pages/login-page"
import { RegisterPage } from "@/pages/register-page"
import { ProtectedRoute } from "@/components/protected-route"
import { LedgerPage } from "@/pages/ledger-page"

createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route element={<ProtectedRoute />}>
            <Route path="/" element={<DashboardLayout />}>
              <Route path="ledgers/:ledgerId" element={<LedgerPage />} />
            </Route>
          </Route>
          <Route path="/login" element={<EmptyLayout />}>
            <Route index element={<LoginPage />} />
          </Route>
          <Route path="/register" element={<EmptyLayout />}>
            <Route index element={<RegisterPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
)
