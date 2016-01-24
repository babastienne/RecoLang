package langModel;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MyVocabularyTest {

    @Test
    public void testgetSize() {
	MyVocabulary vocab = new MyVocabulary();
	assertEquals(0,vocab.getSize());
	vocab.addWord("lala");
	assertEquals(1,vocab.getSize());
    }

    @Test
    public void testGetWords(){
	MyVocabulary vocab = new MyVocabulary();
	Set<String> liste = new HashSet<String>();
	assertEquals(liste,vocab.getWords());
	vocab.addWord("lala");
	vocab.addWord("lili");
	liste.add("lala");liste.add("lili");
	assertEquals(liste,vocab.getWords());	 
    }

    @Test
    public void testcontains(){ // Addword() testé de la même manière
	MyVocabulary vocab = new MyVocabulary();
	vocab.addWord("lala");
	assertEquals(true,vocab.contains("lala"));
	assertEquals(false,vocab.contains("lili"));
    }

    @Test
    public void testremoveWord(){
	MyVocabulary vocab = new MyVocabulary();
	vocab.addWord("lala");
	vocab.removeWord("lala");
	assertEquals(false,vocab.contains("lala"));
    }

    @Test
    public void testscanNgramSet() {
	MyVocabulary vocabScan = new MyVocabulary();
	MyNgramCounts ngramSet = new MyNgramCounts();
	ngramSet.readNgramCountsFile("data/test/test-result-sentence_myngramcount");
	vocabScan.scanNgramSet(ngramSet.getNgrams());

	MyVocabulary vocabResult = new MyVocabulary();
	vocabResult.readVocabularyFile("data/test/test-result-vocabulary-scan");

	assertEquals(vocabScan.getWords(), vocabResult.getWords());
	assertEquals(vocabScan.getSize(), vocabResult.getSize());
    }

    @Test
    public void testreadVocabularyFile() {
	MyVocabulary vocabWrite = new MyVocabulary();
	vocabWrite.addWord("lala");
	vocabWrite.addWord("lili");
	String filePath = "data/test/test-write-vocabularyFile";
	vocabWrite.writeVocabularyFile(filePath);

	MyVocabulary vocabRead = new MyVocabulary();
	vocabRead.readVocabularyFile(filePath);

	assertEquals(vocabWrite.getWords(), vocabRead.getWords());
	assertEquals(vocabWrite.getSize(), vocabRead.getSize());

    }

}
