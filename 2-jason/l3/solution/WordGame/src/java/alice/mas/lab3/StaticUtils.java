/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * A set of utilities, made to lighten the behaviour code.
 * 
 * @author Danilo Pianini
 * @version 20111017
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

	public static HashMap<String, ? extends LinkedList<String>> scanDict() throws IOException{
		final HashMap<String,LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
		String dict = StaticUtils.fileToString("english-words.70");
		StringTokenizer tkn = new StringTokenizer(dict, "\n");
		while(tkn.hasMoreElements()){
			String n = tkn.nextToken();
			n = n.toLowerCase();
			boolean proceed = true;
			for(char c: n.toCharArray()){
				if((c<65 || c>90) && (c<97 || c>122)){
					proceed = false;
					break;
				}
			}
			if(n.length()>0 && proceed){
				String first = n.substring(0, 1);
				LinkedList<String> words = map.get(first);
				if(words == null){
					words = new LinkedList<String>();
					map.put(first, words);
				}
				words.add(n.trim());
			}
		}
		return map;
	}
}
