/**
 * 
 */
package langModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyLaplaceLanguageModelTest {

    /**
     * Test method for {@link langModel.MyLaplaceLanguageModel#getNgramProb(java.lang.String)}.
     */
    @Test
    public void testGetNgramProb() {
	MyLaplaceLanguageModel lmtest = new MyLaplaceLanguageModel();
	MyNgramCounts ngram = new MyNgramCounts();
	ngram.readNgramCountsFile("data/test/test-result-sentence_myngramcount");
	lmtest.setNgramCounts(ngram);
	String sentence = lmtest.getNgramProb("<s> la phrase est la phrase").toString();
	System.out.println(sentence);
	assertEquals("15.133333333333333", sentence);
    }

}
