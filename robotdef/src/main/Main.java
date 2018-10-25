package main;

import org.jgrapht.graph.SimpleGraph;
import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException {
		SimpleGraph G = new RGraph("lib_json/test.json", false);
	}
}
