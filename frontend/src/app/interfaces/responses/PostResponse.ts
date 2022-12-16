import { Post } from '../Post'
export interface PostResponse {
  timeStamp: Date
  statusCode: number
  status: string
  reason: string
  message: string
  developerMessage: string
  path: string
  data: { posts?: Post[], post?: Post, length?: number }
}
