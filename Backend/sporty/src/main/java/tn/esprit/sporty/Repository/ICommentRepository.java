package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.Comment;
import tn.esprit.sporty.Entity.Product;


import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProduct(Product product);

}
