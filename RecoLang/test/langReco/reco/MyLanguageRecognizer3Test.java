/**
 * MyLanguageRecognizer3Test - Junit test for the third languageRecognizer
 * Test a run with unigram 16000
 * 
 * @version1.0
 *
 * @author Bastien POTIRON
 * @date 08/01/2016
 * @notes Results of the test
 */

package langReco.reco;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyLanguageRecognizer3Test {

    @Test
    public void testBaselineLanguageRecognizer() {
	String goldSent = "data/gold/test-sent.txt";
//	String goldLang = "data/gold/micro-lang.txt";

	MyLanguageRecognizer2 config = new MyLanguageRecognizer2("lm/fichConfig_unigram.txt");

	String hypLangFilePath = "tmp/hyphoteseseLanguageRecognizer2WithUnigramTestRun";
	config.recognizeFileLanguage(goldSent, hypLangFilePath);
//	System.out.println("Performance of the run = " + Performance.evaluate(goldLang, hypLangFilePath));
    }


    @Rule
    public TestName name = new TestName();


    @Before
    public void printSeparator()
    {
	System.out.println("\n=========== " + name.getMethodName() + " =====================");
    }
}