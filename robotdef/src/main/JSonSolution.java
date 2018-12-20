package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSonSolution {

	public static void saveJSonSolution(Collection<RVertex> solution, String fileName) {
		if(solution == null)
		{
			System.out.println("Pas de solution au probleme.");
			return;
		}

		JSONObject object = generateJSONSolution(solution);

		FileWriter fileWriter = null;

		fileName = "solutions/" + fileName;
		try {
			fileWriter = new FileWriter(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.write(object.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Solution saved as " + fileName);

	}

	public static JSONObject generateJSONSolution(Collection<RVertex> solution) {
		JSONObject object = new JSONObject();
		JSONArray listPosition = new JSONArray();
		for (RVertex v : solution) {

			JSONArray position = new JSONArray();
			try {
				position.put(v.get_position().getX());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				position.put(v.get_position().getY());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listPosition.put(position);
		}
		try {
			object.put("defenders", listPosition);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

}
