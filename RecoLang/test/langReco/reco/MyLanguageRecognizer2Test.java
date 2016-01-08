/**
 * MyLanguageRecognizer2Test - Junit test for the first languageRecognizer
 * Test a run with trigrams-16000 and need to recognize unknown languages
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes Results of the test
 *  	...
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.eval.Performance;

public class MyLanguageRecognizer2Test {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSent = "data/gold/gold-sent.txt";
		String goldLang = "data/gold/gold-lang.txt";

		MyLanguageRecognizer2 config = new MyLanguageRecognizer2("lm/fichConfig_trigram.txt");

		String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer2WithTrigramAnd UnknownLanguages";
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