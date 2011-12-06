/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A set of utilities, made to lighten the rest of the code.
 * 
 * @author Danilo Pianini
 * @version 20111206
 *
 */
public class StaticUtils {
	
	public static String fileToString(File f) throws IOException {
		if(f.exists()){
			BufferedReader br = new BufferedReader(new FileReader(f));
			char[] res = new char[(int)f.length()];
			br.read(res);
			br.close();
			return new String(res);
		}
		return null;
	}

	public static String fileToString(String path) throws IOException {
		return fileToString(new File(path));
	}

}
