package com.algaworks.algamoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listarPessoas() {
		return pessoaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Pessoa> buscarPessoa(@PathVariable Long id) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		if (pessoa.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrados");
		}
		return pessoa;
	}

	@PostMapping("/salvarpessoa")
	public ResponseEntity<Pessoa> salvarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Optional<Pessoa> pess = pessoaRepository.findByNome(pessoa.getNome());
		publisher.publishEvent(new RecursoCriadoEvento(this, response, pessoa.getIdPessoa()));
		if (pess.isEmpty()) {
			pessoaRepository.save(pessoa);
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Pessoa já cadastrada");
		}

	}

	@PutMapping("/atualizarpessoa/{id}")
	public Pessoa atualizarPessoa(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id) {
		Optional<Pessoa> pess = pessoaRepository.findById(id);
		if (!pess.isEmpty()) {
			BeanUtils.copyProperties(pessoa, pess);
			pessoaRepository.save(pessoa);
			return pessoa;
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Categoria não encontrada");
		}

	}

	@DeleteMapping("/deletarpessoa/{id}")
	public void deletarPessoa(@PathVariable Long id) {
		Optional<Pessoa> categ = pessoaRepository.findById(id);
		if (!categ.isEmpty()) {
			pessoaRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Categoria não encontrada");
		}
	}
}
