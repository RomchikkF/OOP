import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringFinder {

    private int[] prefixFunction(String string) { /* works in O(n) */
        int n = string.length();
        int[] prefixFunction = new int[n];
        prefixFunction[0] = 0;
        for (int i = 1; i < n; ++i) {
            int k = prefixFunction[i - 1];  // potential value for current max prefix length
            while (k > 0 && string.charAt(i) != string.charAt(k)) {
                k = prefixFunction[k - 1];
            }
            if (string.charAt(i) == string.charAt(k)) {
                k++;
            }
            prefixFunction[i] = k;
        }
        return prefixFunction;
    }

    // works in O(n + m) where n and m are lengths of 2 strings
    private ArrayList<Integer> find(String string1, String string2) {
        // split symbol '\n' does not appear in both strings
        String concat = string2 + "\n" + string1;
        int[] prefFunction = prefixFunction(concat);
        ArrayList<Integer> answerList = new ArrayList<>();
        for (int i = 0; i < string1.length(); ++i) {
            if (prefFunction[i + string2.length() + 1] == string2.length()) {
                answerList.add(i - string2.length() + 1);
            }
        }
        return answerList;
    }

    private String toUtf8(String str) {
        byte[] utf8Bytes = str.getBytes();
        return new String(utf8Bytes, StandardCharsets.UTF_8);
    }

    public int[] findInFile(String filePath, String stringToFind) throws IOException {
        stringToFind = toUtf8(stringToFind);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<Integer> answerList = new ArrayList<>();
        int totalLength = 0;
        String line;
        // assuming stringToFind does not have end-line characters
        while((line = reader.readLine()) != null) {
            line = toUtf8(line);
            ArrayList<Integer> result = find(line, stringToFind);
            for (int i = 0; i < result.size(); ++i) {
                result.set(i, result.get(i) + totalLength);
            }
            totalLength += line.length();
            answerList.addAll(result);
        }
        int[] answerArray = new int[answerList.size()];
        for (int i = 0; i < answerArray.length; ++i) {
            answerArray[i] = answerList.get(i);
        }
        return answerArray;
    }
}