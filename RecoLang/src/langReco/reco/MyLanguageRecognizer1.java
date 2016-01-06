package langReco.reco;

import java.util.ArrayList;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

public class MyLanguageRecognizer1 extends LanguageRecognizer {
	
	public MyLanguageRecognizer1(String directoryToConfigurationFile) {
		super();
		super.loadNgramCountPath4Lang(directoryToConfigurationFile);
	}
	
	@Override
	public String recognizeSentenceLanguage(String sentence) {
		
		LanguageModel frllm;
		lang = new ArrayList<String>(getLanguages());
		Double probab = 0.0;
		String language="";
//		for(String l : lang){
//			System.out.println("l : " + l);
//			for(String mllm : super.langNgramCountMap.get(l).values()){
//				System.out.println("mllm : " + mllm);
//			}
//		}
		
		for(String l : lang){
			
			for(String mllm : super.langNgramCountMap.get(l).values()){
				
				frllm = new MyLaplaceLanguageModel();
				
				NgramCounts test = new MyNgramCounts();
				test.readNgramCountsFile(this.getNgramCountPath(l, l+"_bi"));
				frllm.setNgramCounts(test);
				
				if(frllm.getSentenceProb(sentence) > probab) {
					probab = frllm.getSentenceProb(sentence);
					language = l;
				}
			}	
		}
		
		return language;
		
	}

}
