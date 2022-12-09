export interface Post {
  id?: number
  fotos: [{ id?: number; image: string }]
  descripcion: string
  titulo: string
}
