package tn.esprit.sporty.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Shop;
import tn.esprit.sporty.Repository.IShopRepository;


import java.util.List;

@RequiredArgsConstructor
@Service
public class ShopService implements IShopService {
    private final IShopRepository shopRepository;

    @Override
    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public Shop retrieveShop(Long idShop) {
        return shopRepository.findById(idShop).orElse(null);
    }

    @Override
    public List<Shop> retrieveAll() {
        return shopRepository.findAll();
    }

    @Override
    public void deleteShop(Long idShop) {
        shopRepository.deleteById(idShop);
    }

    @Override
    public Shop updateShop(Shop shop) {
        return shopRepository.save(shop);
    }
}
