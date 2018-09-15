package ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser/generator of INI files.
 *
 * @author Samir Genaim <genaim@gmail.com>
 *
 */
public class Ini {

	/**
	 * A pattern for matching an INI section line
	 */
	private Pattern _section = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");

	/**
	 * A pattern for matching a key-value line
	 */
	private Pattern _keyValue = Pattern.compile("\\s*([^=]*)=(.*)");

	/**
	 * A pattern for matching white spaces
	 */
	private Pattern _whitespaces = Pattern.compile("\\s*");

	/**
	 * A pattern for matching a comment
	 */
	private Pattern _comment = Pattern.compile("[;,#](.*)");

	/**
	 * List of section
	 */
	private List<IniSection> _iniSections = new ArrayList<>();

	/**
	 * Construct an empty INI structure
	 */
	public Ini() {
	}

	/**
	 * Construct an INI structure from an {@link InputStream}
	 * 
	 * @param is
	 *            An input stream from which the INI structure is read
	 * @throws IOException
	 *             Exceptions thrown by the input stream
	 */
	// Usada en cargaEventos del controlador
	public Ini(InputStream is) throws IOException {
		load(is);
	}

	/**
	 * Construct an INI structure from a file
	 * 
	 * @param is
	 *            A filename from which the INI structure is read
	 * @throws IOException
	 *             Exceptions thrown when reading from the input file
	 */
	public Ini(String path) throws IOException {
		this(new FileInputStream(new File(path)));
	}

	/**
	 * Adds sections to the INI structure from an {@link InputStream}
	 * 
	 * @param is
	 *            An input stream from which the INI structure is read
	 * @throws IOException
	 *             Exceptions thrown by the input stream
	 */
	public void load(InputStream is) throws IOException {
		InputStreamReader r = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(r);
		String line;
		IniSection section = null;

		while ((line = br.readLine()) != null) {
			Boolean matched = false;

			if (_comment.matcher(line).matches() || _whitespaces.matcher(line).matches()) {
				matched = true;
			} else {
				Matcher m = _section.matcher(line);
				if (m.matches()) {
					section = new IniSection(m.group(1).trim());
					// if the section name starts with '!' then we ignore it
					// (still the syntax of its key-value elements must be
					// valid)
					if (!section.getTag().startsWith("!"))
						_iniSections.add(section);
					matched = true;
				} else if (section != null) {
					m = _keyValue.matcher(line);
					if (m.matches()) {
						String key = m.group(1).trim();
						String value = m.group(2).trim();
						section.setValue(key, value);
						matched = true;
					}
				}

				if (!matched) {
					throw new IniError("Syntax error: " + line);
				}
			}
		}

	}

	/**
	 * Adds a section to the INI structure
	 * 
	 * @param sec
	 *            A section to be added to the INI structure
	 */
	public void addsection(IniSection sec) {
		_iniSections.add(sec);
	}

	/**
	 * Returns a list of sections in this INI structure
	 * 
	 * @return a list of sections in this INI structure
	 */
	
	public List<IniSection> getSections() {
		return Collections.unmodifiableList(_iniSections);
	}

	/**
	 * Write the INI structure to an {@link OutputStream}
	 * 
	 * @param outStream
	 *            An {@link OutputStream} to which the INI structure should be
	 *            written
	 * @throws IOException
	 *             Exceptions thrown by the output stream
	 */
	public void store(OutputStream outStream) throws IOException {
		for (IniSection sec : _iniSections) {
			sec.store(outStream);
			outStream.write(System.lineSeparator().getBytes());
		}
	}

	/**
	 * Two INI structure are equal if they have the same the same number of
	 * sections, and the i-th section in both are equal. Note that sections are
	 * equal up to key-value reordering.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ini other = (Ini) obj;

		// must have same size
		if (this.getSections().size() != other.getSections().size()) {
			return false;
		}

		for (int i = 0; i < this.getSections().size(); i++) {
			if (!this.getSections().get(i).equals(other.getSections().get(i))) {
				System.out.println("Section: " + i);
				System.out.println("---------------");
				System.out.println("RESULT");
				System.out.println("---------------");
				System.out.println(this.getSections().get(i));
				System.out.println("---------------");
				System.out.println("EXPECTED RESULT");
				System.out.println("---------------");
				System.out.println(other.getSections().get(i));
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		String s = "";
		for (IniSection sec : _iniSections) {
			s += sec + System.lineSeparator();
		}
		return s;
	}
}
