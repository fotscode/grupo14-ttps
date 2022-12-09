import { Pago } from "../Pago"

export interface PagosResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { pagos: Pago[]}
}