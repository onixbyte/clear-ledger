import { useParams } from "react-router"

export const LedgerPage = () => {
  const { ledgerId } = useParams<{ ledgerId: string }>()

  return <div>Ledger ID = {ledgerId}</div>
}
