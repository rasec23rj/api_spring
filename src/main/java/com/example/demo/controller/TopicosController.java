package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.example.demo.controller.dto.DetathesTopicoDto;
import com.example.demo.controller.dto.TopicoDto;
import com.example.demo.controller.form.AtualizarTopicoForm;
import com.example.demo.controller.form.TopicoForm;
import com.example.demo.model.Topico;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.TopicoRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private AutorRepository usuarioRepository;

    @GetMapping(path = "")
    // Se for Lista usar no lugar de public page List
    public Page<TopicoDto> lista(@RequestParam(required = false) String nome,
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 3) Pageable paginacao) {

        if (nome == null) {
            // List<Topico> topicos = topicoRepository.findAll();
            /** paginação */
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converterPaginacao(topicos);
        } else {
            // List<Topico> topicos = topicoRepository.findByTitulo(nome);
            Page<Topico> topicos = topicoRepository.findByTitulo(nome, paginacao);
            return TopicoDto.converterPaginacao(topicos);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DetathesTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetathesTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();

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
    @JsonIgnore
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository, usuarioRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm form,
            UriComponentsBuilder uriBuilder) {

        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            Topico topicoPut = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topicoPut));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}
