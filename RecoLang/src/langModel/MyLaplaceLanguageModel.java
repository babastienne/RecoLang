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
	return((double) (this.ngramCounts.getCounts(ngram) + 1) / this.ngramCounts.getCounts(NgramUtil.getHistory(ngram, this.ngramCounts.getMaximalOrder())) != 1.0) ? (double) (this.ngramCounts.getCounts(ngram) + 1) / this.ngramCounts.getCounts(NgramUtil.getHistory(ngram, this.ngramCounts.getMaximalOrder())) : (double) (this.ngramCounts.getCounts(ngram) + 1) / this.ngramCounts.getTotalWordNumber() + this.vocabulary.getSize();
    }
}
