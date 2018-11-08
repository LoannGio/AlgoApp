package main;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {

	public static String openFile() {
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
			filepath = "File not found"; // ou throw new
											// FileNotFoundException(), mais de
											// toute facon
			// si il n y a
			// pas de fichier on execute pas...
		}
		return filepath;
	}

}
