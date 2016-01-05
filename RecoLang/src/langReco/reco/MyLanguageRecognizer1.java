package langReco.reco;

import java.util.HashMap;
import java.util.Map;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer1 extends LanguageRecognizer {
	
	public MyLanguageRecognizer1() {
		super();
		loadNgramCountPath4Lang("lm/fichConfig_bigram-100.txt");
		recognizeSentenceLanguage("<s> futuro de los sistemas de seguridad social y de pensiones ( breve presentación ) </s>");
	}
	
	@Override
	public String recognizeSentenceLanguage(String sentence) {

		Map<String,String> monMap = new HashMap<String, String>(); // nul
		int order = 1;
		Double proba = 0.0;
		String name = "";
		String mamerde = "";
		NgramCounts montruc;
		LanguageModel frllm;
		
		// Parcourir langNgramCountMap
		for(String lang: langNgramCountMap.keySet()) {
			
			for(String idLM: langNgramCountMap.get(lang).keySet()) {
				
				montruc = new MyNgramCounts();
				
				montruc.scanTextFile(getNgramCountPath(lang, idLM), order);
				
				montruc.writeNgramCountFile(mamerde);
				frllm = new MyLaplaceLanguageModel();
				montruc.readNgramCountsFile(mamerde);
				
				if(frllm.getSentenceProb(sentence) > proba) {
					proba = frllm.getSentenceProb(sentence);
					name = lang;
				}
			}
			
		}
		
		return name;
		
	}

}
