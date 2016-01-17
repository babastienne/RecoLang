package langModel;


import java.io.IOException;
import java.util.Set;


/**
 * Interface NgramCounts: interface to build the counts of n-grams from a training corpus 
 * (to be used by a language model).
 * 
 * Two methods are available to compute the counts of n-grams: 
 * either by parsing a corpus saved in a file, or by reading a NgramCounts previously saved in a file. 
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public interface NgramCounts {

	/**
	 * Getter of the maximal order of the n-grams in the training corpus.
	 * 
	 * A private method should be considered to set the value in the class implementing 
	 * the interface. In practice, the set method will be called when parsing the training corpus, 
	 * or when parsing the NgramCounts file (using the maximal n-gram length encountered).
	 * 
	 * @return the maximal order of n-grams considered.
	 */
	public int getMaximalOrder ();
	
	
	/**
	 * Getter of the data structure which maps the n-grams to the counts
	 * and return the number of mapped n-grams.
	 * 
	 * @return the number of distinct n-grams.
	 */
	public int getNgramCounterSize ();
	
	
	/**
	 * Getter of the total number of words attribute.
	 * 
	 * @return the total number of words in the corpus (different or not)
	 */
	public int getTotalWordNumber();
	
	
	/**
	 * Getter of the data structure which maps the n-grams to the counts
	 * and return the set of n-grams present in the structure.
	 * 
	 * @return the set of distinct n-grams.
	 */
	public Set<String> getNgrams ();
	
	
	/**
	 * Getter of the data structure which maps the n-grams to the counts
	 * and return the count of a particular n-gram.
	 * If the n-gram is not present, it should return zero.
	 * 
	 * @param ngram the n-gram to consider.
	 * @return the count of the given n-gram.
	 */
	public int getCounts (String ngram);
	
	
	/**
	 * Method incrementing the count of the given n-gram.
	 * If the n-gram has never been seen before, then it sets the counts to 1,
	 * else increments the counts.
	 * The method is used when parsing a corpus file.
	 * 
	 * @param ngram the n-gram whose counts is to increase.
	 */
	public void incCounts (String ngram);
	
	
	
	/**
	 * Setter of the counts of the given n-gram to a given count value.
	 * The method is used when parsing a file containing previously saved n-gram counts.
	 * 
	 * @param ngram the n-gram to consider.
	 * @param counts the counts of the given n-gram.
	 */
	public void setCounts (String ngram, int counts);
	

	/**
	 * Method loading the text given and scanning tit to count all the n-grams of each sentence.
	 * 
	 * @param text the text to consider.
	 * @param maximalOrder the maximal order of n-grams to consider. 
	 */
	public void scanTextString (String text, int maximalOrder);
	
	
	/**
	 * Method loading the text contained in a specified file (corresponding to a training corpus)
	 * and scanning the text to count all the n-grams of each sentence.
	 * 
	 * @param filePath the path of the file corresponding to the training corpus.
	 * @param maximalOrder the maximal order of n-grams to consider. 
	 * @throws IOException 
	 */
	public void scanTextFile (String filePath, int maximalOrder);
	
	
	/**
	 * Method saving the current n-gram counts in a file.
	 * The file should contain one n-gram per line, each line being made of the n-gram and its count 
	 * separated by a tabulation character '\t'.
	 * 
	 * @param filePath the path of the file used to save the counts of the n-grams.
	 */
	public void writeNgramCountFile (String filePath);
	
	
	/**
	 * Method reading the current n-gram counts from a file.
	 * The file should contain one n-gram per line, each line being made of the n-gram and its count 
	 * separated by a tabulation character '\t'.
	 * The method should also set the maximum encountered n-gram length (i.e. the NgramCounts order).
	 * 
	 * @param filePath the path of the file in which the counts of the n-grams are saved.
	 */
	public void readNgramCountsFile (String filePath);
}
