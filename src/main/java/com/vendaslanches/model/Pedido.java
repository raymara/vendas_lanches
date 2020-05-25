package com.vendaslanches.model;

import java.util.List;



public class Pedido {

	private Tipo lanche;
	private List<Adicional> adicionais;
	private String total;
	

	public Tipo getLanche() {
		return lanche;
	}

	public void setLanche(Tipo lanche) {
		this.lanche = lanche;
	}

	public List<Adicional> getAdicionais() {
		return adicionais;
	}

	public void setAdicionais(List<Adicional> adicionais) {
		this.adicionais = adicionais;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adicionais == null) ? 0 : adicionais.hashCode());
		result = prime * result + ((lanche == null) ? 0 : lanche.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Pedido other = (Pedido) obj;
		if (adicionais == null) {
			if (other.adicionais != null)
				return false;
		} else if (!adicionais.equals(other.adicionais))
			return false;
		if (lanche != other.lanche)
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
}
