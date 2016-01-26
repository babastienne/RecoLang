package langModel;

/**
 * Class MyNaiveLanguageModel: class implementing the interface LanguageModel by creating a naive language model,
 * i.e. a n-gram language model with no smoothing.
 * 
 * @author ... (2015)
 *
 */
public abstract class MyNaiveLanguageModel implements LanguageModel {
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


    /* (non-Javadoc)
     * @see langModel.LanguageModel#setNgramCounts(langModel.NgramCounts)
     */
    public void setNgramCounts(NgramCounts ngram) {
	this.ngramCounts = ngram;
	this.vocabulary.scanNgramSet(this.ngramCounts.getNgrams());
    }

    /* (non-Javadoc)
     * @see langModel.LanguageModel#getLMOrder()
     */
    @Override
    public int getLMOrder() {
	return this.ngramCounts.getMaximalOrder();
    }

    /* (non-Javadoc)
     * @see langModel.LanguageModel#getVocabularySize()
     */
    @Override
    public int getVocabularySize() {
	return this.ngramCounts.getNgramCounterSize();
    }

    /* (non-Javadoc)
     * @see langModel.LanguageModel#getNgramProb(java.lang.String)
     */
    @Override
    public abstract Double getNgramProb(String ngram); 

    /* (non-Javadoc)
     * @see langModel.LanguageModel#getSentenceProb(java.lang.String)
     */
    @Override
    public Double getSentenceProb(String sentence) {
	Double proba = 1.0;
	for(String Eachngram : NgramUtil.generateNgrams(sentence, 1, this.getLMOrder())) proba = Double.valueOf(this.getNgramProb(Eachngram) * proba);
	return proba;

    }
    
    public Vocabulary getVocabulary() {
	return this.vocabulary;
    }

}
