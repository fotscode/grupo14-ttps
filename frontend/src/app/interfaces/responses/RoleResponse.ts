import { Role } from "../Role"

export interface RoleResponse {
  timeStamp: Date
  statusCode: number
  status: string
  reason: string
  message: string
  developerMessage: string
  path: string
  data: { roles: Role[] }
}
