package tn.esprit.sporty.Service;


import tn.esprit.sporty.Entity.Shop;

import java.util.List;

public interface IShopService {
    List<Shop> retrieveAll();
    Shop retrieveShop(Long id);
    Shop createShop(Shop shop);
    Shop updateShop(Shop shop);
    void deleteShop(Long id);
}
