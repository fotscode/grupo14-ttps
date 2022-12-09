import { Manguito } from "../Manguito"

export interface ManguitosResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { manguitos: Manguito[]}
}