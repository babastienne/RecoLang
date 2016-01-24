package langModel;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class testMyNaiveLanguageModel {

    @Test
    public void testgetLMOrder() {
	MyNaiveLanguageModel lmtest = new MyLaplaceLanguageModel();
	MyNgramCounts ngram = new MyNgramCounts();
	ngram.readNgramCountsFile("data/test/test-result-sentence_myngramcount");
	lmtest.setNgramCounts(ngram);

	assertEquals(3, lmtest.getLMOrder());
    }

    @Test
    public void testgetVocabularySize() {
	MyNaiveLanguageModel lmtest = new MyLaplaceLanguageModel();
	MyNgramCounts ngram = new MyNgramCounts();
	ngram.readNgramCountsFile("data/test/test-result-sentence_myngramcount");
	lmtest.setNgramCounts(ngram);

	assertEquals(15, lmtest.getVocabularySize());
    }

    @Test
    public void testgetSentenceProb() {
	MyNaiveLanguageModel lmtest = new MyLaplaceLanguageModel();
	MyNgramCounts ngram = new MyNgramCounts();
	ngram.readNgramCountsFile("lm/unigram-train-de.lm");
	lmtest.setNgramCounts(ngram);
	String sentence = lmtest.getSentenceProb("<s> la phrase est la phrase est la phrase </s>").toString();
	assertEquals("4.3619659946368E13", sentence);
    }
}
