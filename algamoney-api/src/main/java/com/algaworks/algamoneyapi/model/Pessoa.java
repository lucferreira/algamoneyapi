package com.algaworks.algamoneyapi.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@EqualsAndHashCode
public class Pessoa {
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Idpessoa")
	private Long idPessoa;
	@Getter @Setter
	@NotNull
	@Size(min = 5, max = 200)
	private String nome;
	@Getter @Setter
	@NotNull
	private boolean ativo;
	@Getter @Setter
	@Embedded
	private Endereco endereco;

}
