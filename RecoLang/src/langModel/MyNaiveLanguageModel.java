package langModel;

import java.util.List;

/**
 * Class MyNaiveLanguageModel: class implementing the interface LanguageModel by creating a naive language model,
 * i.e. a n-gram language model with no smoothing.
 * 
 * @author ... (2015)
 *
 */
public class MyNaiveLanguageModel implements LanguageModel {
	/**
	 * The NgramCounts corresponding to the language model.
	 */
	protected NgramCounts ngramCounts;

	/**
	 * The vocabulary of the language model.
	 */
	protected Vocabulary vocabulary;


	/**
	 * Constructor.
	 */
	public MyNaiveLanguageModel(){
		this.ngramCounts = new MyNgramCounts();
		this.vocabulary = new MyVocabulary();
	}


	public void setNgramCounts(NgramCounts ngram) {
		this.ngramCounts = ngram;
		this.vocabulary.scanNgramSet(this.ngramCounts.getNgrams());
	}

	@Override
	public int getLMOrder() {
		return this.ngramCounts.getMaximalOrder();
	}

	@Override
	public int getVocabularySize() {
		return this.ngramCounts.getNgramCounterSize();
	}

	@Override
	public Double getNgramProb(String ngram) {
		String history = NgramUtil.getHistory(ngram, getLMOrder());
		Double denom = new Double(0);
		if(history.equals("")){
			denom = Double.valueOf(this.ngramCounts.getTotalWordNumber());
		}else{
			denom = Double.valueOf(this.ngramCounts.getCounts(history));
		}
		Double num = Double.valueOf(this.ngramCounts.getCounts(ngram));
		Double prob= num/denom;
		if(Double.isNaN(prob)){
			prob = Double.valueOf(0);
		}
		return prob;
	    
	    
	    
	    
	    //		double count = 0.0;
//		double countBis = 0.0;
//		String wArray[];
//		String ngrameBis="";
////		System.out.println(this.ngramCounts.getCounts(ngram));
//		
//		if(this.ngramCounts.getCounts(ngram)!=0){
//			count = this.ngramCounts.getCounts(ngram);
//			wArray = ngram.split("\\s+");
//			for(int i =0; i < wArray.length-2; i++){
//				ngrameBis =ngrameBis + wArray[i] + " ";
//			}
//			ngrameBis =ngrameBis + wArray[wArray.length-1];
////			System.out.println(ngrameBis);
//			if(this.ngramCounts.getCounts(ngrameBis)!=0){
//				countBis = this.ngramCounts.getCounts(ngrameBis);
//			} else {
//				return 1.0;
//			}
//		} else {
//			return 1.0;
//		}
//		return count / countBis;
	}

	@Override
	public Double getSentenceProb(String sentence) {
		Double proba = 1.0;
		List<String> MyNgrams = NgramUtil.generateNgrams(sentence, 1, this.getLMOrder());
		for(String Eachngram : MyNgrams){
			proba = Double.valueOf(this.getNgramProb(Eachngram) * proba);
		}
		return proba;

	}

}
