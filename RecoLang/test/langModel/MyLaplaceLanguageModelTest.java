/**
 * 
 */
package langModel;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Bastien
 *
 */
public class MyLaplaceLanguageModelTest {

    /**
     * Test method for {@link langModel.MyLaplaceLanguageModel#getNgramProb(java.lang.String)}.
     */
    @Test
    public void testGetNgramProb() {
	MyLaplaceLanguageModel lmtest = new MyLaplaceLanguageModel();
	MyNgramCounts ngram = new MyNgramCounts();
	ngram.readNgramCountsFile("lm/trigram-train-es.lm");
	lmtest.setNgramCounts(ngram);
	String sentence = lmtest.getNgramProb("<s> la phrase").toString();
	System.out.println(sentence);
	assertEquals("0.0016", sentence);
    }

}
