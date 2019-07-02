package com.algaworks.algamoneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

public class RecursoCriadoEvento extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private HttpServletResponse resp;
	@Setter
	@Getter
	private Long codigo;

	public RecursoCriadoEvento(Object source, HttpServletResponse resp, Long codigo) {
		super(source);
		this.resp = resp;
		this.codigo = codigo;
	}

}
