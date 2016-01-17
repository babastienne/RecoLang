package langModel;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;


/**
 * Class MiscUtil: class containing miscellaneous functions to deal with files and directories.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class MiscUtil {

	/**
	 * Method reading the lines of a file and saving them in a list.
	 * Alternative exists: faster but more memory consuming.
	 * @see http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	 * 
	 * @param filePath: the path of the file from which to read lines.
	 * @return the list of sentences in the file.
	 */
	public static List<String> readTextFileAsStringList(String filePath) {
		BufferedReader reader;
		List<String> lineList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader (filePath));

			String line = reader.readLine();
			while(line != null) {
				lineList.add(line);
				line = reader.readLine();
			} 
			reader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return lineList;
	}
	
	
	/**
	 * Method writing a text in a file (by overwriting it or concatenating it).
	 *
	 * @param text the text to write.
	 * @param filename the name of the file.
	 * @param append true if the text is to append at the end of the file, false otherwise.
	 */
	public static void writeFile (String text, String filename, boolean append) {
		PrintWriter out = null;

		int dirPathEnd = filename.lastIndexOf(File.separator);
		String dirPath = "";
		if (dirPathEnd != -1) {
			dirPath = filename.substring(0, dirPathEnd);
			createDir(dirPath); 
		}
		
		try {
			out = new PrintWriter(new FileOutputStream(new File(filename), append));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		out.print(text);
		out.close();
	}

	
	/**
	 * Method creating a directory.
	 * 
	 * @param dirPath the path of the directory to create.
	 */
	public static void createDir(String dirPath) {
		File theDir = new File(dirPath);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.err.println("Info: creating directory: " + dirPath);
			boolean result = theDir.mkdir();  

			if(!result) {    
				System.err.println("Error: problem when creating directory: " + dirPath);  
			}
		}
	}
}
