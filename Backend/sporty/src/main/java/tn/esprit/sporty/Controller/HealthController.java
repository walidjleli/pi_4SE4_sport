package tn.esprit.sporty.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tn.esprit.sporty.Entity.Health;
import tn.esprit.sporty.Service.HealthService;

import org.springframework.web.bind.annotation.PutMapping;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;



@RequiredArgsConstructor
@RequestMapping("Health")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HealthController {

    private final HealthService service;

    // localhost:8089/Sporty/Health/add




    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Health add(
            @RequestPart("health") Health h,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        
               
        return service.addHealth(h, file);
    }

    // localhost:8089/Sporty/Health/update
    @PutMapping("/update")
     Health updateHealth(@RequestPart Health health, 
     @RequestPart(required = false) MultipartFile file) throws IOException {
        return service.updateHealth(health, file);
    }
    


    // localhost:8089/Sporty/Health/delete/
    @DeleteMapping("/delete/{id}")
     void deleteHealth(@PathVariable ("id") Long id){
        service.deleteHealth(id);
     }

    // localhost:8089/Sporty/Health/All
     @GetMapping("/All")
     public List<Health> getAll() {
         return service.findAll() ;
     }
    // localhost:8089/Sporty/Health/get/
     @GetMapping("/get/{condition}")
     public Health getHealthByCondition(@PathVariable String condition) {
         return  service.findHealthByCondition(condition);
     }
     // localhost:8089/Sporty/Health/get/id
     @GetMapping("/{id}")
     public Health getHealthByID(@PathVariable Long id) {
         return service.findHealthById(id);
     }
     
     
     


}

