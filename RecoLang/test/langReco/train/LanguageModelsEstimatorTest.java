package langReco.train;


import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langModel.MiscUtil;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

/**
 * @author hernandez-n
 *
 */
public class LanguageModelsEstimatorTest {

	/**
	 * Method used to create a language model for each train text with order 3.
	 * The method also generate the config file for localize the trigrams models.
	 */
	@Test
	public void testCreateLmWordLangOrder3() {
		ArrayList<String> lang = new ArrayList<String>();

			lang.add("de");
			lang.add("es");
			lang.add("et");
			lang.add("hu");
			lang.add("it");
			lang.add("lv");
			lang.add("nl");
			lang.add("pl");
			lang.add("pt");
			lang.add("sv");
		
		String trainFilePathShort = "data/train/train-"; 	// Chaine contenant la premi�re partie du chemin vers le fichier
		String lmFilePathShort = "lm/trigram-train-"; 		// Chaine contenant la premi�re partie du chemin vers le fichier qui sera cr��
		int order = 3; 										// Le ngram cr�� sera d'ordre 3
		NgramCounts NgramCounts;
		String configFile = ""; 							// La variable servira � contenir le chemin des fichiers lm pour le fichier de conf
		
		for( String language : lang ) {
			
			NgramCounts= new MyNgramCounts();
			NgramCounts.scanTextFile(trainFilePathShort + language + ".txt", order);
			
			NgramCounts.writeNgramCountFile(lmFilePathShort + language + ".lm");
			
			configFile = configFile + language + " " + language + "_bi " +
							lmFilePathShort + language + ".lm" + "\n"; // on cr�� la chaine de caract�re contenant les chemins vers les fichiers
			
		}
		
		MiscUtil.writeFile(configFile, "lm/fichConfig_trigram.txt", false); // on place notre chaine de caract�re configFile dans notre fichier de configuration
		
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
