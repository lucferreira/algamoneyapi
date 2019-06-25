package com.algaworks.algamoneyapi.model;

import javax.persistence.Column;
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
@Table(name = "categoria")
@EqualsAndHashCode
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Idcategoria")
	@Getter @Setter
	private long idCategoria;
	
	@NotNull
	@Size(min = 3, max = 20)
	@Getter @Setter
	private String nome;

}
