package langReco.reco;


import java.util.List;
import java.util.Random;


/**
 * Class BaselineLanguageRecognizer: a baseline language recognition system that "recognizes" 
 * the language of a sentence by randomly choosing one language recognized by the recognition system.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class BaselineLanguageRecognizer extends LanguageRecognizer {

    public BaselineLanguageRecognizer() {
	super();
    }

    public BaselineLanguageRecognizer(List<String> lang) {
	super();
	getLang().clear();
	getLang().addAll(lang);
    }


    @Override
    public String recognizeSentenceLanguage(String sentence) {	
	Random rand = null;
	rand = new Random(System.currentTimeMillis());
	return lang.get(rand.nextInt(lang.size()));
    }

}
