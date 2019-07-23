package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long idPessoa, Pessoa pessoa) {
		Pessoa pess = buscarPessoaPeloCodigo(idPessoa);
		BeanUtils.copyProperties(pessoa, pess, "idPessoa");
		return pessoaRepository.save(pess);

	}
	
	public void atualizarPropriedadeAtivo(Long idPessoa, Boolean ativo) {
		Pessoa pess = buscarPessoaPeloCodigo(idPessoa);
		pess.setAtivo(ativo);
		pessoaRepository.save(pess);
		
	}
	
	private Pessoa buscarPessoaPeloCodigo(Long idPessoa) {
		Pessoa pess = pessoaRepository.findByIdPessoa(idPessoa);
		if (pess == null) {	
			throw new EmptyResultDataAccessException(1);
		}
		return pess;
	}

}
