export type Ledger = {
  id: string
  name: string
  description: string
  role: string
  joinedAt: string
}

export type CreateLedgerRequest = {
  name: string
  description: string
}

