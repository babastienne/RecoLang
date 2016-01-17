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
    public MyVocabulary(){
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
	Iterator<String> iter = vocabulary.iterator();
	while (iter.hasNext()) {
	    liste.add(iter.next());
	}
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
	Iterator<String> iter = ngramSet.iterator();
	String[] wArray;
	while (iter.hasNext()) {
	    wArray = iter.next().split("\\s+");
	    for(int i = 0; i <= wArray.length-1; i++) {
		if (!vocabulary.contains(wArray[i])){
		    addWord(wArray[i]);
		}
	    }
	}
    }

    @Override
    public void readVocabularyFile(String filePath) {
	List<String> sentences = MiscUtil.readTextFileAsStringList(filePath);
	for(String ligne : sentences){
	    if (!vocabulary.contains(ligne)){
		addWord(ligne);
	    }
	} 		
    }

    @Override
    public void writeVocabularyFile(String filePath) {
	StringBuffer ligne = new StringBuffer();
	Iterator<String> iter = vocabulary.iterator();
	while (iter.hasNext()) {
	    ligne.append(iter.next());
	    ligne.append("\n");
	}
	MiscUtil.writeFile(ligne.toString(), filePath, false);

    }

}
