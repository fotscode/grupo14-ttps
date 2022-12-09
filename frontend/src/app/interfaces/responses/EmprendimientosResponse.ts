import { Emprendimiento } from "../Emprendimiento";
export interface EmprendimientoResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { emprendimientos: Emprendimiento[] }
}