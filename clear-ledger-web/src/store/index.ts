import { configureStore } from "@reduxjs/toolkit"
import authReducer from "./auth-slice"
import ledgerReducer from "./ledger-slice"
import { User } from "@/types"

const preloadedState = {
  auth: {
    user: JSON.parse(localStorage.getItem("user") || `{"id": "", "username": "", "email": ""}`) as User,
    token: localStorage.getItem("token"),
    isAuthenticated: !!localStorage.getItem("token"),
  },
}

export const store = configureStore({
  reducer: {
    auth: authReducer,
    ledger: ledgerReducer
  },
  preloadedState,
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
export type AppStore = typeof store