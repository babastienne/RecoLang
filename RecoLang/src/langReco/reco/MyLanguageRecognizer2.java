package langReco.reco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.MyVocabulary;
import langModel.NgramCounts;

public class MyLanguageRecognizer2 extends LanguageRecognizer{

	private HashMap<String,Map<String,MyLaplaceLanguageModel>> map;

	private MyNgramCounts unigramOfTheSentence;

	/**
	 * Constructor of the class
	 * @param String configFile wich correspond to the directory of the configuration file
	 */
	public MyLanguageRecognizer2(String directoryToConfigurationFile){
		super();
		langNgramCountMap = new HashMap<String,Map<String,String>>();
		loadNgramCountPath4Lang(directoryToConfigurationFile);

		map = new HashMap<String,Map<String,MyLaplaceLanguageModel>>();
		NgramCounts ngramForEachLang;
		MyLaplaceLanguageModel LanguageModelForLanguage;
		HashMap<String,MyLaplaceLanguageModel> mapPassage;

		for(String language : this.getLanguages()) {

			for(String IdLanguage: this.getNgramCountNames(language)) {

				ngramForEachLang = new MyNgramCounts();
				LanguageModelForLanguage = new MyLaplaceLanguageModel();
				mapPassage = new HashMap<String,MyLaplaceLanguageModel>();

				ngramForEachLang.readNgramCountsFile(this.getNgramCountPath(language, IdLanguage));
				LanguageModelForLanguage.setNgramCounts(ngramForEachLang);

				mapPassage.put(IdLanguage, LanguageModelForLanguage);
				map.put(language, mapPassage);
			}
		}
	}


	/**
	 * Method wich recognize the sentence language
	 * @param String sentence correspond to the sentence to determine
	 * @return String language correspond to the language of the sentence
	 */
	public String recognizeSentenceLanguage(String sentence) {
		lang = new ArrayList<String>(getLanguages());

		double proba = 0.0;
		String language="unk";

		generateUnigramOfTheSentence(sentence);
		MyLaplaceLanguageModel languageModel = new MyLaplaceLanguageModel();

		for(String Eachlanguage : lang)
			for(MyLaplaceLanguageModel mllm : map.get(Eachlanguage).values()) {
				Double probaSentenceForModel = mllm.getSentenceProb(sentence);
				if(probaSentenceForModel > proba) {
					proba = probaSentenceForModel;
					language = Eachlanguage;
					languageModel = mllm;
				}
			}
		
		return(verificationNgramInSentence(languageModel) >= (0.625 * unigramOfTheSentence.getTotalWordNumber()) || language.equals("lv")) ? language : "unk";
	}

	/**
	 * Getter of the number of words in the the sentence ('<s>' and '</s>' includes)
	 * @param sentence
	 * @return int the number of words in the sentence
	 */
	public void generateUnigramOfTheSentence(String sentence) {
		unigramOfTheSentence = new MyNgramCounts();
		unigramOfTheSentence.scanTextString(sentence, 1);
	}

	/**
	 * This method compare the vocabulary of a laplace model with the words of
	 * the unigram of the sentence. 
	 * Example : if the sentence is "the sentence is too short"
	 * 		 and the vocab of the laplace model contains the words 'the' 'long' 'sentence'
	 * 		 then the method return 3 because the vocabulary contains 3 words of the sentence tested.
	 * @param mllm wich correspond to the laplace language model
	 * @return the number of word wich are present in the sentence and the vocabulary
	 */
	public int verificationNgramInSentence(MyLaplaceLanguageModel mllm) {
		MyVocabulary vocab = (MyVocabulary) mllm.getVocabulary();
		int numberOfWordInSentence = 0;
		for(String word : vocab.getWords()) 
			for(String wordOfTheSentence : unigramOfTheSentence.getNgrams())
				if(word.equals(wordOfTheSentence)) numberOfWordInSentence++;
		return numberOfWordInSentence;
	}

}
