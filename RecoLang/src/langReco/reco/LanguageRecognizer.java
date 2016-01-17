package langReco.reco;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import langModel.MiscUtil;


/**
 * Abstract class LanguageRecognizer: abstract class that every language recognition system has to implement.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public abstract class LanguageRecognizer {

	/**
	 * List of languages present in the train corpus + unk for a potential unknown language 
	 */
	protected List<String> lang;

	/**
	 * Map of ngram counter file paths associated with each language as each language can be 
	 * associated with several language models. 
	 * The keys to the first map are the ISO names of the languages (e.g., "fr") and the keys 
	 * to the second map are the names associated with each file containing a language model 
	 * (e.g., "fr-bigrams").
	 * The values of the second map are the file path associated with the given language name
	 * (e.g., "ngramCounts/fr-bigrams.lm").
	 */
	protected Map<String,Map<String,String>> langNgramCountMap;


	
	
	/**
	 * Constructor.
	 */
	public LanguageRecognizer() {
		lang = new ArrayList<String>();

		lang.add("da");
		lang.add("de");
		lang.add("en");
		lang.add("es");
		lang.add("et");
		lang.add("fr");
		lang.add("lv");
		lang.add("nl");
		lang.add("pl");
		lang.add("sv");
		lang.add("unk");

		langNgramCountMap = new HashMap<String,Map<String,String>>();
	}

	
	
	/**
	 * Accessor to the lang attribute.
	 * 
	 * @return the list of languages of the recognition system.
	 */
	protected List<String> getLang() {
		return this.lang;
	}
	
	
	/**
	 * Accessor to the langNgramCountMap attribute.
	 * 
	 * @return the map of language file paths of the recognition system.
	 */	
	protected Map<String,Map<String,String>> getLangNgramCountMap() {
		return this.langNgramCountMap;
	}
	
	
	/**
	 * Accessor to the languages of the langNgramCountMap attribute.
	 * 
	 * @return the set of languages for which at least one file path is declared.
	 */
	protected Set<String> getLanguages() {
		return getLangNgramCountMap().keySet();
	}
	
	
	/**
	 * Accessor to the language model names of a particular language in the langNgramCountMap attribute.
	 * 
	 * @param lang the language to consider.
	 * @return the set of language model names for the given language.
	 */
	protected Set<String> getNgramCountNames(String lang) {
		return getLangNgramCountMap().get(lang).keySet();
	}
	
	
	/**
	 * Method returning a collection of language model file paths associated with a language.
	 * 
	 * @param lang the language to consider.
	 * @return the collection of language model files for the given language.
	 */
	protected Collection<String> getNgramCountPath(String lang) {
		return getLangNgramCountMap().get(lang).values();
	}
	
	
	/**
	 * Accessor to the language model file path of a particular language model name in the langNgramCountMap attribute.
	 * 
	 * @param lang the language to consider.
	 * @param ngramCountName the name of the language model to consider.
	 * @return the file path of the language model whose language and model name are given.
	 */
	protected String getNgramCountPath(String lang, String ngramCountName) {
		return getLangNgramCountMap().get(lang).get(ngramCountName);
	}
	
	

	/**
	 * Method adding a language model file path to the langNgramCountMap attribute.
	 * 
	 * @param lang the language to consider.
	 * @param ngramCountName the name of the language model to add.
	 * @param ngramCountFilePath the file path of the language model to add.
	 */
	private void addTuple2LangNgramCountMap(String lang, String ngramCountName, String ngramCountFilePath) {
		Map<String,String> namePathMap = null;
		if (! getLangNgramCountMap().containsKey(lang)) 
			 namePathMap = new HashMap<String,String>();
		else 
			namePathMap = getLangNgramCountMap().get(lang);
		 namePathMap.put(ngramCountName, ngramCountFilePath);
		 getLangNgramCountMap().put(lang,namePathMap);
	}
	
	
	/**
	 * Method parsing a configuration file where each line is a tuple in the following format:
	 * lang ngramCountName ngramCountFilePath
	 * (each value is separated by a whitespace character; lines started with # are not considered)
	 * This method initialized the langNgramCountMap attribute with the language models used in the 
	 * recognition system.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (language, name and file path).
	 */
	public void loadNgramCountPath4Lang (String configFile) {
		langNgramCountMap = new HashMap<String,Map<String,String>>();
		
		List<String> configLines = MiscUtil.readTextFileAsStringList(configFile);

		for (String configLine : configLines) {
			if (!configLine.startsWith("#")) {
				String configLineParameters [] = configLine.split("\\s+");
				addTuple2LangNgramCountMap(configLineParameters[0], configLineParameters[1], configLineParameters[2]);
			}
		}
	}
	

	
	/**
	 * Method recognizing and returning the language of the given sentence by choosing randomly one
	 * of the languages of the recognition system (the unknown language can also be picked up).
	 * 
	 * --> the is the only method to implement!
	 * 
	 * @param sentence the sentence whose language is to recognize.
	 * @return the language of the sentence as recognized by the recognition system.
	 */
	public abstract String recognizeSentenceLanguage(String sentence);

	

	/**
	 * Method processing the sentences of a given reference file (one per line),
	 * and writing the hypothesis language of each sentence in another file.
	 * (this is the first version of the recognizeFileLanguage() method)
	 * 
	 * @param testSentenceFilePath the reference file containing the sentences whose language is to recognize.
	 * @param hypLangFilePath the hypothesis file of languages recognized by the recognition system.
	 */
	public void recognizeFileLanguage(String testSentenceFilePath, String hypLangFilePath) {
		//reads the sentences to recognize
		List<String> sentences = MiscUtil.readTextFileAsStringList(testSentenceFilePath);

		//predicts the language of each sentence
		String langPredict;
		StringBuffer languagePrediction = new StringBuffer();

		int i = 1;
		for (String sentence: sentences) {
			langPredict = recognizeSentenceLanguage(sentence);
			languagePrediction.append(langPredict);
			languagePrediction.append("\n");
//			if(i % 100 == 0)
				System.out.println("Phrases traitées jusqu'Ã  " + i + " langue : " + langPredict);
			i ++;
		}
		
		MiscUtil.writeFile(languagePrediction.toString(), hypLangFilePath, false);
	}	
}
