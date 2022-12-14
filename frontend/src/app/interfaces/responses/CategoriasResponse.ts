import { Categoria } from "../Categoria"

export interface CategoriasResponse{
    timeStamp: Date
    statusCode: number
    status: string
    reason: string
    message: string
    developerMessage: string
    path: string
    data: { categorias?: Categoria[], categoria?: Categoria }
}
