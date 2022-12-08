export interface TokenResponse {
  timeStamp: Date
  statusCode: number
  status: string
  reason: string
  message: string
  developerMessage: string
  path: string
  data: { tokens: { access_token: string; refresh_token: string } }
}
