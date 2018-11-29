package main;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException, FileNotFoundException {
		// Select file name
		String filepath = FileHandler.openFile();
		if (filepath == null) {
			System.out.println("File not found");
			return;
		}
		// Select problem extansion
		String[] extension = new String[1];
		extension[0] = "Normal";
		Boolean[] collision = new Boolean[1];
		collision[0] = false;
		new ExtensionChooserFrame(extension, collision, "Select extension");

		// Create graph
		Set<RVertex> realSolution = new HashSet<>();
		realSolution.add(new RVertex(new Point2D.Double(4.3, 2.2),RVertexType.GOOD_GUY));
		realSolution.add(new RVertex(new Point2D.Double(4.3, 0.8),RVertexType.GOAL_GUY));
		RGraph G = new RGraph(filepath, collision[0]);
		FileHandler.saveToDot(G, realSolution, "model_goalKeeper");

		// Run algo
		new AlgoRunner(G, extension[0], collision[0]);
		

	}
}
