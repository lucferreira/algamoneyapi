package com.algaworks.algamoneyapi.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
public class ExceptionHandlerAlgamoney extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = messages.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();
		// São mensagens que não consegui ler
		return handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	public static class Erro {
		
		@Getter @Setter
		private String mensagemUsuario;
		@Getter @Setter
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor){
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
			this.mensagemUsuario = mensagemUsuario;
		}
		
	}

}
