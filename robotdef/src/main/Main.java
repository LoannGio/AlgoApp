package main;

import java.io.File;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.DOTExporter;
import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException {
		String filepath = "";
		JFileChooser chooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir") + "/problems");
		chooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files (*.json)", "json");

		chooser.setFileFilter(filter);

		int retValue = chooser.showOpenDialog(null);
		if (retValue == JFileChooser.APPROVE_OPTION) {
			filepath = chooser.getSelectedFile().getPath();
		} else {
			System.out.println("File not found");
			return; // ou throw new FileNotFoundException(), mais de toute facon
					// si il n y a
					// pas de fichier on execute pas...
		}
		System.out.println("toto");

		RGraph G = new RGraph(filepath, false);

		// Test de smallestDominatingSet()
		System.out.println("---Test de smallestDominatingSet()");
		Set<RVertex> solution = (Set<RVertex>) Domination.dominatingSetGreedy(G, G.getShotLineVertices(),
				G.getPositionVertices());
		JSonSolution.saveJSonSolution(solution);
		System.out.println(solution);
		System.out.println(G.getPositionVertices());
		new RGraphVisualizer(G);
	}
}
