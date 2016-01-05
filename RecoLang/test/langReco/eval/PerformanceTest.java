package langReco.eval;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langReco.reco.BaselineLanguageRecognizer;
import langReco.reco.LanguageRecognizer;


/**
 * Class PerformanceTest: JUnit Test class to evaluate the performance of a recognition system.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class PerformanceTest {

	@Test
	public void testEvaluateStringString() {
		String goldLangPath = "data/gold/gold-lang_sample.txt";
		Double systemPerf = Performance.evaluate(goldLangPath, goldLangPath);
		
		System.out.printf("System performance = %f\n",systemPerf);
		assertEquals(new Double(1.0), systemPerf);
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
