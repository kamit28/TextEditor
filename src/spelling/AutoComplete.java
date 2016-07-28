/**
 * 
 */
package spelling;

import java.util.List;

/**
 * @author Amit
 *
 */
public interface AutoComplete {
	public List<String> predictCompletions(String prefix, int numCompletions);
}
