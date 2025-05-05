import { ShopCategory } from '../models/shop-category';
import { Product } from './product'; // Adjust the path if needed


export class Shop {
  idShop?: number;
  nom: string;
  location: string;
  contactNumber: string;
  openingHours: string;
  closinghours: string;
  email: string;
  description: string;
  categorie: ShopCategory;
  productList?: Product[];

  // Constructor with default values
  constructor(
    nom: string = '',
    location: string = '',
    contactNumber: string = '',
    openingHours: string = '',
    closinghours: string = '',
    email: string = '',
    description: string = '',
    categorie: ShopCategory = ShopCategory.MEN, // Default category
    idShop?: number
  ) {
    this.nom = nom;
    this.location = location;
    this.contactNumber = contactNumber;
    this.openingHours = openingHours;
    this.closinghours = closinghours;
    this.email = email;
    this.description = description;
    this.categorie = categorie;
    if (idShop) {
      this.idShop = idShop;
    }
  }
}
