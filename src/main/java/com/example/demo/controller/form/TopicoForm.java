package com.example.demo.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.model.Curso;
import com.example.demo.model.Topico;
import com.example.demo.model.Usuario;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.CursoRepository;

import org.hibernate.validator.constraints.Length;

public class TopicoForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String mensagem;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String nomeCurso;
    private Integer idAutor;

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

    public Integer getIdAutor() {
        return this.idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Topico converter(CursoRepository repository, AutorRepository autorRepository) {
        Curso curso = repository.findByNome(nomeCurso);
        Usuario autor = autorRepository.findById(idAutor);

        return new Topico(titulo, mensagem, curso, autor);
    }

    @Override
    public String toString() {
        return "{" + " titulo='" + getTitulo() + "'" + ", mensagem='" + getMensagem() + "'" + ", nomeCurso='"
                + getNomeCurso() + "'" + "}";
    }

}
