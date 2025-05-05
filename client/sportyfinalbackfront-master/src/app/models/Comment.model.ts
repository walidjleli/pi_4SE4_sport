import { Product } from './product';
export interface Comment {
    id?: number;         // backend uses 'id'
    text: string;
    likes: number;
    dislikes: number;
    product?: { idProduct: number };// relationship with Product
  }
  