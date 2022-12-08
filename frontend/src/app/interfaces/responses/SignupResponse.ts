import { User } from '../User'
export interface SignupResponse {
  timeStamp: Date
  statusCode: number
  status: string
  reason: string
  message: string
  developerMessage: string
  path: string
  data: { user: User }
}
