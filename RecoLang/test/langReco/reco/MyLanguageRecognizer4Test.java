/**
 * MyLanguageRecognizer4Test - Junit test for the first languageRecognizer
 * Test a run with trigrams-16000 and need to recognize unknown languages
 * 
 * @version1.0
 *
 * @author Babastienne
 * @date 08/01/2016
 * @notes Results of the test for gold-sent :
 * 		Time of the run  : 184.347
 *  	performance  : 	0.99025
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.eval.Performance;

public class MyLanguageRecognizer4Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "data/gold/gold-sent.txt";
		String goldLang = "data/gold/gold-lang.txt";

		MyLanguageRecognizer4 config = new MyLanguageRecognizer4("lm/fichConfig_trigram.txt");

		String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer4WithTrigram16000";
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
