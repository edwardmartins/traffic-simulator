package ini;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IniSection {

	/**
	 * The section's tag
	 */
	private String _tag;

	/**
	 * Key-Value mapping
	 */
	private Map<String, String> _attr;

	/**
	 * The list of keys. We could get it from field {@code _attr}, but keep
	 * another list of keys in the order in which they where added. This is
	 * mainly used when writing the INI structure.
	 */
	private List<String> _keys;

	/**
	 * Key-comment mapping
	 */
	private Map<String, List<String>> _comments;

	/**
	 * Creates and INI section
	 * 
	 * @param tag
	 *            The tag of the INI section
	 */
	public IniSection(String tag) {
		_tag = tag;
		_attr = new HashMap<>();
		_keys = new LinkedList<>();
		_comments = new HashMap<>();
		_comments.put("", new ArrayList<>()); // section comments
	}

	private void checkKeyValidity(String key) {
		if (key.isEmpty()) {
			throw new IniError("Invalid key: " + key);
		}
	}

	/**
	 * 
	 * @return The tag of the INI section
	 */
	public String getTag() {
		return _tag;
	}

	/**
	 * Sets a key-value in the INI section. If the key exists its value is
	 * overwritten.
	 * 
	 * @param key
	 *            The key
	 * @param value
	 *            The value
	 */
	public void setValue(String key, Object value) {
		checkKeyValidity(key);
		if (getValue(key) == null) {
			_keys.add(key);
			_comments.put(key, new ArrayList<>()); // key comments
		}
		_attr.put(key, value.toString());
	}

	/**
	 * Adds a comment to be printed just before the line corresponding to key.
	 * 
	 * @param key
	 * @param comment
	 */
	public void addKeyComment(String key, String comment) {
		if (getValue(key) != null) {
			_comments.get(key).add(comment);
		}
	}

	/**
	 * Adds a comment to be printed just before the the section
	 * 
	 * @param key
	 * @param comment
	 */
	public void addSectionComment(String comment) {
		_comments.get("").add(comment);
	}

	/**
	 * Returns the value corresponding to a given key
	 * 
	 * @param key
	 *            key
	 * @return Value for key
	 */
	public String getValue(String key) {
		return _attr.get(key);
	}

	public List<String> getKeyComments(String key) {
		checkKeyValidity(key);
		return _comments.get(key);
	}

	/**
	 * Write the section into an {@link OutputStream}
	 * 
	 * @param out
	 *            An output stream to which the section to be wirtten
	 * @throws IOException
	 */
	public void store(OutputStream out) throws IOException {
		out.write(toString().getBytes());
	}

	/**
	 * Returns the key-value map of this section.
	 * 
	 * @return the key-value map of this section.
	 */
	public Map<String, String> getKeysMap() {
		return Collections.unmodifiableMap(_attr);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getKeys() {
		return Collections.unmodifiableList(_keys);
	}

	/**
	 * To be equal they must have the same keys, the order is not important, and
	 * value of all keys are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IniSection other = (IniSection) obj;

		// we require to have the same number of keys, we don't force it to be
		// in the same order
		if (this.getKeys().size() != other.getKeys().size()) {
			return false;
		}

		for (String key : this.getKeys()) {
			if (!this.getValue(key).equals(other.getValue(key))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		String s = "";

		// section comments
		for (String c : _comments.get("")) {
			s += ";" + c + System.lineSeparator();
		}

		s += "[" + _tag + "]" + System.lineSeparator();
		for (String key : _keys) {

			// key comments
			for (String c : _comments.get(key)) {
				s += ";" + c + System.lineSeparator();
				;
			}

			s += key + " = " + _attr.get(key) + System.lineSeparator();
		}
		return s;
	}

}
