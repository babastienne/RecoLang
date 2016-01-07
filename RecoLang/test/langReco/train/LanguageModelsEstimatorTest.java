package langReco.train;


import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langModel.MyNgramCounts;
import langModel.NgramCounts;

/**
 * @author hernandez-n
 *
 */
public class LanguageModelsEstimatorTest {

	/**
	 * Duplicate this method as many times as you want to create a language model.
	 * Give it an explicit name with respect to the created language model e.g. testCreateLmWordLangEnOrder3
	 * to create a language model with words from texts in English and order 3.
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
		
		String trainFilePathShort = "data/train/train-"; 	// Chaine contenant la première partie du chemin vers le fichier
		String lmFilePathShort = "lm/trigram-train-"; 		// Chaine contenant la première partie du chemin vers le fichier qui sera créé
		int order = 3; // Le ngram créé sera d'ordre 3
		NgramCounts NgramCounts;
		String trainFilePathComplete, lmFilePathComplete;
		
		
		for( String language : lang ) {
			
			trainFilePathComplete = trainFilePathShort + language + ".txt"; // A chaque iteration le début du chemin est le même, puis on ajoute le code de la langue et ".txt"
			lmFilePathComplete = lmFilePathShort + language + ".lm";		// A chaque itération on complète le chemin du fichier créé
			NgramCounts= new MyNgramCounts();
			NgramCounts.scanTextFile(trainFilePathComplete, order);
			NgramCounts.writeNgramCountFile(lmFilePathComplete);
			
		}		
		
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
