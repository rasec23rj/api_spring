package com.example.demo.repository;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Usuario, Long>{
    
    Usuario findById(Integer idAutor);
}
