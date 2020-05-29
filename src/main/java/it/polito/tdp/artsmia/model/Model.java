package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;
import it.polito.tdp.artsmia.db.CoppiaArtisti;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Artist, DefaultWeightedEdge> graph;
	private Map<Integer, Artist> vertices;
	private List<CoppiaArtisti> edges;

	private List<Artist> best;

	public Model() {
		this.dao = new ArtsmiaDAO();
	}
	
	public void generateGraph(String role) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertices = this.dao.listArtists(role);
		this.edges = this.dao.listCoppieArtisti(role, this.vertices);
		
		Graphs.addAllVertices(this.graph, vertices.values());
		
		for(CoppiaArtisti c : edges) {
			Graphs.addEdge(this.graph, c.getArt1(), c.getArt2(), c.getPeso());
		}
		
	}
	
	public List<String> listRoles() {
		return this.dao.listRoles();
	}
	
	public List<CoppiaArtisti> getElencoCoppie() {
		List<CoppiaArtisti> coppie = this.edges;
		Collections.sort(coppie);
		return coppie;
	}
	
	public Integer getNumVertici() {
		return this.vertices.size();
	}
	
	public Integer getNumArchi() {
		return this.edges.size();
	}
	
	public List<Artist> getCamminoMax(Integer id) {
		List<Artist> parziale = new ArrayList<>();
		Artist a = this.vertices.get(id);
		this.best = new ArrayList<>();
		
		parziale.add(a);
		
		ricorsiva(a, parziale, 0, 0);
		
		
		return this.best;
	}

	private void ricorsiva(Artist a, List<Artist> parziale, int L, double numEsposizioni) {
		Artist b = parziale.get(parziale.size()-1);
		
		if(parziale.size()>this.best.size()) {
			this.best = new ArrayList<>(parziale);
		}
			
		
		
		if(L==0) {
			for(Artist prossimo : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
				if(!parziale.contains(prossimo)) {
					parziale.add(prossimo);
					this.ricorsiva(a, parziale, L+1, this.graph.getEdgeWeight(this.graph.getEdge(a, prossimo)));
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
		for(Artist prossimo : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(prossimo) && this.graph.getEdgeWeight(this.graph.getEdge(b, prossimo)) == numEsposizioni) {
				parziale.add(prossimo);
				this.ricorsiva(a, parziale, L+1, this.graph.getEdgeWeight(this.graph.getEdge(b, prossimo)));
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public double getPesoArco(Artist a1, Artist a2) {
		return this.graph.getEdgeWeight(this.graph.getEdge(a1, a2));
	}

}
