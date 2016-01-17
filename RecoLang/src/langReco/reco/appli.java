/**
 * Appli - main class for testing the differents languages recognizers
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 06/01/2016
 * @notes This class is in developpement
 */

package langReco.reco;

import langReco.eval.Performance;

public class appli {
	
	/**
	 * Main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

        	String goldSent = "data/gold/gold-sent.txt";
        	String goldLang = "data/gold/gold-lang.txt";
        
        	MyLanguageRecognizer2 config = new MyLanguageRecognizer2("lm/fichConfig_bigram.txt");
        
        	String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer2WithBigram";
        	config.recognizeFileLanguage(goldSent, hypLangFilePath);
        	System.out.println("Performance of the run = " + Performance.evaluate(goldLang, hypLangFilePath));
		
	}

}
