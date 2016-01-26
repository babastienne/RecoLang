package langModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class MyNgramCounts: class implementing the interface NgramCounts. 
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 */
public class MyNgramCounts implements NgramCounts {

	/**
	 * The maximal order of the n-gram counts.
	 */
	protected int order;

	/**
	 * The map containing the counts of each n-gram.
	 */
	protected Map<String,Integer> ngramCounts;

	/**
	 * The total number of words in the corpus.
	 * In practice, the total number of words will be increased when parsing a corpus 
	 * or when parsing a NgramCounts file (only if the ngram encountered is a unigram one).
	 */
	protected int nbWordsTotal;	

	/**
	 * Constructor.
	 */
	public MyNgramCounts(){
		this.ngramCounts = new HashMap<String, Integer>();
		this.nbWordsTotal = 0;
		this.order= 0;
	}

	/**
	 * Setter of the maximal order of the ngrams considered.
	 * 
	 * In practice, the method will be called when parsing the training corpus, 
	 * or when parsing the NgramCounts file (using the maximal n-gram length encountered).
	 * 
	 * @param order the maximal order of n-grams considered.
	 */
	protected void setMaximalOrder (int order) {
		this.order = order;
	}

	@Override
	public int getMaximalOrder() {
		return this.order;
	}

	@Override
	public int getNgramCounterSize() {
		return this.ngramCounts.size();		
	}

	@Override
	public int getTotalWordNumber() {
		return this.nbWordsTotal;
	}	

	@Override
	public Set<String> getNgrams() {
		return this.ngramCounts.keySet();
	}

	@Override
	public int getCounts(String ngram) {
		return(this.ngramCounts.containsKey(ngram)) ? this.ngramCounts.get(ngram) : 1;
	}	

	@Override
	public void incCounts(String ngram) {
		if(this.ngramCounts.containsKey(ngram)) setCounts(ngram, this.ngramCounts.get(ngram)+1);
		else setCounts(ngram, 1);	
	}

	@Override
	public void setCounts(String ngram, int counts) {
		this.ngramCounts.put(ngram, counts);
		this.nbWordsTotal++;
	}

	@Override
	public void scanTextString(String text, int maximalOrder) {
		for(String ngram : NgramUtil.generateNgrams(text, 1, maximalOrder)) this.incCounts(ngram);
		this.setMaximalOrder(maximalOrder);
	}

	@Override
	public void scanTextFile(String filePath, int maximalOrder) {
		for(String ligne : MiscUtil.readTextFileAsStringList(filePath)) this.scanTextString(ligne, maximalOrder);
	}

	@Override
	public void writeNgramCountFile(String filePath) {
		StringBuffer ligne = new StringBuffer();
		for(String cle : this.ngramCounts.keySet()) ligne = ligne.append(cle + "\t" + this.ngramCounts.get(cle) + "\n");
		MiscUtil.writeFile(ligne.toString(), filePath, false);
	}

	@Override
	public void readNgramCountsFile(String filePath) {
		for(String ligne : MiscUtil.readTextFileAsStringList(filePath)) {
			this.setCounts(ligne.split("\t")[0], Integer.parseInt(ligne.split("\t")[1]));
			if(this.getMaximalOrder() < ligne.split("\t")[0].split("\\s+").length) this.setMaximalOrder(ligne.split("\t")[0].split("\\s+").length);
		}
	}
}
