package com.algaworks.algafood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.domain.Cliente;
import com.algaworks.algafood.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoClienteService;	
	
	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {		
		this.ativacaoClienteService = ativacaoClienteService;
		System.out.println("MeuPrimeiroController: " + ativacaoClienteService);
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente diego = new Cliente("Diego", "diego@test.com", "43999999999");
		ativacaoClienteService.ativar(diego);
		return "Hello!";
	}
	
}
