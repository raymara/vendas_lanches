package com.vendaslanches.model;


public class Adicional {
	private TipoIngrediente ingrediente;
	private Integer qtde;

	public TipoIngrediente getTipoIngrediente() {
		return ingrediente;
	}

	public void setTipoIngrediente(TipoIngrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingrediente == null) ? 0 : ingrediente.hashCode());
		result = prime * result + ((qtde == null) ? 0 : qtde.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adicional other = (Adicional) obj;
		if (ingrediente != other.ingrediente)
			return false;
		if (qtde == null) {
			if (other.qtde != null)
				return false;
		} else if (!qtde.equals(other.qtde))
			return false;
		return true;
	}
}
