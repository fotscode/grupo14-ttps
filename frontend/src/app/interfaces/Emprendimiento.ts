import { Post } from "./Post"
import {Categoria} from "./Categoria"
import {Plan} from "./Plan"
import {RedSocial} from "./RedSocial"
import {Manguito} from "./Manguito"
export interface Emprendimiento {
    id?: number
    domainUrl: string
    nombre: string
    descripcion: string
    valorManguito: number
    filterByDonations:boolean
    filterByManguitos:boolean
    imagen: string
    categorias: Categoria[]
    posts:Post[]
    planes:Plan[]
    redesSociales:RedSocial[]
    manguitos:Manguito[]

  }
  