package langModel;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class MyNgramCountsTest {
//	public Object[] parametersForTestsetMaximalOrder(){
//		return new Object[][]{
//				{2,2},
//				{3,3},
//				{4,4},
//				{5,5},
//				{6,6}
//		};
//	}
//	
//	@Test
//	@Parameters
//	public void testSetMaximalOrder (final int order, final int verif){
//		MyNgramCounts ngram = new MyNgramCounts();
//		ngram.setMaximalOrder(order);
//		assertEquals("test ordre",verif,ngram.order);
//	}
	
	// Prends en compte les doublons
	@Test
	public void testgetTotalWordNumber() {
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.incCounts("la la la");
		ngram.incCounts("li li li");
		ngram.incCounts("lo lo lo");
		ngram.incCounts("lo lo lo");
		System.out.println(ngram.getTotalWordNumber());
		assertEquals(4,ngram.getTotalWordNumber());
	}
	
	@Test
	public void testscantTestString() {
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.scanTextString("la lo li lu", 3); // [la,lo, li, lu, la lo, lo, li, li lu, la lo li, lo li lu]
		MyNgramCounts compareNgram = new MyNgramCounts();
		compareNgram.incCounts("la");
		compareNgram.incCounts("lo");
		compareNgram.incCounts("li");
		compareNgram.incCounts("lu");
		compareNgram.incCounts("la lo");
		compareNgram.incCounts("lo li");
		compareNgram.incCounts("li lu");
		compareNgram.incCounts("la lo li");
		compareNgram.incCounts("lo li lu");
		
		System.out.println(ngram.getNgrams());
		assertEquals(compareNgram.getNgrams(), ngram.getNgrams());
	}
	

	@Test
	public void testSetMaximalOrder (){
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.setMaximalOrder(5);
		assertEquals(5,ngram.order);
	}
	
	//' Nombre de ngrame sans prendre en compte les doublons'
	@Test
	public void testgetNgramCounterSize(){
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.incCounts("la la la");
		ngram.incCounts("li li li");
		ngram.incCounts("lo lo lo");
		ngram.incCounts("lo lo lo");
		assertEquals(3,ngram.getNgramCounterSize());
	}
	
	@Test
	public void testgetNgrams(){
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.incCounts("la la la");
		ngram.incCounts("li li li");
		ngram.incCounts("lo lo lo");
		ngram.incCounts("lo lo lo");
		HashSet<String> liste = new HashSet<String>();
		liste.add("li li li");
		liste.add("la la la");
		liste.add("lo lo lo"); // Car getNgrams ne prend pas en compte les doublons ( dû à la méthode incCounts)
//		System.out.println(ngram.getCounts("la la la"));
		assertEquals(liste,ngram.getNgrams());
	}
	
	@Test
	public void testgetCounts(){
		MyNgramCounts ngram = new MyNgramCounts();
		assertEquals(0,ngram.getCounts("la la la"));
		ngram.incCounts("la la la");
		ngram.incCounts("li li li");
		ngram.incCounts("lo lo lo");
		ngram.incCounts("lo lo lo");
//		System.out.println(ngram.getCounts("la la la"));
		assertEquals(1,ngram.getCounts("la la la"));
		assertEquals(2,ngram.getCounts("lo lo lo"));
	}
	
	@Test
	public void testincCounts(){
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.incCounts("lalala");
		assertEquals(1,ngram.getCounts("lalala"));
		ngram.incCounts("lalala");
		ngram.incCounts("li li li");
		ngram.incCounts("lo lo lo");
		ngram.incCounts("lo lo lo");
		assertEquals(2,ngram.getCounts("lalala"));
	}
	
	@Test 
	public void testsetCounts(){
		MyNgramCounts ngram = new MyNgramCounts();
		ngram.setCounts("lalala",3);
		assertEquals(3,ngram.getCounts("lalala"));
		ngram.setCounts("lalala",10);
		assertEquals(10,ngram.getCounts("lalala"));	
	}

}
