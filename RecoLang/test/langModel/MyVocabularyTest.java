package langModel;

import static org.junit.Assert.*;

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
	
	 
}
