package com.vendaslanches.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum Tipo {

	XBACON,
	
	XBURGER,
	
	XEGG,
	
	XEGGBACON;
	
	public static String descricao(Tipo tipo) {
		switch (tipo) {
		case XBACON:
			return "X-Bacon (Bacon, Hambúrguer de carne e queijo)";
		case XBURGER:
			return "X-Burger (Hambúrguer de carne e queijo)";
		case XEGG:
			return "X-Egg (Ovo, Hambúrguer de carne e queijo)";
		case XEGGBACON:
			return "X-EGG Bacon (Ovo, bancon, Hambúrguer de carne e queijo)";
		default:
			break;
		}
		return null;
	}
	
	public static List<TipoIngrediente> ingredientes(Tipo tipo) {
		switch (tipo) {
		case XBACON:
			return Arrays.asList(TipoIngrediente.BACON, TipoIngrediente.BURGER, TipoIngrediente.QUEIJO);
		case XBURGER:
			return Arrays.asList(TipoIngrediente.BURGER, TipoIngrediente.QUEIJO);
		case XEGG:
			return Arrays.asList(TipoIngrediente.OVO, TipoIngrediente.BURGER, TipoIngrediente.QUEIJO);
		case XEGGBACON:
			return Arrays.asList(TipoIngrediente.OVO, TipoIngrediente.BACON, TipoIngrediente.BURGER, TipoIngrediente.QUEIJO);
		default:
			break;
		}
		return new ArrayList<TipoIngrediente>();
	}

}
