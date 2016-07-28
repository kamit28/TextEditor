package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author Amit
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		List<String> tokens = getTokens("[\\s]+", sourceText);
		if (null != tokens && !tokens.isEmpty()) {
			starter = tokens.get(0);
			wordList.add(new ListNode(starter));
			ListNode previous = wordList.get(0);
			for (int i = 1; i < tokens.size(); i++) {
				// add new word to previous.nextWords list
				String tok = tokens.get(i);
				previous.addNextWord(tok);
				// if the word is new add in wordList
				ListNode node = findNode(tok);
				if (null != node) {
					previous = node;
				} else {
					node = new ListNode(tok);
					wordList.add(node);
					previous = node;
				}
			}
			wordList.get(wordList.size() - 1).addNextWord(starter);
		}
	}

	/**
	 * Returns the tokens that match the regex pattern from the document text
	 * string.
	 * 
	 * @param pattern
	 *            A regular expression string specifying the token pattern
	 *            desired
	 * @return A List of tokens from the document text that match the regex
	 *         pattern
	 */
	private List<String> getTokens(String pattern, String text) {
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);

		while (m.find()) {
			tokens.add(m.group());
		}
		return tokens;
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		String currentWord = starter;
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < numWords; i++) {
			if (i == 0) {
				buf.append(currentWord);
			} else {
				ListNode node = findNode(currentWord);
				if (null != node) {
					currentWord = node.getRandomNextWord(rnGenerator);
					buf.append(" ").append(currentWord);
				}
			}
		}
		return buf.toString();
	}

	private ListNode findNode(String currentWord) {
		for (ListNode ln : wordList) {
			if (ln.getWord().equalsIgnoreCase(currentWord)) {
				return ln;
			}
		}
		return null;
	}

	// Helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		wordList = null;
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}

	/**
	 * This is a minimal set of tests. Note that it can be difficult to test
	 * methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Amit.  Test again.";
		// String textString = "Hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(15));
		String textString2 = "You say yes, I say no, "
				+ "You say stop, and I say go, go, go, "
				+ "Oh no. You say goodbye and I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. "
				+ "I say high, you say low, "
				+ "You say why, and I say I don't know. "
				+ "Oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. "
				+ "Why, why, why, why, why, why, "
				+ "Do you say goodbye. "
				+ "Oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. "
				+ "You say yes, I say no, "
				+ "You say stop and I say go, go, go. "
				+ "Oh, oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/**
 * Links a word to the next words in the list.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		int index = generator.nextInt(nextWords.size());
		return nextWords.get(index);
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
}
