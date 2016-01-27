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

    //TEST
    private HashMap<String, Integer> nbLang;
    //END TEST

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


	//TEST
	nbLang = new HashMap<String, Integer>();
	for(String lang : getLanguages()) {
	    nbLang.put(lang, 0);
	}
	nbLang.put("unk", 0);
	//END TEST


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
     * @param String sentence wich correspond to the sentence to determine
     */
    public String recognizeSentenceLanguage(String sentence) {
	int numberOfWordInTheSentence = getNumberOfWordInSentence(sentence);
	lang = new ArrayList<String>(getLanguages());

	double proba = 0.0;
	String language="unk";

	for(String Eachlanguage : lang)
	    for(MyLaplaceLanguageModel mllm : map.get(Eachlanguage).values())
		if(mllm.getSentenceProb(sentence) > proba)
		    if(verificationNgramInSentence(mllm) >= 0.625 * numberOfWordInTheSentence || Eachlanguage.equals("lv")) {
			proba = mllm.getSentenceProb(sentence);
			language = Eachlanguage;

		    }
	//TEST
	nbLang.put(language, nbLang.get(language)+1);
	//END TEST

	return language;
    }

    /**
     * Getter of the number of words in the the sentence ('<s>' and '</s>' includes)
     * @param sentence
     * @return int the number of words in the sentence
     */
    public int getNumberOfWordInSentence(String sentence) {
	unigramOfTheSentence = new MyNgramCounts();
	unigramOfTheSentence.scanTextString(sentence, 1);

	return unigramOfTheSentence.getTotalWordNumber();
    }

    /**
     * This method compare the vocabulary of a laplace model with the words of
     * the unigram of the sentence. 
     * Example : if the sentence is "the sentence is too short"
     * 		 and the vocab of the laplace model contains the words 'the' 'long' 'sentence'
     * 		 then the method return 3 because the voabulary contains 3 words of the sentence tested.
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

    /*
     * (non-javadoc) TEST METHOD (delete for final package)
     * this method print the number of sentence recognize in each language
     */
    public void afficherLanguages() {
	for(String lang : nbLang.keySet()) {
	    System.out.println(lang + " : " + nbLang.get(lang));
	}
    }

}