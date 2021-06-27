package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Player, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Player> IdMap ;
	
	public String creaGrafo(Match g) {
		
		dao = new PremierLeagueDAO();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.IdMap = new HashMap<>() ;
		
		Graphs.addAllVertices(this.grafo, this.dao.listAllPlayers(g));
		for(Player p : this.dao.listAllPlayers(g)) {
			this.IdMap.put(p.getPlayerID(), p);
		}
		
		Map<Integer, Double> eff = this.dao.listEff(g);
		
		for(Player p : this.dao.listAllPlayers(g)) {
			for(Player p2 : this.dao.listAllPlayers(g)) {
				if(p.getTeamID() != p2.getTeamID()) {
					if(!this.grafo.containsEdge(p2, p) && !this.grafo.containsEdge(p, p2)) {
						if((this.dao.listEff(g).get(p.getPlayerID()) - this.dao.listEff(g).get(p2.getPlayerID())) > 0) {
							Graphs.addEdge(this.grafo, p, p2, Math.abs((this.dao.listEff(g).get(p.getPlayerID()) - this.dao.listEff(g).get(p2.getPlayerID()))));
						}else {
							Graphs.addEdge(this.grafo, p2, p, Math.abs((this.dao.listEff(g).get(p.getPlayerID()) - this.dao.listEff(g).get(p2.getPlayerID()))));
						}
					}
				}
			}
		}
		
		return String.format("Grafo creato!\n#vertici: %d\n#archi: %d\n\n" , this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		
	}

	public Migliore getBest(Match g){
		List<Player> vertici = this.dao.listAllPlayers(g);
		double max = 0.0;
		Player best = null;
		for(Player p : vertici) {
			double out = 0;
			double in = 0;
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
				out += (double)this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p)) {
				in += (double)this.grafo.getEdgeWeight(e);
			}
			double delta = out - in;
			if((delta) > max) {
				max = delta;
				best = p;
			}
		}
		
		return new Migliore(best, max);
	}
	
	
	public List<Match> getAllMatch(){
		dao = new PremierLeagueDAO();
		return dao.listAllMatches();
	}
	
}
