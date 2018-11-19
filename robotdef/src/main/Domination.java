package main;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class Domination {
	// Vï¿½rifie si un ensemble de sommets D domine le graphe G
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

	/* Vï¿½rifie si un ensemble de sommets dominated domine un */
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

	// Retourne un plus petit ensemble dominant (et indépendant si collisions = true) 
	// d'un graphe (null si gamma(G) > 6)
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, boolean collisions) {
		return smallestDominatingSetBruteForce(G, G.vertexSet(), G.vertexSet(), collisions);
	}

	// Retourne un plus petit ensemble qui domine un sous-ensemble de sommets d
	// d'un graphe (null si son cardinal est > 6) (si collisions = true : cet ensemble sera indépendant)
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated, boolean collisions) {
		return smallestDominatingSetBruteForce(G, dominated, G.vertexSet(), collisions);
	}
	
	/*Retourne un plus petit ensemble qui domine un sous-ensemble de sommets dominated d'un graphe 
	(null si son cardinal est > 6) par un ensemble de sommets dans dominating 
	(si collisions = true : cet ensemble sera indépendant)*/
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating, boolean collisions) {
		for (int i = 0; i <= 6; i++) {
			for(Set<V> D : SubsetCreator.allSubsetsOfSizeN(dominating, i)) {
				if(collisions) {
					if(!Independance.isIndependent(G, dominating) && dominates(G, dominated, D))
						continue;
				}
				else {
					if (dominates(G, dominated, D))
						return D;
				}
			}
		}
		return null;
	}
	
	/* Retourne un ensemble dominant du graphe G qui domine un sous-ensemble 
	 * de sommets dominated d'un graphe par un ensemble de sommets dans 
	 * dominating (null si la mï¿½thode gloutonne ne trouve pas de sous-ensemble
	 *  de taille <= 6 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating) {
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
	 * de sommets dominated d'un graphe (null si la mï¿½thode gloutonne ne trouve
	 *  pas de sous-ensemble de taille <= 6 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G, Set<V> dominated) {
		return dominatingSetGreedy(G, dominated, G.vertexSet());
	}
	
	/*Retourne un ensemble dominant du graphe G
	 * (null si la mï¿½thode gloutonne ne trouve pas de 
	 * sous-ensemble de taille <= 6 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G) {
		return dominatingSetGreedy(G, G.vertexSet());
	}
	
	public static <V, E> Set<V> dominatingSetGreedyGoal(SimpleGraph<V, E> G, Set<V> dominatingGoal, Set<V> dominating, Set<V> dominated) {
		Set<V> dominatedCopy = new HashSet<V>(dominated);
		Set<V> dominatingCopy = new HashSet<V>(dominating);
		//dominatingCopy contient tous les sommets de potentiellement dominant
		SimpleGraph<V, E> GCopy = (SimpleGraph<V, E>) G.clone();
		Set<V> D = new HashSet<V>();
		
		/*On commence par ajouter le sommet sur la surface de réparation de plus grand degré
		 * et on supprime tous ses voisins puis on fait comme dans la version normale de dominatingSetGreedyGoal
		 */
		//Choix du Goal
		V v = vertexOfHighestDegree(GCopy, dominatingGoal);
		D.add(v);
		Set<V> neighbors = Graphs.neighborSetOf(GCopy, v);
		neighbors = Graphs.neighborSetOf(GCopy, v);
		GCopy.removeAllVertices(neighbors);
		dominatedCopy.removeAll(neighbors);
		dominatingCopy.removeAll(neighbors);
		GCopy.removeVertex(v);
		dominatedCopy.remove(v);
		dominatingCopy.remove(v);			
		if(dominates(G, dominatedCopy, D))
			return D;
		//Choix des autres défenseurs
		for (int i = 1; i <= 6; i++) {
			v = vertexOfHighestDegree(GCopy, dominatingCopy);
			D.add(v);
			neighbors = Graphs.neighborSetOf(GCopy, v);
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
