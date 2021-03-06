package com.example.demo.repository;

import java.util.List;
import com.example.demo.model.Topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // List<Topico> findByTitulo(String nome);
    Page<Topico> findByTitulo(String nome, Pageable paginacao);

    List<Topico> findByCursoNome(String nome);

    /*
     * Busca personalizada !
     * 
     * @Query("select t from Topico t where t.curso.nome = :nome") List<Topico>
     * carregarPorNomeDoCurso(@Param("nome") String nome);
     */

}
