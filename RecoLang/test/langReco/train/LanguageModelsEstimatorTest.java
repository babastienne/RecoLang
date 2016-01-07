package langReco.train;


import static org.junit.Assert.*;
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
 * @author hernandez-n
 *
 */
public class LanguageModelsEstimatorTest {

	/**
	 * Duplicate this method as many times as you want to create a language model.
	 * Give it an explicit name with respect to the created language model e.g. testCreateLmWordLangEnOrder3
	 * to create a language model with words from texts in English and order 3.
	 */
	@Test
	public void testCreateLmWordLangDeOrder3() {
		String trainDeFilePath = "data/train/train-de.txt";
		
		String lmDeFilePath = "lm/trigram-train-de.lm";

		int order = 3;
		
		// building a language model of order 3 on De data 
		NgramCounts DeNgramCounts = new MyNgramCounts();
		DeNgramCounts.scanTextFile(trainDeFilePath, order);
		DeNgramCounts.writeNgramCountFile(lmDeFilePath);		
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}

}
