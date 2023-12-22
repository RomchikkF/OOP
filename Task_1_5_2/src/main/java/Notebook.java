import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Notebook {

    ObjectMapper objectMapper = new ObjectMapper();
    String filename;

    /** Init new notebook that stores notes in json file with given name */
    public Notebook(String filename) throws IOException {
        this.filename = filename;
        File file = new File(filename);
        if (file.createNewFile()) {
            List<Note> empty = new ArrayList<>();
            objectMapper.writeValue(file, empty);
        }
    }

    /** Clear file */
    public void clear() throws IOException {
        File file = new File(filename);
        List<Note> empty = new ArrayList<>();
        objectMapper.writeValue(file, empty);
    }

    /** Add new note to file
     * if note with the same name already exists nothing happens. */
    public void add(String name, String text) throws IOException {
        Note newNote = new Note(name, text);
        File file = new File(filename);
        List<Note> notes = objectMapper.readValue(file, new TypeReference<List<Note>>(){});
        for (Note note : notes) {
            if (note.getName().equals(name)) {
                return;
            }
        }
        notes.add(newNote);
        objectMapper.writeValue(file, notes);
    }

    /** Removes note with given name. If there are no such note nothing happens */
    public void remove(String name) throws IOException {
        File file = new File(filename);
        List<Note> notes = objectMapper.readValue(file, new TypeReference<List<Note>>(){});
        notes = notes.stream().filter(note -> !note.getName().equals(name)).toList();
        objectMapper.writeValue(file, notes);
    }

    /** Writes all notes */
    public void show() throws IOException {
        List<Note> notes = get();
        for (Note note : notes){
            System.out.println(note);
        }
    }

    /** Writes all notes that were created from timeL to timeR
     * and have at least one keyword in a name. */
    public void show(LocalDateTime timeL, LocalDateTime timeR, String[] keywords) throws IOException {
        List<Note> notes = get(timeL, timeR, keywords);
        for (Note note : notes) {
            System.out.println(note);
        }
    }

    /** Returns list of all notes */
    public List<Note> get() throws IOException {
        File file = new File(filename);
        return objectMapper.readValue(file, new TypeReference<List<Note>>() {});
    }

    /** Returns list of all notes that were created from timeL to timeR
     * and have at least one keyword in a name. */
    public List<Note> get(LocalDateTime timeL, LocalDateTime timeR, String[] keywords) throws IOException {
        File file = new File(filename);
        List<Note> notes = objectMapper.readValue(file, new TypeReference<List<Note>>() {});
        return notes.stream().filter(note -> noteCheck(note, timeL, timeR, keywords)).toList();
    }

    private boolean noteCheck(Note note, LocalDateTime timeL, LocalDateTime timeR, String[] keywords) {
        if (!note.isInInterval(timeL, timeR)) {
            return false;
        }
        for (String keyword : keywords) {
            if (note.containsKeyword(keyword)) {
                return true;
            }
        }
        return false;
    }
}