package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.Artist;

public class CoppiaArtisti implements Comparable<CoppiaArtisti>{
	
	private Artist art1;
	private Artist art2;
	private Integer peso;
	
	public CoppiaArtisti(Artist art1, Artist art2, Integer peso) {
		this.art1 = art1;
		this.art2 = art2;
		this.peso = peso;
	}

	public Artist getArt1() {
		return art1;
	}

	public void setArt1(Artist art1) {
		this.art1 = art1;
	}

	public Artist getArt2() {
		return art2;
	}

	public void setArt2(Artist art2) {
		this.art2 = art2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(CoppiaArtisti o) {
		return -(this.peso-o.getPeso());
	}

	@Override
	public String toString() {
		return art1 + " - " + art2 + ", " + peso;
	}
}
