/**
 * MyLanguageRecognizer2 - Goal : detect the unknown languages
 *
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes
 * 	Amméliorer les commentaires, respecter les normes de qualité de code
 */

package langReco.reco;

import java.util.HashMap;
import java.util.Set;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
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
	super.loadNgramCountPath4Lang(directoryToConfigurationFile);		// on déclare que la variable passée en paramètre contient le chemin du fichier de configuration (liste des langues ...)
    }

    /**
     * @see langReco.reco.LanguageRecognizer#recognizeSentenceLanguage(java.lang.String)
     */
    @Override
    public String recognizeSentenceLanguage(String sentence) {
	// TO CHANGE
	LanguageModel laplaceModel;
	Double probaLanguePhrase = 0.0;					// variable qui stockera la plus forte probabilité calculée
	String language = "unk"; 					// variable qui sera retournée et contiendra le code pays correspondant à la langue de la phrase transmise en paramètre
	int nbrIdenticWords = 0; // TEST

	

	HashMap<String, Integer> myLanguagesWords = languagesWithMostIdenticalsWords(sentence);

	for (String codeLangue : myLanguagesWords.keySet()) {				// on parcours la liste des langues pr�-selectionn�es

	    laplaceModel = new MyLaplaceLanguageModel();

	    NgramCounts NgramOfCodeLangue = new MyNgramCounts();
	    NgramOfCodeLangue.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
		    (String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));

	    laplaceModel.setNgramCounts(NgramOfCodeLangue);

	    if (myLanguagesWords.get(codeLangue) * laplaceModel.getSentenceProb(sentence) > probaLanguePhrase) { 	//On calcul la probabilité que la phrase soit dans la langue 'codeLangue'
		probaLanguePhrase = laplaceModel.getSentenceProb(sentence); 	// Si la probabilité est suppérieur à celle calculée avant alors on stocke cette nouvelle probabilité
		language = codeLangue; 						// On stocke également le code de la langue correspondante (exemple : fr)
		nbrIdenticWords = myLanguagesWords.get(codeLangue);
	    }
	}
//	System.out.println("Langue :" + language + " nbrMots : " + nbrIdenticWords);
	return language; // on retourne le code de la langue detectée pour la phrase

    }


    /*
     * (non-javadoc)
     * Cette méthode permet de déterminer si une langue est inconnue ou non.
     *
     * On consid�re (d�cid� au hasard et m�rite d'�tudier + les r�sultats pour �tre amm�lior�)
     * qu'une langue est inconnue quand moins de la moiti� des mots de la phrase sont pr�sent dans les dictionnaires des langues
     */
    public boolean languageIsUnknown(int numberOfIdenticalWordsOfTheLanguage) {
	// TODO just an example of implementation
	return(numberOfIdenticalWordsOfTheLanguage < (unigramOfTheSentence.getNgramCounterSize() / 3));
    }

    /**
     * This method create a hashmap of String and Integer.
     * For each language, the integer associate correspond to the number of words wich are
     * identicals with the words of the sentence.
     *
     * @return HashMap<String, Integer> wich correspond to the keys (the languages) associated with the number of words wich are identical in the sentence (values)
     */
    public HashMap<String, Integer> numberOfWordsInLanguage() {

	HashMap<String, Integer> langWithIdenticalwords = new HashMap<String, Integer>();

	NgramCounts unigramOfCodeLangue = new MyNgramCounts();

	int identicalWords;

	Set<String> lang = getLanguages();

	for (String codeLangue : lang) {

	    unigramOfCodeLangue.readNgramCountsFile("lm/unigram-train-" + codeLangue + ".lm");

	    identicalWords = 0; // on réinitialise le nombre de mots identiques

	    for(String language : unigramOfCodeLangue.getNgrams()) {

		for(String gramSentence : unigramOfTheSentence.getNgrams()) {

		    if( gramSentence.equals(language) ) {
			identicalWords++;
		    }

		}
	    }
//	    System.out.println(codeLangue + " : " + identicalWords); // TEST
	    langWithIdenticalwords.put(codeLangue, identicalWords);

	}

	return langWithIdenticalwords;
    }

    /**
     * This method select the languages with the most identical words with the sentence.
     * It select the top 3 languages, then the recognize part can be run faster.
     *
     * @param String the sentence to generate and use for the selection of languages
     * @return HashMap<Sting, Integer> wich contain the languages with the most identicals words and the number of identical words
     */
    public HashMap<String, Integer> languagesWithMostIdenticalsWords(String sentence) {
	generateUnigramOfTheSentence(sentence); // we generate the unigram of the sentence

	HashMap<String, Integer> allLanguages = numberOfWordsInLanguage(); // we call the function who count the number of identicals word for each language

	HashMap<String, Integer> selectedLanguages = new HashMap<String, Integer>();

	for(String language : allLanguages.keySet()) {
	    if(!languageIsUnknown(allLanguages.get(language))) {
		selectedLanguages.put(language, allLanguages.get(language));
	    }
	}
	return selectedLanguages;
    }


    /**
     * This method generate a the unigrams of the sentence in param.
     *
     * @param sentence is the sentence to use for generate the unigram
     */
    public void generateUnigramOfTheSentence(String sentence) {
	unigramOfTheSentence.scanTextString(sentence, 1); // on genere les unigrams de la phrase
//	System.out.println(sentence + " : " + unigramOfTheSentence.getNgramCounterSize()); //TEST
    }

}
