package spelling;

import java.util.List;

/**
 * 
 * @author Amit
 *
 */
public interface SpellingSuggest {

	public List<String> suggestions(String word, int numSuggestions);

}
