package langModel;


/**
 * Interface LanguageModel: interface to describe a language model.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public interface LanguageModel {

	/**
	 * Setter associating the current language model to a NgramCounts object.
	 * It also scans the set of ngrams of the ngram counts structure and builds a vocabulary with it.
	 * 
	 * @param ngramCounts the NgramCounts object to set.
	 */
	public void setNgramCounts(NgramCounts ngramCounts);


	/**
	 * Getter of the language model order.
	 * In practice it will get the maximal order of the NgramCounts structure.
	 * @return the maximal order of the language model.
	 */
	public int getLMOrder();

	
	/**
	 * Getter of the size of the vocabulary.
	 * @return the size of the vocabulary.
	 */
	public int getVocabularySize();


	/**
	 * Method computing and returning the probability of the given n-gram,
	 * using the NgramCounts structure.
	 * An implementation can consider the Laplace backoff smoothing.
	 * Another one can compute the log-probability instead of the probability...
	 * 
	 * @param ngram the n-gram whose probability to compute.
	 * @return the probability of the given n-gram.
	 */
	public Double getNgramProb (String ngram);

	
	/**
	 * Method computing and returning the probability of the given sentence,
	 * according to its n-grams.
	 * 
	 * @param sentence the sentence whose probability to compute.
	 * @return the probability of the given sentence.
	 */
	public Double getSentenceProb (String sentence);
}
