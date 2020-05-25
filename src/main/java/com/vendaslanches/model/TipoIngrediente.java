package com.vendaslanches.model;



public enum TipoIngrediente {

ALFACE,
	
	BACON,
	
	BURGER,
	
	OVO,
	
	QUEIJO;
	
	public static String nome(TipoIngrediente tipo) {
		switch (tipo) {
		case ALFACE:
			return "Alface";
		case BACON:
			return "Bacon";
		case BURGER:
			return "Hambúrguer de carne";
		case OVO:
			return "Ovo";
		case QUEIJO:
			return "Queijo";
		default:
			break;
		}
		return null;
	}
}
