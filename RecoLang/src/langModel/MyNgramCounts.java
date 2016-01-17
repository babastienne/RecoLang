package langModel;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class MyNgramCounts: class implementing the interface NgramCounts. 
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
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
	public int getTotalWordNumber(){
	    return this.nbWordsTotal;
	}
	
	
	@Override
	public Set<String> getNgrams() {
		return this.ngramCounts.keySet();
	}

	
	@Override
	public int getCounts(String ngram) {
		if(this.ngramCounts.containsKey(ngram)){
			return this.ngramCounts.get(ngram);
		}
		else {
			return 0;
		}
	}
	

	@Override
	public void incCounts(String ngram) {
		if(this.ngramCounts.containsKey(ngram)){
			setCounts(ngram, this.ngramCounts.get(ngram)+1);
		}
		else{
			setCounts(ngram, 1);
		}			
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
	    this.ngramCounts.put(ngram, counts);
	    this.nbWordsTotal++;
//	    System.out.println(this.ngramCounts.get(ngram));
	}




	
	@Override
	public void scanTextString(String text, int maximalOrder) {
//	    this.nbWordsTotal += NgramUtil.getSequenceSize(text);
		List<String> liste = NgramUtil.generateNgrams(text, 1, maximalOrder);
		for(String ngram : liste)
			this.incCounts(ngram);
		this.setMaximalOrder(maximalOrder);
	}

	
	@Override
	public void scanTextFile(String filePath, int maximalOrder) {
	    
	    List<String> sentences = MiscUtil.readTextFileAsStringList(filePath);
	    for(String ligne : sentences){
	    	this.scanTextString(ligne, maximalOrder);
	    } 
	}

	
	@Override
	public void writeNgramCountFile(String filePath) {
		StringBuffer ligne = new StringBuffer();
		for(String cle : this.ngramCounts.keySet()) {
		    ligne.append(cle);
		    ligne.append("\t");
		    ligne.append(this.ngramCounts.get(cle));
		    ligne.append("\n");
//		    ligne = ligne+ cle + "\t" + this.ngramCounts.get(cle) + "\n";
		}
		MiscUtil.writeFile(ligne.toString(), filePath, false);
	}

	@Override
	public void readNgramCountsFile(String filePath) {
	    List<String> sentences = MiscUtil.readTextFileAsStringList(filePath);
	    for(String ligne : sentences) {
	    	String[] NgramAndCount = ligne.split("\t");
	    	this.setCounts(NgramAndCount[0], Integer.parseInt(NgramAndCount[1]));
	    	if(this.getMaximalOrder() < NgramAndCount[0].split("\\s+").length) {
	    	    this.setMaximalOrder(NgramAndCount[0].split("\\s+").length);
	    	}

	    }
	}

}
