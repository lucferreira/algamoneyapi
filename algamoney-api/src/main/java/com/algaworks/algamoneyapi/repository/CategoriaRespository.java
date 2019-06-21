package com.algaworks.algamoneyapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoneyapi.model.Categoria;

public interface CategoriaRespository extends JpaRepository<Categoria, Long>{

	Optional<Categoria> findByNome(String nome);

}
