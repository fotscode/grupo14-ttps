import { Emprendimiento } from "../Emprendimiento";
export interface TopEmprendimientosResponse {
  timeStamp: Date
  statusCode: number
  status: string
  reason: string
  message: string
  developerMessage: string
  path: string
  data: { topDonaciones: Emprendimiento[], topManguitos: Emprendimiento[] }
}
