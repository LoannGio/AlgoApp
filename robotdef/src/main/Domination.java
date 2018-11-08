package main;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class Domination {
	// V�rifie si un ensemble de sommets D domine le graphe G
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

	/* V�rifie si un ensemble de sommets dominated domine un */
	public static <V, E> boolean dominates(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating) {
		for (V v : dominated) {
			if (dominating.contains(v))
				continue;
			boolean thereIsANeighborInDominating = false;
			for (V u : Graphs.neighborSetOf(G, v)) {
				if (dominating.contains(u)) {
					thereIsANeighborInDominating = true;
					break;
				}
			}
			if (thereIsANeighborInDominating)
				continue;
			return false;
		}

		return true;
	}

	// Retourne un plus petit ensemble dominant d'un graphe (null si gamma(G) >
	// 6)
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G) {
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
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated) {
		for (int i = 0; i <= 6; i++) {
			for (Set<V> D : SubsetCreator.allSubsetsOfSizeN(G.vertexSet(), i))
				if (dominates(G, D, dominated)) {
					return D;
				}
		}
		return null;
	}
	
	/*Retourne un plus petit ensemble qui domine un sous-ensemble de sommets dominated d'un graphe 
	(null si son cardinal est > 6) par un ensemble de sommets dans dominating*/
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating) {
		for (int i = 0; i <= 6; i++) {
			for(Set<V> D : SubsetCreator.allSubsetsOfSizeN(dominating, i))
				if(dominates(G, D, dominated)) {
					return D;
				}
		}
		return null;
	}
	
	/* Retourne un ensemble dominant du graphe G qui domine un sous-ensemble 
	 * de sommets dominated d'un graphe par un ensemble de sommets dans 
	 * dominating (null si la m�thode gloutonne ne trouve pas de sous-ensemble
	 * */

	public static <V, E> Set<V> dominatingSetGluttonous(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating) {
		Set<V> dominatedCopy = new HashSet<V>(dominated);
		Set<V> dominatingCopy = new HashSet<V>(dominating);
		SimpleGraph<V, E> GCopy = (SimpleGraph<V, E>) G.clone();
		Set<V> D = new HashSet<V>();
		for (int i = 1; i <= 6; i++) {
			V v = vertexOfHighestDegree(GCopy, dominatingCopy);
			D.add(v);
			Set<V> neighbors = Graphs.neighborSetOf(GCopy, v);
			GCopy.removeAllVertices(neighbors);
			dominatedCopy.removeAll(neighbors);
			dominatingCopy.removeAll(neighbors);
			GCopy.removeVertex(v);
			dominatedCopy.remove(v);
			dominatingCopy.remove(v);
			
			if(dominates(G, dominatedCopy, D))
				return D;			
		}
		
		return null;
	}
	
	/* Retourne un ensemble dominant du graphe G qui domine un sous-ensemble 
	 * de sommets dominated d'un graphe (null si la m�thode gloutonne ne trouve*/
	public static <V, E> Set<V> dominatingSetGluttonous(SimpleGraph<V, E> G, Set<V> dominated) {
		return dominatingSetGluttonous(G, dominated, G.vertexSet());
	}
	
	/*Retourne un ensemble dominant du graphe G
	 * (null si la m�thode gloutonne ne trouve pas de 6 */
	public static <V, E> Set<V> dominatingSetGluttonous(SimpleGraph<V, E> G) {
		return dominatingSetGluttonous(G, G.vertexSet());
	}
	
	private static <V, E> V vertexOfHighestDegree(SimpleGraph<V, E> G, Set<V> vertices) {
		int max = -1;
		V res = null;
		for (V v : vertices) {
			int d = G.degreeOf(v);
			if (d > max) {
				max = d;
				res = v;
			}
		}
		
		return res;
	}
}
