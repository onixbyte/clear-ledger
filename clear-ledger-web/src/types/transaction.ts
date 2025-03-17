export type Transaction = {
  id: string
  ledgerId: string
  userId: string
  username: string
  amount: number
  description: string
  transactionDate: string
}

export type CreateTransactionRequest = {
  ledgerId: string
  amount: number
  description: string
  transactionDate: string
}

export type FilterTransactionParams = {
  transactionDateStart?: string
  transactionDateEnd?: string
}
