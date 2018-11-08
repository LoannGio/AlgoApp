package main;

import java.util.Set;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException {

		String filepath = FileHandler.openFile();
		RGraph G = new RGraph(filepath, false);

		// Test de smallestDominatingSet()
		System.out.println("---Test de smallestDominatingSet()");
		Set<RVertex> solution = (Set<RVertex>) Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(),
				G.getPositionVertices(), false);
		JSonSolution.saveJSonSolution(solution);
		System.out.println(solution);
		System.out.println(G.getPositionVertices());
		new RGraphVisualizer(G);
	}
}
