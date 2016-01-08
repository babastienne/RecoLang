/**
 * MyLanguageRecognizer2 - Goal : detect the unknown languages
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes 
 * 	Amm�liorer les commentaires, respecter les normes de qualit� de code
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
		super.loadNgramCountPath4Lang(directoryToConfigurationFile);		// on d�clare que la variable pass�e en param�tre contient le chemin du fichier de configuration (liste des langues ...)
	}
	
	/**
	 * @see langReco.reco.LanguageRecognizer#recognizeSentenceLanguage(java.lang.String)
	 */
	@Override
	public String recognizeSentenceLanguage(String sentence) {
		
		LanguageModel laplaceModel;
		lang = new ArrayList<String>(getLanguages()); 	// on cr�� une liste des langues existantes pour pouvoir ensuite la parcourir
		Double probaLanguePhrase = 0.0;					// variable qui stockera la plus forte probabilit� calcul�e
		String language = "unk"; 						// variable qui sera retourn�e et contiendra le code pays correspondant � la langue de la phrase transmise en param�tre
		
		for (String codeLangue : lang) {				// on parcours la liste des langues
	
			laplaceModel = new MyLaplaceLanguageModel();
			
			NgramCounts NgramOfCodeLangue = new MyNgramCounts();
			NgramOfCodeLangue.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
					(String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));
			
			laplaceModel.setNgramCounts(NgramOfCodeLangue);
			if(!this.languageIsUnknown(sentence, codeLangue)) {
				if (laplaceModel.getSentenceProb(sentence) > probaLanguePhrase) { 	//On calcul la probabilit� que la phrase soit dans la langue 'codeLangue'
					probaLanguePhrase = laplaceModel.getSentenceProb(sentence); 	// Si la probabilit� est supp�rieur � celle calcul�e avant alors on stocke cette nouvelle probabilit�
					language = codeLangue; 											// On stocke �galement le code de la langue correspondante (exemple : fr)
				}
			}
		}
		
		return language; // on retourne le code de la langue detect�e pour la phrase
		
	}
	
	
	
	/*
	 * (non-javadoc)
	 * Cette m�thode permet de d�terminer si une langue est inconnue ou non.
	 * 
	 * @param la phrase � traiter
	 * @param le code de la langue avec laquelle on comparera la phrase
	 * @return boolean vrai si inconnue sinon fausse
	 * 
	 * Pour cela la m�thode compare tout les unigrammes d'une langue (chaque mot) avec les mots de la phrase.
	 * Une phrase est qualifi�e de langue inconnue si aucun mot de la phrase ne correspond � un mot de la langue voulue.
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
