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
		
		SATReduction.reduction(G, "reduction.txt");

		System.out.println("---Test de smallestDominatingSetBruteForcePosInit");
		long time = System.currentTimeMillis();
		/*
		 * Set<RVertex> solution =
		 * Domination.smallestDominatingSetBruteForcePosInit(G,
		 * G.getShotLineVertices(), G.getPositionVertices(),
		 * G.getInitPosDefenders(), false);
		 */

		Set<RVertex> solution = Domination.dominatingSetGreedyPosInit(G, G.getShotLineVertices(),
				G.getPositionVertices(), G.getInitPosDefenders());

		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution);
	}
}