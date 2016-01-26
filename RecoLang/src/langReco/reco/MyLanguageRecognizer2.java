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
     * @param String sentence wich correspond to the sentence to determine
     */
    public String recognizeSentenceLanguage(String sentence) {
	int numberOfWordInTheSentence = getNumberOfWordInSentence(sentence);
	lang = new ArrayList<String>(getLanguages());

	double proba = 0.0;
	String language="unk";
//	MyLaplaceLanguageModel laplace = null;

	for(String Eachlanguage : lang)
	    for(MyLaplaceLanguageModel mllm : map.get(Eachlanguage).values())
		if(mllm.getSentenceProb(sentence) > proba) {
    		    if(verificationNgramInSentence(mllm) >= 0.6 * numberOfWordInTheSentence) {
        		    proba = mllm.getSentenceProb(sentence);
        		    language = Eachlanguage;
//            		    laplace = mllm;

		    }
//    		    if(laplace == null)
//    		    laplace = mllm;
		}
//	if(language.equals("unk"))
//	    System.out.println(/*"Langue : " + language *//*+ " proba : " + proba + */" nbWord :" + numberOfWordInTheSentence + " equals : " + verificationNgramInSentence(laplace) + " tot : " + (double)verificationNgramInSentence(laplace)/(double)numberOfWordInTheSentence);
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
    
    public int verificationNgramInSentence(MyLaplaceLanguageModel mllm) {
	MyVocabulary vocab = (MyVocabulary) mllm.getVocabulary();
	int numberOfWordInSentence = 0;
	for(String word : vocab.getWords()) 
	    for(String wordOfTheSentence : unigramOfTheSentence.getNgrams())
		if(word.equals(wordOfTheSentence)) numberOfWordInSentence++;
	return numberOfWordInSentence;
    }

}