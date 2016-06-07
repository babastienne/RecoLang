/**
 * MyLanguageRecognizer3Test - Junit test for the third languageRecognizer
 * Test a run with bigram 16000
 * 
 * @version1.0
 *
 * @author Babastienne
 * @date 08/01/2016
 * @notes Results of the run on gold-sent :
 * 		Time :			100.646 sec
 * 		Performance : 	0.99535	
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyLanguageRecognizer3Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "run/test-sent.txt";

		MyLanguageRecognizer3 config = new MyLanguageRecognizer3("lm/fichConfig_bigram.txt");

		String hypLangFilePath = "run/test-lang-hyp2.txt";
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
