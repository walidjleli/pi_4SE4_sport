package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.sporty.Entity.File;



@Repository
public interface FileRepo extends JpaRepository<File,Long>{

}
