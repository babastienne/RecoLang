/**
 * MyLanguageRecognizer1 - first test for hte project
 * explication supplémentaire si nécessaire
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 06/01/2016
 * @notes Still in developpement
 * 	Amméliorer les commentaire, respecter les normes de qualité de code
 */

package langReco.reco;

import java.util.ArrayList;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer1 extends LanguageRecognizer {
	
	/**
	 * Constructor of the MyLanguageRecognizer1 class.
	 * 
	 * @param directoryToConfigurationFile configFile the file path of the configuration file containing the information 
	 * 	on the language models (language, name and file path).
	 */
	public MyLanguageRecognizer1(String directoryToConfigurationFile) {
		super(); 															// on appel le constructeur de LanguageRecognizer.java
		super.loadNgramCountPath4Lang(directoryToConfigurationFile);		// on déclare que la variable passée en paramètre contient le chemin du fichier de configuration (liste des langues ...)
	}
	
	/**
	 * @see langReco.reco.LanguageRecognizer#recognizeSentenceLanguage(java.lang.String)
	 */
	@Override
	public String recognizeSentenceLanguage(String sentence) {
		
		LanguageModel laplaceModel;
		lang = new ArrayList<String>(getLanguages());
		Double probaLanguePhrase = 0.0;
		String language = ""; // variable qui sera retournée et contiendra le code pays correspondant à la langue de la phrase transmise en paramètre
		
		for (String codeLangue : lang) {
	
			laplaceModel = new MyLaplaceLanguageModel();
			
			NgramCounts test = new MyNgramCounts();
			test.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
					(String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));
			
			laplaceModel.setNgramCounts(test);
			
			if (laplaceModel.getSentenceProb(sentence) > probaLanguePhrase) { 	//On calcul la probabilité que la phrase soit dans la langue 'codeLangue'
				probaLanguePhrase = laplaceModel.getSentenceProb(sentence); 	// Si la probabilité est suppérieur à celle calculée avant alors on stocke cette nouvelle probabilité
				language = codeLangue; 											// On stocke également le code de la langue correspondante (exemple : fr)
			}
		}
		
		return language;
		
	}

}
