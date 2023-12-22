import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class NotebookTest {

    @Test
    void createTest() {
        try {
            new Notebook("notebook.json");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void clearTest0() {
        try {
            Notebook notebook = new Notebook("notebook.json");
            notebook.clear();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void addTest0() {
        try {
            Notebook notebook = new Notebook("notebook.json");
            notebook.clear();
            notebook.add("1", "hello,");
            notebook.add("2", "world!");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void removeTest0() {
        try {
            Notebook notebook = new Notebook("notebook.json");
            notebook.clear();
            notebook.add("1", "hello,");
            notebook.remove("1");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getTest0() {
        try {
            Notebook notebook = new Notebook("notebook.json");
            notebook.clear();
            LocalDateTime time0 = LocalDateTime.now();
            notebook.add("note 1", "hello,");
            notebook.add("note 2", "world!");
            LocalDateTime time1 = LocalDateTime.now();
            List<Note> notes0 = notebook.get(time0, time1, new String[]{"1"});
            assertEquals(1, notes0.size());
            assertEquals("hello,", notes0.get(0).getText());
        } catch (IOException e) {
            fail();
        }
    }

    String[] testMain(String[][] commands) throws ParseException, IOException {
        File file = new File("notebook.json");
        file.delete();
        PrintStream defaultOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        List<String> timeBefore = new ArrayList<>();
        List<String> timeAfter = new ArrayList<>();
        for (String[] args : commands) {
            if (args[0].equals("-add")){
                timeBefore.add(LocalDateTime.now().toString());
            }
            if (args[0].equals("-show") && args.length == 4) {
                args[1] = timeBefore.get(Integer.parseInt(args[1]));
                args[2] = timeAfter.get(Integer.parseInt(args[2]));
            }
            Main.main(args);
            if (args[0].equals("-add")){
                timeAfter.add(LocalDateTime.now().toString());
            }
        }
        System.setOut(defaultOut);
        return out.toString().replace("\r", "").split("\n");
    }

    @Test
    void test0() {
        try {
            String name0 = "Note 1";
            String text0 = "First note text";
            String[] result = testMain(new String[][]{
                    new String[]{"-add", name0, text0},
                    new String[]{"-show"},
            });
            assertEquals(3, result.length);
            String resName = result[1];
            String resText = result[2];
            assertEquals(name0, resName);
            assertEquals(text0, resText);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test1() {
        try {
            String name0 = "Note 1";
            String text0 = "First text";
            String name1 = "nOtE tWo";
            String text1 = "Second text";
            String name2 = "secret";
            String text2 = "VeRy sEcReT tExT";
            String[] result = testMain(new String[][]{
                    new String[]{"-add", name0, text0},
                    new String[]{"-add", name1, text1},
                    new String[]{"-add", name2, text2},
                    new String[]{"-show", "0", "2", "note"} // numbers will be parsed to time of those notes
            });
            assertEquals(6, result.length);
            String resText0 = result[2];
            assertEquals(text0, resText0);
            String resText1 = result[5];
            assertEquals(text1, resText1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test2() {
        try {
            String name0 = "Note 1";
            String text0 = "First text";
            String name1 = "nOtE tWo";
            String text1 = "Second text";
            String name2 = "secret";
            String text2 = "VeRy sEcReT tExT";
            String name3 = "NOTE 3";
            String text3 = "this will be removed";
            String[] result = testMain(new String[][]{
                    new String[]{"-add", name0, text0},
                    new String[]{"-add", name3, text3},
                    new String[]{"-add", name1, text1},
                    new String[]{"-add", name2, text2},
                    new String[]{"-rm", name3},
                    new String[]{"-show", "0", "1", "note"} // numbers will be parsed to time of those notes
            });
            assertEquals(3, result.length);
            String resText0 = result[2];
            assertEquals(text0, resText0);
        } catch (Exception e) {
            fail();
        }
    }
}