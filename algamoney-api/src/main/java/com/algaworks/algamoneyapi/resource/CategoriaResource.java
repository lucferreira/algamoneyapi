package com.algaworks.algamoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRespository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRespository categoriaRepository;

	@GetMapping
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Categoria> buscarCategoria(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		}
		return categoria;

	}

	@PostMapping("/salvarcategoria")
	public ResponseEntity<Categoria> salvarCategoria(@Valid @RequestBody Categoria categoria) {
		Optional<Categoria> categ = categoriaRepository.findByNome(categoria.getNome());
		if (categ.isEmpty()) {
			categoriaRepository.save(categoria);
			return ResponseEntity.ok(categoria);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Categoria já cadastrada");
		}

	}

	@PutMapping("/atualizarcategoria/{id}")
	public Categoria atualizarCategoria(@Valid @RequestBody Categoria categoria, @PathVariable Long id) {
		Optional<Categoria> categ = categoriaRepository.findById(id);
		if (!categ.isEmpty()) {
			BeanUtils.copyProperties(categoria, categ);
			categoriaRepository.save(categoria);
			return categoria;
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Categoria não encontrada");
		}

	}
	@DeleteMapping("/deletarcategoria/{id}")
	public void deletarCategoria(@PathVariable Long id) {
		Optional<Categoria> categ = categoriaRepository.findById(id);
		if (!categ.isEmpty()) {
			categoriaRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Categoria não encontrada");
		}
		
		
	}

}
