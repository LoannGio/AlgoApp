package main;

import java.util.HashSet;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException {
		/*
		 * String filepath = ""; JFileChooser chooser = new JFileChooser();
		 * FileNameExtensionFilter filter = new
		 * FileNameExtensionFilter("JSON files (*.json)", "json");
		 * 
		 * chooser.setFileFilter(filter);
		 * 
		 * int retValue = chooser.showOpenDialog(null); if (retValue ==
		 * JFileChooser.APPROVE_OPTION) { filepath =
		 * chooser.getSelectedFile().getPath(); } else {
		 * System.out.println("File not found"); }
		 * 
		 * RGraph G = new RGraph(filepath, false); new RGraphVisualizer(G);
		 * 
		 * // Test de smallestDominatingSet()
		 * System.out.println("---Test de smallestDominatingSet()");
		 * System.out.println(Domination.smallestDominatingSet(G,
		 * G.getShotLineVertices(), G.getPositionVertices()));
		 * System.out.println(G.getPositionVertices());
		 */
		HashSet D3 = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			D3.add(i);
		}
		String result = SubsetCreator.allSubsetsOfSizeN(D3, 3).toString();
		System.out.println("----------------");
	}
}
