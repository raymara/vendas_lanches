package com.vendaslanches;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.vendaslanches.model.Adicional;
import com.vendaslanches.model.Lanche;
import com.vendaslanches.model.Pedido;
import com.vendaslanches.model.Tipo;
import com.vendaslanches.model.TipoIngrediente;
import com.vendaslanches.service.VendasService;


@SpringBootTest
public class VendasLanchesTest {
	@Mock
	private VendasService vendasService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockValoresTipoIngredientes();
	}
	private void mockValoresTipoIngredientes() {
		Mockito.when(vendasService.getValor(TipoIngrediente.ALFACE)).thenReturn(new BigDecimal(0.40));
		Mockito.when(vendasService.getValor(TipoIngrediente.BACON)).thenReturn(new BigDecimal(2.00));
		Mockito.when(vendasService.getValor(TipoIngrediente.BURGER)).thenReturn(new BigDecimal(3.00));
		Mockito.when(vendasService.getValor(TipoIngrediente.OVO)).thenReturn(new BigDecimal(0.80));
		Mockito.when(vendasService.getValor(TipoIngrediente.QUEIJO)).thenReturn(new BigDecimal(1.50));
	}
	
	@Test
	public void calcularLanche() {
		Tipo xBacon = Tipo.XBACON;
		Tipo xBurger = Tipo.XBURGER;
		Tipo xEgg = Tipo.XEGG;
		Tipo xEggBacon = Tipo.XEGGBACON;
		
		VendasService vendasService = new VendasService();
		
		BigDecimal xBaconValor = new BigDecimal(6.50).setScale(2, BigDecimal.ROUND_HALF_UP);
		Lanche actual = vendasService.calculaLanche(xBacon);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(xBaconValor), actual.getValor());
		
		BigDecimal xBurgerValor = new BigDecimal(4.50).setScale(2, BigDecimal.ROUND_HALF_UP);
		actual = vendasService.calculaLanche(xBurger);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(xBurgerValor), actual.getValor());
		
		BigDecimal xEggValor = new BigDecimal(5.30).setScale(2, BigDecimal.ROUND_HALF_UP);
		actual = vendasService.calculaLanche(xEgg);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(xEggValor), actual.getValor());
		
		BigDecimal xEggBaconValor = new BigDecimal(7.30).setScale(2, BigDecimal.ROUND_HALF_UP);
		actual = vendasService.calculaLanche(xEggBacon);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(xEggBaconValor), actual.getValor());
	}

	// Teste para calcular valor do pedido sem promoções
	@Test
	public void calcularPedido() {
		Pedido pedido = new Pedido();
		pedido.setLanche(Tipo.XBURGER);

		Adicional ad1 = new Adicional();
		ad1.setTipoIngrediente(TipoIngrediente.OVO);
		ad1.setQtde(1);
		Adicional ad2 = new Adicional();
		ad2.setTipoIngrediente(TipoIngrediente.BACON);
		ad2.setQtde(2);
		pedido.setAdicionais(Arrays.asList(ad1, ad2));

		BigDecimal expected = new BigDecimal(9.30).setScale(2, BigDecimal.ROUND_HALF_UP);

		VendasService vendasService = new VendasService();

		Pedido actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());
		
		// Sem adicionais
		pedido.setAdicionais(null);
		expected = new BigDecimal(4.50).setScale(2, BigDecimal.ROUND_HALF_UP);

		actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());
	}

	// Testes para o calculo da promoção: Light
	@Test
	public void calcularPedidolight() {
		Pedido pedido = new Pedido();
		pedido.setLanche(Tipo.XBURGER);

		Adicional ad = new Adicional();
		ad.setTipoIngrediente(TipoIngrediente.ALFACE);
		ad.setQtde(2);
		pedido.setAdicionais(Arrays.asList(ad));

		BigDecimal expected = new BigDecimal(4.77).setScale(2, RoundingMode.HALF_EVEN);
		
		VendasService vendasService = new VendasService();

		Pedido actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());
	}

	
	// Testes para o calculo da promoção: Muito queijo
	@Test
	public void calcularPedidoMuitoQueijo() {
		Pedido pedido = new Pedido();
		pedido.setLanche(Tipo.XEGG);

		Adicional ad = new Adicional();
		ad.setTipoIngrediente(TipoIngrediente.QUEIJO);
		ad.setQtde(2);
		pedido.setAdicionais(Arrays.asList(ad));

		BigDecimal expected = new BigDecimal(6.80).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		VendasService vendasService = new VendasService();

		Pedido actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());

		ad.setQtde(6);
		pedido.setAdicionais(Arrays.asList(ad));
		expected = new BigDecimal(11.30).setScale(2, BigDecimal.ROUND_HALF_UP);

		actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());
	}
	// Testes para o calculo da promoção: Muita carne
		@Test
		public void calcularPedidoMuitaCarne() {
			Pedido pedido = new Pedido();
			pedido.setLanche(Tipo.XBACON);

			Adicional ad = new Adicional();
			ad.setTipoIngrediente(TipoIngrediente.BURGER);
			ad.setQtde(2);
			pedido.setAdicionais(Arrays.asList(ad));

			BigDecimal expected = new BigDecimal(9.50).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			VendasService vendasService = new VendasService();

			Pedido actual = vendasService.calculaPedido(pedido);
			Assertions.assertNotNull(actual);
			Assertions.assertEquals(formatValor(expected), actual.getTotal());

			ad.setQtde(6);
			pedido.setAdicionais(Arrays.asList(ad));
			expected = new BigDecimal(18.50).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			actual = vendasService.calculaPedido(pedido);
			Assertions.assertNotNull(actual);
			Assertions.assertEquals(formatValor(expected), actual.getTotal());
		}

	
	// Testes para o calculo de todas as promoções
	@Test
	public void calcularPedidoPromocoes() {
		Pedido pedido = new Pedido();
		pedido.setLanche(Tipo.XEGG);

		Adicional ad1 = new Adicional();
		ad1.setTipoIngrediente(TipoIngrediente.BURGER);
		ad1.setQtde(3);
		Adicional ad2 = new Adicional();
		ad2.setTipoIngrediente(TipoIngrediente.ALFACE);
		ad2.setQtde(2);
		Adicional ad3 = new Adicional();
		ad3.setTipoIngrediente(TipoIngrediente.QUEIJO);
		ad3.setQtde(5);
		pedido.setAdicionais(Arrays.asList(ad1, ad2, ad3));

		BigDecimal expected = new BigDecimal(14.94).setScale(2, RoundingMode.HALF_EVEN);

		VendasService vendasService = new VendasService();
		
		Pedido actual = vendasService.calculaPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(formatValor(expected), actual.getTotal());
	}
	
	private String formatValor(BigDecimal bd) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		return df.format(bd);
	}
	

}
