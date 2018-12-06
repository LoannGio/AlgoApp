package main;

import java.io.FileNotFoundException;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException, FileNotFoundException {
		// Select file name
		String filepath = FileHandler.openFile();
		if (filepath == null) {
			System.out.println("File not found");
			return;
		}
		// Select problem extension
		String[] extension = new String[1];
		extension[0] = "Normal";
		Boolean[] collision = new Boolean[1];
		collision[0] = false;
		new ExtensionChooserFrame(extension, collision, "Select extension");

		// Create graph
		RGraph G = new RGraph(filepath, collision[0]);

		// Run algo
		new AlgoRunner(G, extension[0], collision[0]);
	}
}
