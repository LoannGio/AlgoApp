package main;

import java.io.FileNotFoundException;
import java.util.Set;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException, FileNotFoundException {

		String filepath = FileHandler.openFile();
		if (filepath == null) {
			System.out.println("File not found");
			return;
		}
		RGraph G = new RGraph(filepath, false);

		// Test de smallestDominatingSet()
		System.out.println("---Test de smallestDominatingSet()");
		long time = System.currentTimeMillis();
		Set<RVertex> solution = (Set<RVertex>) Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(),
				G.getPositionVertices(), false);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution);
		System.out.println(solution);
		System.out.println(G.getPositionVertices());
		new RGraphVisualizer(G);
	}
}