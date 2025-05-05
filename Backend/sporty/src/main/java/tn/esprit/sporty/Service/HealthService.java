package tn.esprit.sporty.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tn.esprit.sporty.Entity.File;
import tn.esprit.sporty.Entity.Health;
import tn.esprit.sporty.Repository.FileRepo;
import tn.esprit.sporty.Repository.HealthRepo;

@RequiredArgsConstructor
@Service


public class HealthService  {

    private final HealthRepo hrepo;
    private final FileRepo frepo;



    public Health addHealth(Health h, MultipartFile file) throws IOException{
        if (file != null && !file.isEmpty()) {
            File fileEntity = new File();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setType(file.getContentType());
            fileEntity.setSize(file.getSize());
            fileEntity.setData(file.getBytes());
            
            frepo.save(fileEntity);
            h.setFile(fileEntity);
        }


        return hrepo.save(h);
    }

    public Health updateHealth(Health h, MultipartFile file) throws IOException {
        // Get the existing health record from database
        Health existingHealth = hrepo.findById(h.getId()).orElse(null);
        
        if (existingHealth == null) {
            throw new RuntimeException("Health record not found with id: " + h.getId());
        }
        
        // Handle file update if a new file is provided
        if (file != null && !file.isEmpty()) {
            // If there was an existing file, delete it first
            if (existingHealth.getFile() != null) {
                frepo.delete(existingHealth.getFile());
            }
            
            // Create and save new file
            File fileEntity = new File();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setType(file.getContentType());
            fileEntity.setSize(file.getSize());
            fileEntity.setData(file.getBytes());
            
            frepo.save(fileEntity);
            h.setFile(fileEntity);
        } else {
            // If no new file provided, keep the existing file
            h.setFile(existingHealth.getFile());
        }
        
        // Update other fields
        return hrepo.save(h);
    }

    public void deleteHealth(Long id){
        hrepo.deleteById(id);
    }

    public List<Health> findAll(){
        return hrepo.findAll();
    }

    public Health findHealthById(Long id){
        return hrepo.findById(id).orElse(null);
    }

    public Health findHealthByCondition(String condition){
        return hrepo.findHealthByUserCondition(condition);
    }

    
    
}

