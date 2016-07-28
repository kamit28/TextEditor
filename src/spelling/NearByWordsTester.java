package spelling;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Amit
 *
 */
public class NearByWordsTester {
	private String dictFile = "data/tester_dict.txt";

	Dictionary dict;
	NearbyWords w;
	String word;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dict = new AutoCompleteDictionaryTrie();

		DictionaryLoader.loadDictionary(dict, dictFile);

		w = new NearbyWords(dict);
		word = "ast";

	}

	/**
	 * Test if the deletions method is working correctly.
	 */
	@Test
	public void testDeletions() {
		List<String> retList = new ArrayList<String>();
		w.deletions(word, retList, true);
		assertTrue(retList.contains("as"));
		assertTrue(retList.contains("at"));
		assertEquals(2, retList.size());

		retList = new ArrayList<String>();
		w.deletions(word, retList, false);
		assertTrue(retList.contains("st"));
		assertTrue(retList.contains("at"));
		assertTrue(retList.contains("as"));
		assertEquals(3, retList.size());
	}

	/**
	 * Test if the insertions method is working correctly.
	 */
	@Test
	public void testInsertions() {
		List<String> retList = new ArrayList<String>();
		w.insertions(word, retList, true);
		assertTrue(retList.contains("fast"));
		assertEquals(1, retList.size());

		String wd = "as";
		retList = new ArrayList<String>();
		w.insertions(wd, retList, false);
		assertTrue(retList.contains("aas"));
		assertTrue(retList.contains("bas"));
		assertTrue(retList.contains("cas"));
		assertTrue(retList.contains("yas"));
		assertTrue(retList.contains("zas"));
		assertTrue(retList.contains("als"));
		assertTrue(retList.contains("ams"));
		assertTrue(retList.contains("ans"));
		assertTrue(retList.contains("aos"));
		assertTrue(retList.contains("ast"));
		assertTrue(retList.contains("asu"));
		assertEquals(76, retList.size());
	}
}
