package langReco.reco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer4 extends LanguageRecognizer{
	
	private HashMap<String,Map<String,MyLaplaceLanguageModel>> map;
	
	/**
	 * Constructor of the class
	 * @param String configFile wich correspond to the directory of the configuration file
	 */
	public MyLanguageRecognizer4(String directoryToConfigurationFile){
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
		
		lang = new ArrayList<String>(getLanguages());
		
		double proba = 0.0;
		String language="unk";
		
		for(String Eachlanguage : lang) 
			for(MyLaplaceLanguageModel mllm : map.get(Eachlanguage).values())
				if(mllm.getSentenceProb(sentence) > proba){
					proba = mllm.getSentenceProb(sentence);
					language = Eachlanguage;
				}

		return language;
	}

}