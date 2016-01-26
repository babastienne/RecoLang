/**
 * MyLanguageRecognizer2Test - Junit test for the first languageRecognizer
 * Test a run with unigrams-16000 and need to recognize unknown languages
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes Results of the test for gold-sent
 * 		Time of the run : 31.752
 *  	performance  ~ 0.99545
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyLanguageRecognizer2Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "run/test-sent.txt";

		MyLanguageRecognizer2 config = new MyLanguageRecognizer2("lm/fichConfig_unigram.txt");

		String hypLangFilePath = "run/test-lang-hyp1.txt";
		config.recognizeFileLanguage(goldSent, hypLangFilePath);
	}


	@Rule
	public TestName name = new TestName();


	@Before
	public void printSeparator()
	{
		System.out.println("\n=========== " + name.getMethodName() + " =====================");
	}
}