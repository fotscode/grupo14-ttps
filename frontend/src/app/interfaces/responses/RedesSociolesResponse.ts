import { RedSocial } from "../RedSocial"

export interface RedesSocialesResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { redesSociales: RedSocial[] }
}