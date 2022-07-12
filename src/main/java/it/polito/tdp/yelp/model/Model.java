package it.polito.tdp.yelp.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	YelpDao dao;
	Graph<Business, DefaultWeightedEdge> grafo;
	List<Business> business;
	Map<String, Business> mBusiness;
	List<Adiacenza> adiacenze;
	
	public Model() {
		this.dao = new YelpDao();
	}
	
	public void creaGrafo(String citta) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.business = this.dao.getBusinessCity(citta);
		this.mBusiness = new TreeMap<>();
		for(Business b : business) {
			this.mBusiness.put(b.getBusinessId(), b);
		}
		
		Graphs.addAllVertices(this.grafo, this.business);
		
		this.adiacenze = this.dao.getAdiacenze(citta, mBusiness);
		
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.grafo, a.getB1(), a.getB2(), a.getDistanzaKM());
		}
	}
	
	
	
	public Graph<Business, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<String> getCities(){
		return this.dao.getCities();
	}
	
	
}
