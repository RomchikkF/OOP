import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringFinder {

    private final int bufferSize;

    public StringFinder(){
        bufferSize = 4096; // uses default buffer size
    }

    public StringFinder(int bufSize){
        bufferSize = bufSize;
    }

    public int[] findInFile(String filePath, String stringToFind) throws IOException {
        stringToFind = toUtf8(stringToFind);
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath));
        ArrayList<Integer> answerList = new ArrayList<>();
        int lengthBehind = 0;
        int oldLen = 0;
        byte[] oldBytes = new byte[0];
        int len;
        byte[] bytes = new byte[bufferSize];
        boolean fileEnded = false;
        while((len = stream.read(bytes)) != -1) {
            byte[] concatBytes = new byte[oldLen + len];
            System.arraycopy(oldBytes, 0, concatBytes, 0, oldLen);
            System.arraycopy(bytes, 0, concatBytes, oldLen, len);
            String line = new String(concatBytes, StandardCharsets.UTF_8);
            ArrayList<Integer> result = find(line, stringToFind);
            for (int position : result) {
                int newAns = position + lengthBehind;
                // checking if newAns not present in current answer list
                if (answerList.isEmpty() || newAns > answerList.get(answerList.size() - 1)) {
                    answerList.add(newAns);
                }
            }
            lengthBehind += (new String(oldBytes, StandardCharsets.UTF_8)).length();
            oldBytes = bytes.clone();
            oldLen = len;
        }
        int[] answerArray = new int[answerList.size()];
        for (int i = 0; i < answerArray.length; ++i) {
            answerArray[i] = answerList.get(i);
        }
        return answerArray;
    }

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
        String concat = string2 + "\0" + string1;
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
}