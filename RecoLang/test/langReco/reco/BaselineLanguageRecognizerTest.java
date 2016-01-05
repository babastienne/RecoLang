package langReco.reco;


import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.eval.Performance;
import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;


/**
 * Class BaselineLanguageRecognizerTest: JUnit Test class to evaluate the baseline recognition system.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class BaselineLanguageRecognizerTest {

	@Test
	public void testBaselineLanguageRecognizer() {
		String goldSentPath = "data/gold/gold-sent_sample.txt";
		String goldLangPath = "data/gold/gold-lang_sample.txt";

		List<String> lang = new ArrayList<String>();
		lang.add("fr");
		lang.add("en");
		
		LanguageRecognizer baseline = new BaselineLanguageRecognizer(lang);
		// or use the following if you want to consider all the languages
		// LanguageRecognizer baseline = new BaselineLanguageRecognizer();

		String hypLangFilePath = "/tmp/hyp";
		baseline.recognizeFileLanguage(goldSentPath, hypLangFilePath);
		System.out.printf("System performance = %f\n", Performance.evaluate(goldLangPath, hypLangFilePath));
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
