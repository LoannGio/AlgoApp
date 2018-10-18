package main;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Domination {
	public static void main(String[] args) {
		// Test de dominates() avec un C5 et deux ensembles (un dominant et un
		// non-dominant)
		SimpleGraph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 5; i++) {
			g.addVertex(i);
		}
		for (int i = 0; i < 5; i++) {
			g.addEdge(i, (i + 1) % 5);
		}

		// Ensemble dominant de C5
		HashSet D1 = new HashSet<Integer>();
		D1.add(0);
		D1.add(3);

		// Ensemble non dominant de C5
		HashSet D2 = new HashSet<Integer>();
		D2.add(0);
		D2.add(1);
		System.out.println("---Test de dominates()---");
		System.out.println("Test sur C5 avec D = {0, 3}");
		boolean b = dominates(g, D1);
		System.out.println(b);

		System.out.println("Test sur C5 avec D = {0, 1}");
		b = dominates(g, D2);
		System.out.println(b);

		//Test de allSubsetsOfSizeN()
		HashSet D3 = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			D3.add(i);
		}

		System.out.println(SubsetCreator.allSubsetsOfSizeN(D3, 2));
	}

	// Vérifie si un ensemble de sommets D domine le graphe G
	public static <V, E> boolean dominates(SimpleGraph<V, E> G, Set<V> D) {
		for (V v : G.vertexSet()) {
			if (D.contains(v))
				continue;
			boolean thereIsANeighborInD = false;
			for (V u : Graphs.neighborSetOf(G, v)) {
				if (D.contains(u)) {
					thereIsANeighborInD = true;
					break;
				}
			}
			if (thereIsANeighborInD)
				continue;
			return false;
		}

		return true;
	}
	
	//Vérifie si un ensemble de sommets D domine un sous ensemble de sommets de G d
		public static <V, E> boolean dominates(SimpleGraph<V, E> G, Set<V> D, Set<V> d){		
			for(V v : d) {
				if (D.contains(v))
					continue;
				boolean thereIsANeighborInD = false;
				for(V u : Graphs.neighborSetOf(G, v)) {
					if (D.contains(u)) {
						thereIsANeighborInD = true;
						break;
					}
				}
				if(thereIsANeighborInD)
					continue;
				return false;
			}
			
			return true;		
		}
}
