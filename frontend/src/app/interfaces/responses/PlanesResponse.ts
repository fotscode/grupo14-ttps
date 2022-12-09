import { Plan } from "../Plan"

export interface PlanesResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { planes: Plan[] }
}