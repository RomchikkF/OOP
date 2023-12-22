import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private final LocalDateTime time;
    private final String name;
    private final String text;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm");

    public Note(String name, String text){
        time = LocalDateTime.now();
        this.name = name;
        this.text = text;
    }

    @JsonCreator
    public Note(@JsonProperty("time") String time,
                @JsonProperty("name") String name,
                @JsonProperty("text") String text) {
        this.time = LocalDateTime.parse(time);
        this.name = name;
        this.text = text;
    }

    @JsonGetter
    public String time() {
        return time.toString(); // saves time precisely
    }

    public boolean isInInterval(LocalDateTime timeL, LocalDateTime timeR) {
        boolean notBeforeL = time.isAfter(timeL) || time.isEqual(timeL);
        boolean notAfterR = time.isBefore(timeR) || time.isEqual(timeR);
        return notBeforeL && notAfterR;
    }

    public boolean containsKeyword(String keyword){
        return name.toLowerCase().contains(keyword.toLowerCase());
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String toString(){
        return time.format(format) + "\n" + name + "\n" + text;
    }
}