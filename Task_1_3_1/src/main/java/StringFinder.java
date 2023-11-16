import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringFinder {

    private int[] PrefixFunction(String string){ /* works in O(n) */
        int n = string.length();
        int[] prefixFunction = new int[n];
        prefixFunction[0] = 0;
        for (int i = 1; i < n; ++i){
            int k = prefixFunction[i - 1];  // potential value for current max prefix length
            while (k > 0 && string.charAt(i) != string.charAt(k)){
                k = prefixFunction[k - 1];
            }
            if (string.charAt(i) == string.charAt(k)){
                k++;
            }
            prefixFunction[i] = k;
        }
        return prefixFunction;
    }

    // works in O(n + m) where n and m are lengths of 2 strings
    private ArrayList<Integer> Find(String string1 /* text */, String string2 /* string to find in text */) {
        String concat = string2 + "\n" + string1; // split symbol '\n' does not appear in both strings
        int[] prefFunction = PrefixFunction(concat);
        ArrayList<Integer> answerList = new ArrayList<>();
        for (int i = 0; i < string1.length(); ++i) {
            if (prefFunction[i + string2.length() + 1] == string2.length()) {
                answerList.add(i - string2.length() + 1);
            }
        }
        return answerList;
    }

    private String ToUTF8(String str){
        byte[] utf8Bytes = str.getBytes();
        String utf8str = new String(utf8Bytes, StandardCharsets.UTF_8);
        return utf8str;
    }

    public int[] FindInFile(String filePath, String stringToFind) throws IOException {
        stringToFind = ToUTF8(stringToFind);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<Integer> answerList = new ArrayList<>();
        int total_length = 0;
        String line;
        // assuming stringToFind does not have end-line characters
        while((line = reader.readLine()) != null) {
            line = ToUTF8(line);
            ArrayList<Integer> result = Find(line, stringToFind);
            for (int i = 0; i < result.size(); ++i){
                result.set(i, result.get(i) + total_length);
            }
            total_length += line.length();
            answerList.addAll(result);
        }
        int[] answerArray = new int[answerList.size()];
        for (int i = 0; i < answerArray.length; ++i){
            answerArray[i] = answerList.get(i);
        }
        return answerArray;
    }
}