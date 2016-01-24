package langModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class MyVocabulary: class implementing the interface Vocabulary.
 * 
 * @author ... (2015)
 *
 */
public class MyVocabulary implements Vocabulary {
    
	/**
     * The set of words corresponding to the vocabulary.
     */
    protected Set<String> vocabulary;
    
    /**
     * Constructor.
     */
    public MyVocabulary() {
    	super();
    	this.vocabulary = new HashSet<String>();
    }

    @Override
    public int getSize() {
    	return vocabulary.size();
    }

    @Override
    public Set<String> getWords() {
		Set<String> liste = new HashSet<String>();
		for(String word : vocabulary) liste.add(word);
		return liste;
    }
    
    @Override
    public boolean contains(String word) {
    	return vocabulary.contains(word);
    }

    @Override
    public void addWord(String word) {
    	vocabulary.add(word);
    }

    @Override
    public void removeWord(String word) {
    	vocabulary.remove(word);
    }

    @Override
    public void scanNgramSet(Set<String> ngramSet) {
		for(String ngram : ngramSet) for(int i = 0; i <= ngram.split("\\s+").length-1; i++) if(!vocabulary.contains(ngram.split("\\s+")[i])) addWord(ngram.split("\\s+")[i]);
    }

    @Override
    public void readVocabularyFile(String filePath) {
    	for(String ligne : MiscUtil.readTextFileAsStringList(filePath)) if (!vocabulary.contains(ligne)) addWord(ligne);
    }

    @Override
    public void writeVocabularyFile(String filePath) {
		StringBuffer ligne = new StringBuffer();
		for(String word : vocabulary) ligne.append(word + "\n");
		MiscUtil.writeFile(ligne.toString(), filePath, false);
    }
}
