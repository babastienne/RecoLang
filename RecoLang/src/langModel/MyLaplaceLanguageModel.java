package langModel;


/**
 * Class MyLaplaceLanguageModel: class inheriting the class MyNaiveLanguageModel by creating 
 * a n-gram language model using a Laplace smoothing.
 * 
 * @author ... (2015)
 *
 */

public class MyLaplaceLanguageModel extends MyNaiveLanguageModel {

	@Override
	public Double getNgramProb(String ngram) {
		double count = 0.0;
		double countBis = 0.0;
		String wArray[];
		String ngrameBis="";
		System.out.println(this.ngramCounts.getCounts(ngram));
		
		if(this.ngramCounts.getCounts(ngram)!=0){
			count = this.ngramCounts.getCounts(ngram);
			wArray = ngram.split("\\s+");
			for(int i =0; i < wArray.length-2; i++){
				ngrameBis =ngrameBis + wArray[i] + " ";
			}
			ngrameBis =ngrameBis + wArray[wArray.length-1];
			System.out.println(ngrameBis);
			if(this.ngramCounts.getCounts(ngrameBis)!=0){
				countBis = this.ngramCounts.getCounts(ngrameBis);
			} else {
				return 0.0;
			}
		} else {
			return 0.0;
		}
		return count / countBis;
	}
}
