package com.algaworks.algamoneyapi.event.listner;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvento;

@Component
public class RecursoCriadoListner implements ApplicationListener<RecursoCriadoEvento>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvento event) {
		HttpServletResponse resp = event.getResp();
		Long codigo = event.getCodigo();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		resp.setHeader("Location", uri.toASCIIString());
	}

}
