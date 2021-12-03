package projects.trabalhofinal.out;

import sinalgo.tools.Tools;

public class Out {
	
	public static void log(String msg) {
		Tools.appendToOutput(msg);
		System.out.println(msg);
	}
}
