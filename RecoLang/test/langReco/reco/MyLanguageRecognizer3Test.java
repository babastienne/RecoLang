/**
 * MyLanguageRecognizer3Test - Jubit test for the first languageRecognizer
 * Test a run with trigrams-16000
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes Results of the test
 *  	Time for the run : Too long (~ 1 minute for 1 sentence)
 *  	Result of the run : For 30 sentences = 4~5 errors
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.eval.Performance;

public class MyLanguageRecognizer3Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "data/gold/gold-sent.txt";
		String goldLang = "data/gold/gold-lang.txt";

		MyLanguageRecognizer3 config = new MyLanguageRecognizer3("lm/fichConfig_trigram.txt");

		String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer3WithTrigram";
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