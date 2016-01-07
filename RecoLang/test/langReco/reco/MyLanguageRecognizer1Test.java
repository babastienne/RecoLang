/**
 * MyLanguageRecognizer1Test - Jubit test for the first languageRecognizer
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 07/01/2016
 * @notes This test was test with different ngram. 
 *  With bigram :	
 * 		Time for the run : ~ 40 minutes
 * 		Result of the run : System Performance = 0,452350
 *  With trigram :
 *  	Time for the run : ~    minutes
 *  	Result of the run : System performance = 
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

		MyLanguageRecognizer1 baseline = new MyLanguageRecognizer1("lm/fichConfig_trigram.txt");

		String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer1WithTrigram";
		baseline.recognizeFileLanguage(goldSent, hypLangFilePath);
		System.out.printf("System performance = %f\n", Performance.evaluate(goldLang, hypLangFilePath));
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=========== " + name.getMethodName() + " =====================");
	}
}