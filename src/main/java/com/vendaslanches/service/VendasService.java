package com.vendaslanches.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vendaslanches.model.Lanche;
import com.vendaslanches.model.Pedido;
import com.vendaslanches.model.Tipo;
import com.vendaslanches.model.TipoIngrediente;
import com.vendaslanches.model.Adicional;

@Service
public class VendasService {

	
	
	public Lanche calculaLanche(Tipo tipo) {
		Lanche lanche = new Lanche();

		List<TipoIngrediente> tipoIngredientesLanche = new ArrayList<>();
		tipoIngredientesLanche.addAll(Tipo.ingredientes(tipo));

		lanche.setValor(formatValor(calcularTotal(tipoIngredientesLanche, false)));

		return lanche;
	}

	
	public Pedido calculaPedido(Pedido pedido) {
		Pedido resultado = new Pedido();
		boolean adicional = false;

		if (pedido == null) {
			return resultado;
		}

		List<TipoIngrediente> tipoIngredientesLanche = new ArrayList<>();
		// Deve conter um lanche para efetuar o pedido.
		if (pedido.getLanche() != null) {
			tipoIngredientesLanche.addAll(Tipo.ingredientes(pedido.getLanche()));

			// Adiciona os adicionais ao lanche pedido.
			if (pedido.getAdicionais() != null && !pedido.getAdicionais().isEmpty()) {
				adicional = true;
				for (Adicional extra : pedido.getAdicionais()) {
					for (int i = 0; i < extra.getQtde(); i++) {
						tipoIngredientesLanche.add(extra.getTipoIngrediente());
					}
				}
			}
			resultado.setTotal(formatValor(calcularTotal(tipoIngredientesLanche, adicional)));
		}

		return resultado;
	}


	public BigDecimal calcularTotal(List<TipoIngrediente> tipoIngredientesLanche, boolean adicional) {
		BigDecimal total = BigDecimal.ZERO;

		for (TipoIngrediente TipoIngrediente : tipoIngredientesLanche) {
			total = total.add(this.getValor(TipoIngrediente));
		}

		// Caso possua adicionais, verificar e calcular as promoções
		if (adicional) {
			// Calcular promoções: MUITA CARNE e MUITO QUEIJO
			total = muitaCarneOuQueijo(tipoIngredientesLanche, total);
			// Calcular promoção:LIGHT
			total = light(tipoIngredientesLanche, total);
		}
		return total;
	}

	
	private BigDecimal light(List<TipoIngrediente> tipoIngredientesLanche, BigDecimal total) {
		BigDecimal desconto = BigDecimal.ZERO;
		boolean alface = false;
		boolean bacon = false;

		for (TipoIngrediente tipoIngrediente : tipoIngredientesLanche) {
			if (tipoIngrediente == tipoIngrediente.ALFACE) {
				alface = true;
			}
			if (tipoIngrediente == tipoIngrediente.BACON) {
				bacon = true;
			}
		}

		// Caso o pedido possui o TipoIngrediente alface e não tem bacon, aplica-se
		// o desconto de 10% sobre o valor
		if (alface && !bacon) {
			desconto = total.multiply(new BigDecimal(10)).divide(new BigDecimal(100));
		}
		return total.subtract(desconto);
	}
	
	
	
	private BigDecimal muitaCarneOuQueijo(List<TipoIngrediente> tipoIngredientes, BigDecimal total) {
		int carne = 0;
		int queijo = 0;

		for (TipoIngrediente tipoIngrediente : tipoIngredientes) {
			if (tipoIngrediente == tipoIngrediente.BURGER) {
				++carne;
			}
			if (tipoIngrediente == tipoIngrediente.QUEIJO) {
				++queijo;
			}
		}

		// A cada 3 TipoIngredientes de carne ou queijo um é grátis
		BigDecimal descCarne = new BigDecimal(carne / 3).setScale(1, RoundingMode.DOWN);
		BigDecimal descQueijo = new BigDecimal(queijo / 3).setScale(1, RoundingMode.DOWN);
		BigDecimal desconto = this.getValor(TipoIngrediente.BURGER).multiply(descCarne)
				.add(this.getValor(TipoIngrediente.QUEIJO).multiply(descQueijo));

		return total.subtract(desconto);
	}

	public BigDecimal getValor(TipoIngrediente tipo) {
		switch (tipo) {
		case ALFACE:
			return new BigDecimal(0.40);
		case BACON:
			return new BigDecimal(2.00);
		case BURGER:
			return new BigDecimal(3.00);
		case OVO:
			return new BigDecimal(0.80);
		case QUEIJO:
			return new BigDecimal(1.50);
		default:
			break;
		}
		return BigDecimal.ZERO;
	}
	
	private String formatValor(BigDecimal bd) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		return df.format(bd);
	}
}
