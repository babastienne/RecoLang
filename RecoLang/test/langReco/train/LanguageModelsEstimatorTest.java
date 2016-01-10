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

	private ArrayList<String> lang = new ArrayList<String>();
	
	public LanguageModelsEstimatorTest() {
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
	}
	/**
	 * Method used to create a language model for each train text with order 3.
	 * The method also generate the config file for localize the trigrams models.
	 */
	@Test
	public void testCreateLmWordLangOrder3() {
		
		String trainFilePathShort = "data/train/train-"; 	// Chaine contenant la première partie du chemin vers le fichier
		String lmFilePathShort = "lm/trigram-train-"; 		// Chaine contenant la première partie du chemin vers le fichier qui sera créé
		int order = 3; 										// Le ngram créé sera d'ordre 3
		NgramCounts NgramCounts;
		String configFile = ""; 							// La variable servira à contenir le chemin des fichiers lm pour le fichier de conf
		
		for( String language : lang ) {
			
			NgramCounts= new MyNgramCounts();
			NgramCounts.scanTextFile(trainFilePathShort + language + ".txt", order);
			
			NgramCounts.writeNgramCountFile(lmFilePathShort + language + ".lm");
			
			configFile = configFile + language + " " + language + "_tri " +
							lmFilePathShort + language + ".lm" + "\n"; // on créé la chaine de caractère contenant les chemins vers les fichiers
			
		}
		
		MiscUtil.writeFile(configFile, "lm/fichConfig_trigram.txt", false); // on place notre chaine de caractère configFile dans notre fichier de configuration
		
	}
	
	/**
	 * Method used to create a language model for each train text with order 1.
	 * The method also generate the config file for localize the unigrams models.
	 */
	@Test
	public void testCreateLmWordLangOrder1() {
		
		String trainFilePathShort = "data/train/train-"; 	// Chaine contenant la première partie du chemin vers le fichier
		String lmFilePathShort = "lm/unigram-train-"; 		// Chaine contenant la première partie du chemin vers le fichier qui sera créé
		int order = 1; 										// Le ngram créé sera d'ordre 1
		NgramCounts NgramCounts;
		String configFile = ""; 							// La variable servira à contenir le chemin des fichiers lm pour le fichier de conf
		
		for( String language : lang ) {
			
			NgramCounts= new MyNgramCounts();
			NgramCounts.scanTextFile(trainFilePathShort + language + ".txt", order);
			
			NgramCounts.writeNgramCountFile(lmFilePathShort + language + ".lm");
			
			configFile = configFile + language + " " + language + "_uni " +
							lmFilePathShort + language + ".lm" + "\n"; // on créé la chaine de caractère contenant les chemins vers les fichiers
			
		}
		
		MiscUtil.writeFile(configFile, "lm/fichConfig_unigram.txt", false); // on place notre chaine de caractère configFile dans notre fichier de configuration
		
	}
	
	/**
	 * Method used to create a language model for each train text with order 1.
	 * The method also generate the config file for localize the unigrams models.
	 */
	@Test
	public void testCreateLmWordLangOrder2() {
		
		String trainFilePathShort = "data/train/train-"; 	// Chaine contenant la première partie du chemin vers le fichier
		String lmFilePathShort = "lm/bigram-train-"; 		// Chaine contenant la première partie du chemin vers le fichier qui sera créé
		int order = 1; 										// Le ngram créé sera d'ordre 1
		NgramCounts NgramCounts;
		String configFile = ""; 							// La variable servira à contenir le chemin des fichiers lm pour le fichier de conf
		
		for( String language : lang ) {
			
			NgramCounts= new MyNgramCounts();
			NgramCounts.scanTextFile(trainFilePathShort + language + ".txt", order);
			
			NgramCounts.writeNgramCountFile(lmFilePathShort + language + ".lm");
			
			configFile = configFile + language + " " + language + "_bi " +
							lmFilePathShort + language + ".lm" + "\n"; // on créé la chaine de caractère contenant les chemins vers les fichiers
			
		}
		
		MiscUtil.writeFile(configFile, "lm/fichConfig_bigram.txt", false); // on place notre chaine de caractère configFile dans notre fichier de configuration
		
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
