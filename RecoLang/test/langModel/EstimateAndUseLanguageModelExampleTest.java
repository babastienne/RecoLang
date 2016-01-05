package langModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;


/**
 * Class EstimateAndUseLanguageModelExampleTest: JUnit Test class 
 * used as an example for estimating and using language models with the given library.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class EstimateAndUseLanguageModelExampleTest {

	@Test
	public void testBuildAndCompareModels() {
		String frSentence = "<s> la phrase est la phrase est la phrase </s>";
		String enSentence = "<s> the phrase is in the meadow </s>";
				
		String trainFrFilePath = "data/train/sample-fr.txt";
		String trainEnFilePath = "data/train/sample-en.txt";
		
		String lmFrFilePath = "lm/sample-fr_unigrams.lm";
		String lmEnFilePath = "lm/sample-en_unigrams.lm";

		int order = 1;
		
		
		// building a language model of order 1 on French data 
		NgramCounts frNgramCounts = new MyNgramCounts();
		frNgramCounts.scanTextFile(trainFrFilePath, order);
		frNgramCounts.writeNgramCountFile(lmFrFilePath);
		
		// loading a language model of order 1 from a file
		LanguageModel frllm = new MyLaplaceLanguageModel();
		frNgramCounts.readNgramCountsFile(lmFrFilePath);
		frllm.setNgramCounts(frNgramCounts);
		
		
		// building a language model of order 1 on English data 
		NgramCounts enNgramCounts = new MyNgramCounts();
		enNgramCounts.scanTextFile(trainEnFilePath, order);
		enNgramCounts.writeNgramCountFile(lmEnFilePath);
		
		// loading a language model of order 1 from a file
		LanguageModel enllm = new MyLaplaceLanguageModel();
		enNgramCounts.readNgramCountsFile(lmEnFilePath);
		enllm.setNgramCounts(enNgramCounts);
		
		
		// computing and outputing the recognized language of the French sentence
		System.out.println("Sentence: " + frSentence);
		
		if (frllm.getSentenceProb(frSentence) > enllm.getSentenceProb(frSentence)) 
			System.out.println("Vote: French"); 
		else 
			System.out.println("Vote: English");
		
		System.out.println("Details: The llm_fr score is " + frllm.getSentenceProb(frSentence)
				+" and the llm_en score is " + enllm.getSentenceProb(frSentence));
		System.out.println();
		
		
		// computing and outputing the recognized language of the English sentence
		System.out.println("Sentence: " + enSentence);
		
		if (frllm.getSentenceProb(enSentence) > enllm.getSentenceProb(enSentence)) 
			System.out.println("Vote: French"); 
		else 
			System.out.println("Vote: English");
		
		System.out.println("Details: The llm_fr score is " + frllm.getSentenceProb(enSentence)
				+" and the llm_en score is " + enllm.getSentenceProb(enSentence));
	}


	@Rule
	public TestName name = new TestName();

	
	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}
}
