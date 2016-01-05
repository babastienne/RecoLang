package langReco.eval;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import langModel.MiscUtil;



/**
 * Class Performance: class to compute the performance of a recognition system.
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class Performance {

	/**
	 * Method using the sentences given in two files to compute the accuracy of a recognition system, i.e. the number
	 * of sentences whose language is correctly recognized by the system.
	 * 
	 * The first file contains the language annotation of each sentence of a reference file and the second
	 * file contains the hypothesis language of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param goldLangPath the path of the file containing the gold annotation of a sentences of the reference file.
	 * @param hypLangPath the path of the file containing the hypothesis sentences given by a recognition system. 
	 * @return the accuracy of the recognition system.
	 */
	public static double evaluate (String goldLangPath, String hypLangPath) {
		List<String> gold = MiscUtil.readTextFileAsStringList(goldLangPath);
		List<String> hyp = MiscUtil.readTextFileAsStringList(hypLangPath);
		
		return evaluate(gold,hyp);	
	}
	
	
	/**
	 * Method using the sentences given in two lists to compute the accuracy of a recognition system, i.e. the number
	 * of sentences whose language is correctly recognized by the system.
	 * 
	 * The first list contains the language annotation of each sentence of a reference list and the second
	 * listr contains the hypothesis language of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param goldLang the list containing the gold annotation of the sentences of a reference list.
	 * @param hypLang the list containing the hypothesis sentences given by a recognition system. 
	 * @return the accuracy of the recognition system.
	 */
	public static double evaluate (List<String> goldLang, List<String> hypLang) {
		if (goldLang.size() != hypLang.size()) {
			System.err.println("Error: gold and hyp lists must have the same number of sentences");
			return -1.0;
		}
		
		Double correct = 0.0;
		for (int i = 0 ; i < goldLang.size() ; i++ ) 
			if (goldLang.get(i).equalsIgnoreCase(hypLang.get(i))) 
				correct++;

		return (double) correct/ goldLang.size();	
	}
	
	
	
	/**
	 * Method using the sentences given in two files to compute the accuracy of a recognition system for
	 * each language, i.e. the number of sentences whose language is correctly recognized by the system.
	 * 
	 * The first file contains the language annotation of each sentence of a reference file and the second
	 * file contains the hypothesis language of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param goldLangPath the path of the file containing the gold annotation of a sentences of the reference file.
	 * @param hypLangPath the path of the file containing the hypothesis sentences given by a recognition system. 
	 * @return a String containing the accuracy of the recognition system for each language.
	 */
	public static String evaluateLanguages (String goldLangPath, String hypLangPath) {
		
		List<String> gold = MiscUtil.readTextFileAsStringList(goldLangPath);
		List<String> hyp = MiscUtil.readTextFileAsStringList(hypLangPath);
		
		return evaluateLanguages(gold,hyp);	
	}
	
	
	/**
	 * Method using the sentences given in two lists to compute the accuracy of a recognition system for
	 * each language, i.e. the number of sentences whose language is correctly recognized by the system.
	 * 
	 * The first list contains the language annotation of each sentence of a reference list and the second
	 * listr contains the hypothesis language of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param goldLang the list containing the gold annotation of the sentences of a reference list.
	 * @param hypLang the list containing the hypothesis sentences given by a recognition system. 
	 * @return a String containing the accuracy of the recognition system for each language.
	 */
	public static String evaluateLanguages (List<String> goldLang, List<String> hypLang) {
		if (goldLang.size() != hypLang.size()) {
			System.err.println("Error: gold and hyp files must have the same number of lines");
			return "ERROR : goldLang has " + goldLang.size() + " lines and hypLang has " + hypLang.size() + " lines";
		}		
		
		//computes the number of correct sentences of each language
		int nbCorrect = 0;
		Map<String, Integer> nbSentLang = new HashMap<String, Integer>();
		Map<String, Integer> nbCorrectLang = new HashMap<String, Integer>();
		String lang;
		
		for(int i=0; i<goldLang.size(); i++){
			lang = goldLang.get(i);
			//increments the number of sentences of the current gold language
			if(nbSentLang.containsKey(lang)){
				nbSentLang.put(lang, nbSentLang.get(lang)+1);
			}
			else{
				nbSentLang.put(lang, 1);
			}
			//increments the number of correct sentences of the current gold language if equal to the current hyp language
			if(hypLang.get(i).equalsIgnoreCase(lang)){
				nbCorrect ++;
				if(nbCorrectLang.containsKey(lang)){
					nbCorrectLang.put(lang, nbCorrectLang.get(lang)+1);
				}
				else{
					nbCorrectLang.put(lang, 1);
				}
			}
		}
		
		//sets up the result string with the results
		String str_res = "";
		double score;
		
		for(String l : nbSentLang.keySet()){
			if(nbCorrectLang.containsKey(l))
				score = (double) nbCorrectLang.get(l) / nbSentLang.get(l);
			else
				score = 0;
			str_res += l + " : " + score + " (" + nbSentLang.get(l) + ")\n";
		}
		score = (double) nbCorrect / goldLang.size();
		str_res += "-> total : " + score + "\n";
		
		return str_res;
	}

}
