package spelling;

import java.util.TreeSet;

/**
 * @author Amit
 *
 */
public class DictionaryBST implements Dictionary {
	private TreeSet<String> dict;

	public DictionaryBST() {
		dict = new TreeSet<String>();
	}

	/**
	 * Add this word to the dictionary. Convert it to lowercase first for the
	 * assignment requirements.
	 * 
	 * @param word
	 *            The word to add
	 * @return true if the word was added to the dictionary (it wasn't already
	 *         there).
	 */
	public boolean addWord(String word) {
		if (null != word && !"".equals(word.trim())) {
			return dict.add(word.toLowerCase());
		}
		return false;
	}

	/** Return the number of words in the dictionary */
	public int size() {
		return dict.size();
	}

	/** Is this a word according to this dictionary? */
	public boolean isWord(String s) {
		if (null != s && !"".equals(s.trim())) {
			return dict.contains(s.toLowerCase());
		}
		return false;
	}
}
