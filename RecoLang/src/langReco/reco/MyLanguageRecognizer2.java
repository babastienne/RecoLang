/**
 * MyLanguageRecognizer2 - Goal : detect the unknown languages
 *
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes
 * 	AmmÃ©liorer les commentaires, respecter les normes de qualitÃ© de code
 */

package langReco.reco;

import java.util.Set;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNaiveLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer2 extends LanguageRecognizer {

    NgramCounts unigramOfTheSentence = new MyNgramCounts();

    /**
     * Constructor of the MyLanguageRecognizer1 class.
     *
     * @param directoryToConfigurationFile configFile the file path of the configuration file containing the information
     * 	on the language models (language, name and file path).
     */
    public MyLanguageRecognizer2(String directoryToConfigurationFile) {
	super(); 															// on appel le constructeur de LanguageRecognizer.java
	super.loadNgramCountPath4Lang(directoryToConfigurationFile);		// on dÃ©clare que la variable passÃ©e en paramÃ¨tre contient le chemin du fichier de configuration (liste des langues ...)
    }

    /**
     * @see langReco.reco.LanguageRecognizer#recognizeSentenceLanguage(java.lang.String)
     */
    @Override
    public String recognizeSentenceLanguage(String sentence) {
	LanguageModel laplaceModel;
	Double probaLanguePhrase = 0.0;				// variable qui stockera la plus forte probabilitÃ© calculÃ©e
	String language = "unk"; 					// variable qui sera retournÃ©e et contiendra le code pays correspondant Ã  la langue de la phrase transmise en paramÃ¨tre

	generateUnigramOfTheSentence(sentence);
	
	Set<String> myLanguagesWords = getLanguages();

	for (String codeLangue : myLanguagesWords) {				// on parcours la liste des langues pré-selectionnées

	    laplaceModel = new MyNaiveLanguageModel();

	    NgramCounts NgramOfCodeLangue = new MyNgramCounts();
	    NgramOfCodeLangue.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
		    (String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));

	    laplaceModel.setNgramCounts(NgramOfCodeLangue);
	    double probaPhrase = laplaceModel.getSentenceProb(sentence);
//	    System.out.println(codeLangue + " proba : " + probaPhrase);
	    if (probaPhrase > probaLanguePhrase) { 	//On calcul la probabilitÃ© que la phrase soit dans la langue 'codeLangue'
	    	probaLanguePhrase = probaPhrase; 	// Si la probabilitÃ© est suppÃ©rieur Ã  celle calculÃ©e avant alors on stocke cette nouvelle probabilitÃ©
	    	language = codeLangue; 						// On stocke Ã©galement le code de la langue correspondante (exemple : fr)
	    }
	}
	
	return language; // on retourne le code de la langue detectÃ©e pour la phrase

    }


    /**
     * This method generate a the unigrams of the sentence in param.
     *
     * @param sentence is the sentence to use for generate the unigram
     */
    public void generateUnigramOfTheSentence(String sentence) {
	unigramOfTheSentence.scanTextString(sentence, 1); // on genere les unigrams de la phrase
    }

}
