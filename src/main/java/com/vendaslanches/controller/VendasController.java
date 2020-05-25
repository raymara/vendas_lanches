package com.vendaslanches.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendaslanches.model.Lanche;
import com.vendaslanches.model.Pedido;
import com.vendaslanches.model.Tipo;
import com.vendaslanches.service.VendasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="API REST vendas lanches")
@RestController
@RequestMapping("/vendas")
public class VendasController {
	@Autowired
	private VendasService vendasService;
	
	
	@ApiOperation(value="Calcula o valor do lanche perante seus respectivos Ingredientes."
			+ "@param tipoLanche {@link Tipo} Tipo do lanche pedido.\r\n" + 
			"Retorna o valor calculado por seus ingredientes.")
	@GetMapping("/calculalanche/{tipo}")
	public ResponseEntity<Lanche> calcularLanche(@PathVariable("tipo") Tipo tipo) {
		Lanche lanche = vendasService.calculaLanche(tipo);
		return ResponseEntity.ok(lanche);
	}
	
	@ApiOperation(value="Serviço responsável por preparar os Ingredientes do pedido e o valor final."
			+ "@param {@link Pedido} Objeto com as informações do pedido.\r\n" + 
			".retorna Objeto com o valor total do pedido.")
	@PutMapping("/calculapedido")
	public ResponseEntity<Pedido> criar(@Validated @RequestBody Pedido pedido) {
		Pedido newPedido = vendasService.calculaPedido(pedido);
		return ResponseEntity.ok(newPedido);
	}

}
