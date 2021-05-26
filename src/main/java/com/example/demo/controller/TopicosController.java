package com.example.demo.controller;

import java.util.List;
import com.example.demo.controller.dto.TopicoDto;
import com.example.demo.model.Topico;
import com.example.demo.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
@ResponseBody
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping(path = "")
    public List<TopicoDto> lista(String nome) {
        if (nome == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByTitulo(nome);
            return TopicoDto.converter(topicos);
        }
    }

    @GetMapping(path = "/curso")
    public List<TopicoDto> listaTopicosCurso(String nome) {
        List<Topico> topicos = topicoRepository.findByCursoNome(nome);

        /*
         * Busca personalizada !
         * List<Topico> carregarPorNomeDoCurso =
         * topicoRepository.carregarPorNomeDoCurso(nome);
         */

        return TopicoDto.converter(topicos);

    }

}
