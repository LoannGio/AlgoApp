package main;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	
	// Retourne un plus petit ensemble dominant (et ind�pendant si collisions
	// =
	// true)
	// d'un graphe (null si gamma(G) > 6)
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, boolean collisions) {
		return smallestDominatingSetBruteForce(G, G.vertexSet(), G.vertexSet(), collisions);
	}

	// Retourne un plus petit ensemble qui domine un sous-ensemble de sommets d
	// d'un graphe (null si son cardinal est > 6) (si collisions = true : cet
	// ensemble sera ind�pendant)
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated,
			boolean collisions) {
		return smallestDominatingSetBruteForce(G, dominated, G.vertexSet(), collisions);
	}

	/*
	 * Retourne un plus petit ensemble qui domine un sous-ensemble de sommets
	 * dominated d'un graphe (null si son cardinal est > 6) par un ensemble de
	 * sommets dans dominating (si collisions = true : cet ensemble sera
	 * ind�pendant)
	 */
	public static <V, E> Set<V> smallestDominatingSetBruteForce(SimpleGraph<V, E> G, Set<V> dominated,
			Set<V> dominating, boolean collisions) {
		for (int i = 0; i <= 6; i++) {
			for (Set<V> D : SubsetCreator.allSubsetsOfSizeN(dominating, i)) {
				if (collisions) {
					if (!Independance.isIndependent(G, D))
						continue;
				}
				if (dominates(G, dominated, D))
					return D;
			}
		}
		return null;
	}

	public static <V, E> Set<V> smallestDominatingSetBruteForceGoal(SimpleGraph<V, E> G, Set<V> dominated,
			Set<V> dominatingGoal, Set<V> dominatingDefenser, boolean collisions) {

		 for (int i = 0; i <= 6; i++) {
		for (Set<V> D : SubsetCreator.allSubsetsOfSizeNGoal(dominatingGoal, dominatingDefenser, i)) {
			if (collisions) {
				if (!Independance.isIndependent(G, D))
					continue;
			}
			System.out.println(D);
			if (dominates(G, dominated, D))
				return D;
		}
		 }
		return null;
	}

	/*
	 * Retourne un plus petit ensemble qui domine un sous-ensemble de sommets
	 * dominated d'un graphe (null si son cardinal est > 6) par un ensemble de
	 * sommets dans dominating (si collisions = true : cet ensemble sera
	 * ind�pendant)
	 */
	public static <V, E> Set<V> smallestDominatingSetBruteForcePosInit(SimpleGraph<V, E> G, Set<V> dominated,
			Set<V> dominating, ArrayList<Point2D> initPos, boolean collisions) {
		double distMaxMin = Integer.MAX_VALUE;
		ArrayList<V> bestPermutation = new ArrayList<V>();
		for (int i = 0; i <= initPos.size(); i++) {
			HashSet<HashSet<V>> subsets = SubsetCreator.allSubsetsOfSizeN(dominating, i);
			for (Set<V> D : subsets) {
				if (collisions && !Independance.isIndependent(G, D)) {
					continue;
				}
				if (dominates(G, dominated, D)) {
					ArrayList<ArrayList<V>> permutations = SubsetCreator.permutations(D);
					for (ArrayList<V> permutation : permutations) {
						double dist = SmallestLongestDistance(permutation, initPos);
						if (distMaxMin > dist) {
							bestPermutation = permutation;
							distMaxMin = dist;
						}
					}
				}
			}
		}
		return new HashSet<V>(bestPermutation);
	}

	private static <V, E> double SmallestLongestDistance(ArrayList<V> permutation, ArrayList<Point2D> initPos) {
		double distMaxMin = Integer.MAX_VALUE;
		for (int i = 0; i < permutation.size() - 1; i++) {
			RVertex vertex = (RVertex) permutation.get(i);
			double dist = Math.sqrt(Math.pow(vertex.get_position().getY() - initPos.get(i).getY(), 2)
					+ Math.pow(vertex.get_position().getX() - initPos.get(i).getX(), 2));
			if (distMaxMin > dist)
				distMaxMin = dist;
		}
		return distMaxMin;
	}

	/*
	 * Retourne un ensemble dominant du graphe G qui domine un sous-ensemble de
	 * sommets dominated d'un graphe par un ensemble de sommets dans dominating
	 * (null si la m�thode gloutonne ne trouve pas de sous-ensemble de taille
	 * <= 6
	 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating,
			boolean collision) {
		Set<V> dominatedCopy = new HashSet<V>(dominated);
		Set<V> dominatingCopy = new HashSet<V>(dominating);
		SimpleGraph<V, E> GCopy = (SimpleGraph<V, E>) G.clone();
		Set<V> D = new HashSet<V>();
		for (int i = 0; i <= 6; i++) {
			V v = vertexOfHighestDegree(GCopy, dominatingCopy);
			D.add(v);
			Set<V> neighbors = Graphs.neighborSetOf(GCopy, v);
			GCopy.removeAllVertices(neighbors);
			dominatedCopy.removeAll(neighbors);
			dominatingCopy.removeAll(neighbors);
			GCopy.removeVertex(v);
			dominatedCopy.remove(v);
			dominatingCopy.remove(v);

			if (dominates(G, dominatedCopy, D))
				return D;
		}

		return null;
	}

	public static <V, E> Set<V> dominatingSetGreedyPosInit(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominating,
			ArrayList<Point2D> initPos, boolean collision) {
		Set<V> dominatedCopy = new HashSet<V>(dominated);
		Set<V> dominatingCopy = new HashSet<V>(dominating);
		SimpleGraph<V, E> GCopy = (SimpleGraph<V, E>) G.clone();
		Set<V> D = new HashSet<V>();
		double distMaxMin = Integer.MAX_VALUE;
		ArrayList<V> bestPermutation = new ArrayList<V>();
		for (int i = 1; i <= initPos.size(); i++) {
			V v = vertexOfHighestDegree(GCopy, dominatingCopy);
			D.add(v);
			Set<V> neighbors = Graphs.neighborSetOf(GCopy, v);
			GCopy.removeAllVertices(neighbors);
			dominatedCopy.removeAll(neighbors);
			dominatingCopy.removeAll(neighbors);
			GCopy.removeVertex(v);
			dominatedCopy.remove(v);
			dominatingCopy.remove(v);

			if (dominates(G, dominatedCopy, D)) {
				ArrayList<ArrayList<V>> permutations = SubsetCreator.permutations(D);
				for (ArrayList<V> permutation : permutations) {
					double dist = SmallestLongestDistance(permutation, initPos);
					if (distMaxMin > dist) {
						bestPermutation = permutation;
						distMaxMin = dist;
					}
				}
			}
		}

		return new HashSet<V>(bestPermutation);
	}

	/*
	 * Retourne un ensemble dominant du graphe G qui domine un sous-ensemble de
	 * sommets dominated d'un graphe (null si la m�thode gloutonne ne trouve
	 * pas de sous-ensemble de taille <= 6
	 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G, Set<V> dominated) {
		return dominatingSetGreedy(G, dominated, G.vertexSet(), true);
	}

	/*
	 * Retourne un ensemble dominant du graphe G (null si la m�thode gloutonne
	 * ne trouve pas de sous-ensemble de taille <= 6
	 */
	public static <V, E> Set<V> dominatingSetGreedy(SimpleGraph<V, E> G) {
		return dominatingSetGreedy(G, G.vertexSet());
	}

	public static <V, E> Set<V> dominatingSetGreedyGoal(SimpleGraph<V, E> G, Set<V> dominated, Set<V> dominatingGoal,
			Set<V> dominating, boolean collision) {
		Set<V> dominatedCopy = new HashSet<V>(dominated);
		Set<V> dominatingCopy = new HashSet<V>(dominating);
		// dominatingCopy contient tous les sommets potentiellement dominant
		SimpleGraph<V, E> GCopy = (SimpleGraph<V, E>) G.clone();
		Set<V> D = new HashSet<V>();

		/*
		 * On commence par ajouter le sommet sur la surface de r�paration de
		 * plus grand degr� et on supprime tous ses voisins puis on fait comme
		 * dans la version normale de dominatingSetGreedyGoal
		 */
		// Choix du Goal
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
		if (dominates(G, dominatedCopy, D))
			return D;
		// Choix des autres d�fenseurs
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
			if (dominates(G, dominatedCopy, D))
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
