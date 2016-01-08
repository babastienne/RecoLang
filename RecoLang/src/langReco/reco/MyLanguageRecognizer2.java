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

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer2 extends LanguageRecognizer {
	
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
		
		LanguageModel laplaceModel;
		lang = new ArrayList<String>(getLanguages()); 	// on créé une liste des langues existantes pour pouvoir ensuite la parcourir
		Double probaLanguePhrase = 0.0;					// variable qui stockera la plus forte probabilité calculée
		String language = "unk"; 						// variable qui sera retournée et contiendra le code pays correspondant à la langue de la phrase transmise en paramètre
		
		for (String codeLangue : lang) {				// on parcours la liste des langues
	
			laplaceModel = new MyLaplaceLanguageModel();
			
			NgramCounts NgramOfCodeLangue = new MyNgramCounts();
			NgramOfCodeLangue.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
					(String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));
			
			laplaceModel.setNgramCounts(NgramOfCodeLangue);
			if(!this.languageIsUnknown(sentence, codeLangue)) {
				if (laplaceModel.getSentenceProb(sentence) > probaLanguePhrase) { 	//On calcul la probabilité que la phrase soit dans la langue 'codeLangue'
					probaLanguePhrase = laplaceModel.getSentenceProb(sentence); 	// Si la probabilité est suppérieur à celle calculée avant alors on stocke cette nouvelle probabilité
					language = codeLangue; 											// On stocke également le code de la langue correspondante (exemple : fr)
				}
			}
		}
		
		return language; // on retourne le code de la langue detectée pour la phrase
		
	}
	
	
	
	/*
	 * (non-javadoc)
	 * Cette méthode permet de déterminer si une langue est inconnue ou non.
	 * 
	 * @param la phrase à traiter
	 * @param le code de la langue avec laquelle on comparera la phrase
	 * @return boolean vrai si inconnue sinon fausse
	 * 
	 * Pour cela la méthode compare tout les unigrammes d'une langue (chaque mot) avec les mots de la phrase.
	 * Une phrase est qualifiée de langue inconnue si aucun mot de la phrase ne correspond à un mot de la langue voulue.
	 */
	public boolean languageIsUnknown(java.lang.String sentence, String codeLangue) {
//		MyNaiveLanguageModel laplace = new MyNaiveLanguageModel();
		NgramCounts UnigramOfTheSentence = new MyNgramCounts();
		UnigramOfTheSentence.scanTextString(sentence, 1); // on genere les unigrams de la phrase
		
		NgramCounts UnigramOfCodeLangue = new MyNgramCounts();
		UnigramOfCodeLangue.readNgramCountsFile("lm/unigram-train-" + codeLangue + ".lm");
		int num = 0;
		for(String lang : UnigramOfCodeLangue.getNgrams()) {	

			for(String gramSentence : UnigramOfTheSentence.getNgrams()) {
				
				if( gramSentence.equals(lang) ) num++;
				
			}
		}
		System.out.println(codeLangue + " : " + num);
		return(num > 3) ? false : true;
	}

}
