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
import java.util.Map;
import java.util.Set;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer2 extends LanguageRecognizer {
	
	private int identicalWords = 0;
	
	private NgramCounts unigramOfTheSentence = new MyNgramCounts();
	
	private Map<String, Integer> langWithIdenticalwords = new HashMap<String, Integer>(); 
	
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
		
		generateUnigramForTheSentence(sentence); 		// on génère les unigrams pour la phrase
				
		Map<String, Integer> myLanguages = languageIsUnknown();
 
		String language = "";
		
		if(myLanguages.size() > 0) {
			
			Double probaLanguePhrase = 0.0;
			LanguageModel laplaceModel;
			
			for (String codeLangue : myLanguages.keySet()) {				// on parcours la liste des langues
		
				laplaceModel = new MyLaplaceLanguageModel();
				
				NgramCounts NgramOfCodeLangue = new MyNgramCounts();
				NgramOfCodeLangue.readNgramCountsFile ( this.getNgramCountPath( codeLangue,
						(String) super.langNgramCountMap.get (codeLangue).keySet ().toArray ()[0]));
				
				laplaceModel.setNgramCounts(NgramOfCodeLangue);
				
				if ( ( identicalWords * laplaceModel.getSentenceProb( sentence ) ) > probaLanguePhrase ) { 	//On calcul la probabilité que la phrase soit dans la langue 'codeLangue'
					probaLanguePhrase = laplaceModel.getSentenceProb(sentence); 	// Si la probabilité est suppérieur à celle calculée avant alors on stocke cette nouvelle probabilité
					language = codeLangue; 											// On stocke également le code de la langue correspondante (exemple : fr)
				}
			}
			
			return (language == "") ? "unk" : language; // on retourne le code de la langue detectée pour la phrase
			
		} else {
			return "unk";
		}
		
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
	 * Une phrase est qualifiée de langue inconnue si moins de la moitié des unigrams la composant fait partie du dictionnaire d'une langue.
	 */
	public Map<String, Integer> languageIsUnknown() {
		
		numberOfWordsInLanguage();
		
		int totalIdenticalWords = 0;
		for(int word : langWithIdenticalwords.values()) {
			totalIdenticalWords += word;
		}

		int moyenneWord = totalIdenticalWords / langWithIdenticalwords.size();
		
		Map<String, Integer> myLanguages = new HashMap<String, Integer>(); 
		
		for(String codeLangue : langWithIdenticalwords.keySet()) {
			if (langWithIdenticalwords.get(codeLangue) > moyenneWord) 
				if(langWithIdenticalwords.get(codeLangue) > unigramOfTheSentence.getTotalWordNumber() / 2) myLanguages.put(codeLangue, langWithIdenticalwords.get(codeLangue));
		}
		
		return myLanguages;
		
	}
	
	/*
	 * (non-javadoc)
	 * Cette méthode renvoi le nombre de mot de la phrase recherché qui existent également dans la langue (correspondante à codelangue)
	 * On peut ainsi savoir si la phrase appartient au dictionnaire de la langue
	 */
	public void numberOfWordsInLanguage () {
		
		NgramCounts unigramOfCodeLangue = new MyNgramCounts();
		
		Set<String> lang = getLanguages();
		
		for (String codeLangue : lang) {
			
			unigramOfCodeLangue.readNgramCountsFile("lm/unigram-train-" + codeLangue + ".lm");
			
			identicalWords = 0; // on réinitialise le nombre de mots identiques
			
			for(String language : unigramOfCodeLangue.getNgrams()) {	

				for(String gramSentence : unigramOfTheSentence.getNgrams()) {
					
					if( gramSentence.equals(language) ) identicalWords++;
					
				}
			}
			
			langWithIdenticalwords.put(codeLangue, identicalWords);
			
		}


	}
	
	private void generateUnigramForTheSentence (String sentence) {
		unigramOfTheSentence.scanTextString(sentence, 1); // on genere les unigrams de la phrase
	}

}
