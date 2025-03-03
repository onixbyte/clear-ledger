import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { Ledger } from "@/types/ledger"

interface LedgerState {
  ledgers: Ledger[]
}

const initialState: LedgerState = {
  ledgers: [],
}

const ledgerSlice = createSlice({
  name: "ledger",
  initialState,
  reducers: {
    setLedgers: (state, action: PayloadAction<Ledger[]>) => {
      state.ledgers = action.payload
    },
  },
})

export const { setLedgers } = ledgerSlice.actions
export default ledgerSlice.reducer