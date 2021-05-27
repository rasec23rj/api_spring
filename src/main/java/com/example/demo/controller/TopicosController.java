package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import com.example.demo.controller.dto.TopicoDto;
import com.example.demo.controller.dto.TopicoForm;
import com.example.demo.model.Topico;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@ResponseBody
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

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

    @GetMapping(path = "/ids/", params = "id" )
    public List<TopicoDto> listaId(Long  id) {
        System.out.println("id: " + id);
        List<Topico> topicos = (List<Topico>) topicoRepository.findById(id).get();
        return TopicoDto.converter(topicos);

    }

    @GetMapping(path = "/curso")
    public List<TopicoDto> listaTopicosCurso(String nome) {
        List<Topico> topicos = topicoRepository.findByCursoNome(nome);
        /*
         * Busca personalizada ! List<Topico> carregarPorNomeDoCurso =
         * topicoRepository.carregarPorNomeDoCurso(nome);
         */
        return TopicoDto.converter(topicos);

    }

    @PostMapping

    public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

}
