package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByTitulo(String nome);

    List<Topico> findByCursoNome(String nome);

    /*
     * Busca personalizada !
     * 
     * @Query("select t from Topico t where t.curso.nome = :nome") List<Topico>
     * carregarPorNomeDoCurso(String nome);
     */

}
