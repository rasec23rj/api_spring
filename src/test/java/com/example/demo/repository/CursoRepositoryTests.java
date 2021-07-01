package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.demo.model.Curso;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CursoRepositoryTests {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloNome() {
        String nomeCurso = "HTML 5";
        Curso html5 = new Curso();
        html5.setNome(nomeCurso);
        testEntityManager.persist(html5);
    }

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloCategoria() {
        // String nomeCategoria = "Programação";
        // Curso curso = cursoRepository.findByCategoria(nomeCategoria);
        // assertNotNull(curso);
        // assertNotEquals(curso, curso.getCategoria());

        String nomeCategoria = "Programação";
        Curso html5 = new Curso();
        html5.setCategoria(nomeCategoria);
        testEntityManager.persist(html5);


    }
}