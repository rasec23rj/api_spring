package com.example.demo.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.model.Curso;
import com.example.demo.model.Topico;
import com.example.demo.repository.CursoRepository;

import org.hibernate.validator.constraints.Length;

public class TopicoForm {

    
    @NotNull
    @NotEmpty
    @Length(min = 5, message = "O minimo e de 5")
    private String titulo;

    @NotNull
    @NotEmpty
    private String mensagem;
    private String nomeCurso;

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return this.nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
    public Topico converter(CursoRepository repository){
        Curso curso = repository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}