package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Shop;
import tn.esprit.sporty.Service.IShopService;


import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShopController {

    private final IShopService shopService;

    @PostMapping("/add")
    public ResponseEntity<Shop> addShop(@RequestBody Shop shop) {
        Shop savedShop = shopService.createShop(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShop);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> retrieveShop(@PathVariable Long id) {
        Shop shop = shopService.retrieveShop(id);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Shop>> retrieveAll() {
        return ResponseEntity.ok(shopService.retrieveAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok("Shop deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Shop> updateShop(@RequestBody Shop shop, @PathVariable Long id) {
        shop.setIdShop(id);  // Assuming `idShop` is the ID field in the Shop entity
        Shop updatedShop = shopService.updateShop(shop);
        return ResponseEntity.ok(updatedShop);
    }
}
