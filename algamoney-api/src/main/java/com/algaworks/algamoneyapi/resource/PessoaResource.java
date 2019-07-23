package com.algaworks.algamoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaService pessoaService;

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

		if (pess.isEmpty()) {
			pessoaRepository.save(pessoa);
			publisher.publishEvent(new RecursoCriadoEvento(this, response, pessoa.getIdPessoa()));
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Pessoa já cadastrada");
		}

	}

	@PutMapping("/atualizarpessoa/{idPessoa}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long idPessoa, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(idPessoa, pessoa);
		return ResponseEntity.ok(pessoaSalva);

	}
	
	@PutMapping("/atualizarpessoa/{idPessoa}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void atualizarPessoaParcial(@PathVariable Long idPessoa, @Valid @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(idPessoa, ativo);

	}

	@DeleteMapping("/deletarpessoa/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPessoa(@PathVariable Long id) {
		Optional<Pessoa> pess = pessoaRepository.findById(id);
		if (!pess.isEmpty()) {
			pessoaRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Categoria não encontrada");
		}
	}
}
