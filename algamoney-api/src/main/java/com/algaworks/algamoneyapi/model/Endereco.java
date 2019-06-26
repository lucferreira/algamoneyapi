package com.algaworks.algamoneyapi.model;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Endereco {
	
	@Getter @Setter
	private String logradouro;
	@Getter @Setter
	private String numero;
	@Getter @Setter
	private String complemento;
	@Getter @Setter
	private String bairro;
	@Getter @Setter
	private String cep;
	@Getter @Setter
	private String cidade;
	@Getter @Setter
	private String estado;

}
