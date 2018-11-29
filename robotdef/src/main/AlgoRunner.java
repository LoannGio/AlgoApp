package main;

import java.util.Set;

public class AlgoRunner {

	public AlgoRunner(RGraph G, String extension, Boolean collision) {
		switch (extension) {
		case "Normal":
			runNormal(G, collision);
			break;
		case "GoalKeeper":
			runGoalKeeper(G, collision);
			break;
		case "MultiGoal":
			runMultiGoal(G, collision);
			break;
		case "PosInit":
			runPosInit(G, collision);
			break;
		case "All":
			runAll(G, collision);
		}
	}

	private void runAll(RGraph G, Boolean collision) {
		runNormal(G, collision);
		runGoalKeeper(G, collision);
		runMultiGoal(G, collision);
		runPosInit(G, collision);
	}

	private void runPosInit(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : Position Initiale - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedyPosInit(G, G.getShotLineVertices(), G.getPositionVertices(),
				G.getInitPosDefenders(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyPosInit.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForcePosInit(G, G.getShotLineVertices(),
				G.getPositionVertices(), G.getInitPosDefenders(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForcePosInit.json");

	}

	private void runMultiGoal(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : MultiGoal - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedy(G, G.getShotLineVertices(), G.getPositionVertices(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyMultiGoal.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(), G.getPositionVertices(),
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceMultiGoal.json");
	}

	private void runGoalKeeper(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		Set<RVertex> defenders = G.getPositionVertices();
		defenders.removeAll(G.getGoalPosition());
		System.out.println("#MODE : GoalKeeper - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedyGoal(G, G.getShotLineVertices(), G.getGoalPosition(), defenders,
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyGoalKeeper.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForceGoal(G, G.getShotLineVertices(), G.getGoalPosition(),
				defenders, collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceGoalKeeper.json");

	}

	private void runNormal(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : Normal - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedy(G, G.getShotLineVertices(), G.getPositionVertices(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyNormal.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(), G.getPositionVertices(),
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceNormal.json");
	}

}
