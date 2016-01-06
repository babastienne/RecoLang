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

public class appli {
	
	/**
	 * Main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LanguageRecognizer premierEssai = new MyLanguageRecognizer1("lm/fichConfig_bigram-100.txt");
		String phraseADecoder = "<s> futuro de los sistemas de seguridad social y de pensiones ( breve presentaciÃ³n ) </s>";
		String langue = premierEssai.recognizeSentenceLanguage(phraseADecoder);
		System.out.println("Phrase : " + phraseADecoder + "\nLangue trouv�e : " + langue);
		
		phraseADecoder = "<s> - penso que a comissÃ£o nÃ£o deseja intervir de novo . </s>";
		langue = premierEssai.recognizeSentenceLanguage(phraseADecoder);
		System.out.println("\nPhrase : " + phraseADecoder + "\nLangue trouv�e : " + langue);
		
		phraseADecoder = "<s> Ã­rÃ¡sbeli nyilatkozatok ( az eljÃ¡rÃ¡si szabÃ¡lyzat 149. cikke ) </s>";
		langue = premierEssai.recognizeSentenceLanguage(phraseADecoder);
		System.out.println("\nPhrase : " + phraseADecoder + "\nLangue trouv�e : " + langue);
		
		phraseADecoder = "<s> declaraÃ§Ãµes escritas inscritas no registo ( artigo 123.Âº do regimento ) : ver acta </s>";
		langue = premierEssai.recognizeSentenceLanguage(phraseADecoder);
		System.out.println("\nPhrase : " + phraseADecoder + "\nLangue trouv�e : " + langue);
		
		phraseADecoder = "<s> 3 . 2008. finanÅ¡u gada budÅ¾eta grozÄ«juma nr . 4 projekts ( </s>";
		langue = premierEssai.recognizeSentenceLanguage(phraseADecoder);
		System.out.println("\nPhrase : " + phraseADecoder + "\nLangue trouv�e : " + langue);
	}
}
