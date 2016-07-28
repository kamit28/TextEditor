package document;

import java.util.List;

/**
 * A naive implementation of the Document abstract class.
 * 
 * @author Amit
 */
public class BasicDocument extends Document {
	/**
	 * Create a new BasicDocument object
	 * 
	 * @param text
	 *            The full text of the Document.
	 */
	public BasicDocument(String text) {
		super(text);
	}

	/**
	 * Get the number of words in the document. "Words" are defined as
	 * contiguous strings of alphabetic characters i.e. any upper or lower case
	 * characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords() {
		// String pattern = "[^ ,\\:\\(\\)\\?!\\.0-9]+";
		List<String> words = getTokens("[a-zA-Z]+");
		return words.size();
	}

	/**
	 * Get the number of sentences in the document. Sentences are defined as
	 * contiguous strings of characters ending in an end of sentence punctuation
	 * (. ! or ?) or the last contiguous set of characters in the document, even
	 * if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences() {
		String pattern = "[^\\?\\.!]+";
		List<String> sentences = getTokens(pattern);
		return sentences.size();
	}

	/**
	 * Get the number of syllables in the document. Words are defined as above.
	 * Syllables are defined as: a contiguous sequence of vowels, except for a
	 * lone "e" at the end of a word if the word has another set of contiguous
	 * vowels, makes up one syllable. y is considered a vowel.
	 * 
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables() {
		List<String> tokens = getTokens("[a-zA-Z]+");
		int totalSyllables = 0;
		for (String word : tokens) {
			totalSyllables += countSyllables(word);
		}
		return totalSyllables;

		/*
		 * List<String> words = getTokens("[^a-zA-Z]+"); int syllCount = 0;
		 * 
		 * for (String word : words) { int currSyllCount = 0; boolean prevVowel
		 * = false; for (int i = 0; i < word.length(); i++) { if
		 * (isVowel(word.charAt(i))) { if (prevVowel) continue; else { if (i + 1
		 * == word.length() && word.charAt(i) == 'e' && currSyllCount != 0)
		 * continue; else currSyllCount++; prevVowel = true; } } else {
		 * prevVowel = false; } } syllCount += currSyllCount; } return
		 * syllCount;
		 */
	}

	@SuppressWarnings("unused")
	private boolean isVowel(char ch) {
		return Character.toLowerCase(ch) == 'a'
				|| Character.toLowerCase(ch) == 'e'
				|| Character.toLowerCase(ch) == 'i'
				|| Character.toLowerCase(ch) == 'o'
				|| Character.toLowerCase(ch) == 'u'
				|| Character.toLowerCase(ch) == 'y';
	}

	/*
	 * The main method for testing this class. You are encouraged to add your
	 * own tests.
	 */
	public static void main(String[] args) {
		testCase(new BasicDocument("This is a test.  How many???  "
				+ "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);

		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
				+ "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(
				new BasicDocument(
						"Here is a series of test sentences. Your program should "
								+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
								+ "the correct amount of syllables (example, for example), "
								+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue"), 2, 1, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(
				new BasicDocument(
						"Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
				32, 15, 1);

	}
}
