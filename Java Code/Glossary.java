import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    private static void defAndTerms(SimpleReader in,
            Map<String, String> MappVariables, Set<String> variables) {

        while (!in.atEOS()) {
            String newLine = in.nextLine();
            String defs = "";
            String var = "";
            boolean result = true;
            if (newLine.equals("")) {
                result = false;
            } else {
                var = newLine;
            }
            while (result && !in.atEOS()) {
                newLine = in.nextLine();
                if (!newLine.equals("")) {
                    defs = " " + newLine;
                } else {
                    result = false;
                }
            }
            MappVariables.add(var, defs);
            variables.add(var);
        }
    }

    private static void glossHomeUpper(SimpleWriter out, String outputFile) {

        out.println(
                "<html><head><title>" + "Glossary Index" + "</title></head>");
        out.println("<body><p style=\"font-size:40pt;\">Glossary</p>");
        out.println();
        out.println("<p style=\"font-size:20pt;\">Index</p><ul>");
    }

    private static void glossPages(String w, SimpleWriter out,
            Map<String, String> variableMap, String[] arrayOfTerms,
            String outputFile) {

        SimpleWriter output = new SimpleWriter1L(
                outputFile + "/" + w + ".html");

        output.println("<html><head><title>" + w + "</title></head>");
        output.println(
                "<body><p style=\"color:red;\"><b><i>" + w + "</b></i></p>");
        output.println();
        String defs = variableMap.value(w);
        Set<Character> separatorSet = new Set1L<>();
        String separators = " ,";
        generateElements(separators, separatorSet);
        String page = "";
        int i = 0;
        while (i < defs.length()) {
            String token = nextWordOrSeparator(defs, i, separatorSet);
            if (separatorSet.contains(token.charAt(0))) {
                page = page + token;
            } else {
                int x = 0;
                int z = 0;
                while (x < arrayOfTerms.length) {
                    if (token.equals(arrayOfTerms[x])) {
                        page = page + "<a href=\"" + arrayOfTerms[x]
                                + ".html\">" + token + "</a>";
                        z++;
                    }
                    x++;
                }
                if (z == 0) {
                    page = page + token;
                }
            }
            i = i + token.length();
        }

        output.println("<p style=\"text-align:center;\">" + page + "</p>");
        output.println();
        output.println("Return to <a href=\"index.html\">index</a>");
        output.println("</body>");
        output.println("</html>");
        output.close();
    }

    private static String order(Set<String> variables) {

        Set<String> variables2 = new Set1L<>();
        String result = "";

        while (variables.size() > 0 && result.equals("")) {
            int z = 0;
            String test = variables.removeAny();
            for (String w : variables) {
                if (w.compareTo(test) < 0) {
                    z++;
                }
            }
            if (z == 0) {
                result = test;
            } else {
                variables2.add(test);
            }
        }
        variables.add(variables2);
        return result;
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        int i = str.length();
        while (i > 0) {
            char x = str.charAt(i - 1);
            if (!charSet.contains(x)) {
                charSet.add(x);
            }
            i--;
        }
    }

    /**
     * Returns the first "w" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the w or separator string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first w or separator string found in {@code text} starting at
     *         index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextwOrSeparator =
     *   text[position, position + |nextwOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextwOrSeparator) intersection separators = {}  and
     *   (position + |nextwOrSeparator| = |text|  or
     *    entries(text[position, position + |nextwOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextwOrSeparator) is subset of separators  and
     *   (position + |nextwOrSeparator| = |text|  or
     *    entries(text[position, position + |nextwOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        char ch = text.charAt(position);
        String result = "" + ch;
        int i = position;

        if (separators.contains(ch)) {
            while (i < text.length() && separators.contains(text.charAt(i))) {
                ch = text.charAt(i);
                i++;
            }
        } else {
            while (i < text.length() && !separators.contains(text.charAt(i))) {
                ch = text.charAt(i);
                i++;
            }
        }
        result = text.substring(position, i);
        return result;
    }

    private static void glossHomeLower(SimpleWriter out,
            Sequence<String> variables, Map<String, String> variableMap,
            String[] arrayOfTerms, String outputFile) {

        while (variables.length() > 0) {
            String w = variables.remove(0);
            glossPages(w, out, variableMap, arrayOfTerms, outputFile);
            out.println("<li><a href=\"" + w + ".html\">" + w + "</a></li>");
        }
        out.println("</ul></body></html>");
        out.close();
    }

    private static void sequenceAlph(Sequence<String> listOfVariables,
            Set<String> variables, String[] arrayOfTerms) {

        for (int i = 0; 0 < variables.size(); i++) {
            String nextTerm = order(variables);
            listOfVariables.add(listOfVariables.length(), nextTerm);
            arrayOfTerms[i] = nextTerm;
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Input the file to import: ");
        String fileName = in.nextLine();
        SimpleReader input = new SimpleReader1L(fileName);
        out.print("Provide the output destination: ");
        String outputFile = in.nextLine();

        Map<String, String> MappVariables = new Map1L<>();
        Set<String> variables = new Set1L<>();
        defAndTerms(input, MappVariables, variables);

        Sequence<String> listOfVariables = new Sequence1L<>();
        String[] arrayOfTerms = new String[variables.size()];
        sequenceAlph(listOfVariables, variables, arrayOfTerms);

        SimpleWriter output = new SimpleWriter1L(outputFile + "\\index.html");
        glossHomeUpper(output, outputFile);
        glossHomeLower(output, listOfVariables, MappVariables, arrayOfTerms,
                outputFile);

        in.close();
        out.close();
    }

}