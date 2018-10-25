package main;

import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class Domination {
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

	// Vérifie si un ensemble de sommets D domine un sous ensemble de sommets de
	// G d
	public static <V, E> boolean dominates(SimpleGraph<V, E> G, Set<V> D, Set<V> d) {
		for (V v : d) {
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

	// Retourne un plus petit ensemble dominant d'un graphe (null si gamma(G) >
	// 6)
	public static <V, E> Set<V> smallestDominatingSet(SimpleGraph<V, E> G) {
		for (int i = 0; i <= 6; i++) {
			for (Set<V> D : SubsetCreator.allSubsetsOfSizeN(G.vertexSet(), i))
				if (dominates(G, D)) {
					return D;
				}
		}
		return null;
	}

	// Retourne un plus petit ensemble qui domine un sous-ensemble de sommets d
	// d'un graphe (null si son cardinal est > 6)
	public static <V, E> Set<V> smallestDominatingSet(SimpleGraph<V, E> G, Set<V> d) {
		for (int i = 0; i <= 6; i++) {
			for (Set<V> D : SubsetCreator.allSubsetsOfSizeN(G.vertexSet(), i))
				if (dominates(G, D, d)) {
					return D;
				}
		}
		return null;
	}
}
