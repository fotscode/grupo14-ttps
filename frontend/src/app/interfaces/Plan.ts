import { Pago } from "./Pago"

export interface Plan{
    id?:number
    titulo:string
    descripcion:string
    monto:number
    pagos:Pago[]
}