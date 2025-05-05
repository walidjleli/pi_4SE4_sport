import { FootballProductsCategory } from "./football-products-category";
import { Shop } from './shop';
import { Comment } from './Comment.model'


export class Product {
  idProduct?: number; // Optional since it's auto-generated
  productName!: string;
  description!: string;
  stockquantity!: number;
  creationDate!: string; // Use string to manage date formatting in Angular
  brand!: string;
  price!: number;
  footballProductsCategory!: FootballProductsCategory;
  shop?:Shop;
  comments?: Comment[];
  
}
