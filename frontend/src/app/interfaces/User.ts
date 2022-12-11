import { Emprendimiento } from "./Emprendimiento";

export interface User{
  id?: number;
  email: string;
  fullName: string;
  password: string;
  emprendimiento:Emprendimiento
}
