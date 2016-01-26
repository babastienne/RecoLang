/**
 * MyLanguageRecognizer1Test - Junit test for the first languageRecognizer
 * Test a run with bigrams-100
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 07/01/2016
 * @notes Results of the test on gold-sent
 * 		Time for the run : ~ 91.2 sec
 * 		Result of the run : System Performance = 0,98005
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.eval.Performance;

public class MyLanguageRecognizer1Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "data/gold/gold-sent.txt";
		String goldLang = "data/gold/gold-lang.txt";

		MyLanguageRecognizer1 config = new MyLanguageRecognizer1("lm/fichConfig_bigram-100.txt");

		String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer1WithBigram-100";
		config.recognizeFileLanguage(goldSent, hypLangFilePath);
		System.out.println("Performance of the run = " + Performance.evaluate(goldLang, hypLangFilePath));
	}


	@Rule
	public TestName name = new TestName();


	@Before
	public void printSeparator()
	{
		System.out.println("\n=========== " + name.getMethodName() + " =====================");
	}
}