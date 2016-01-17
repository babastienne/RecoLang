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


	public void setNgramCounts(NgramCounts ngram) {
		this.ngramCounts= ngram;
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
	public abstract Double getNgramProb(String ngram);

	@Override
	public Double getSentenceProb(String sentence) {
		
		double probaSentence = 1.0;
		
		MyNgramCounts ngramSentence = new MyNgramCounts();
		ngramSentence.scanTextString(sentence, this.ngramCounts.getMaximalOrder());
		
		for(String eachNGramSentence : ngramSentence.getNgrams()) {
			probaSentence = probaSentence * getNgramProb(eachNGramSentence);
		}
		
		return probaSentence;

	}

}