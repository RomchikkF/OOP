import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StudentGrades {
    // store all grades for each subject
    // only last grade for each subject is important
    private final HashMap<Subject, ArrayList<Grade>> grades;
    public Grade qualificationWorkGrade = null;

    public StudentGrades() {
        this.grades = new HashMap<>();
    }

    public StudentGrades(HashMap<Subject, ArrayList<Grade>> grades) {
        this.grades = grades;
    }

    public void addGrade(Subject task, Grade grade) {
        if (grades.containsKey(task)) {
            grades.get(task).add(grade);
        } else {
            grades.put(task, new ArrayList<>(Collections.singletonList(grade)));
        }
    }

    // returns average student's grade (takes last grade from each subject)
    public float getAverageGrade() {
        int subjCount = grades.size();
        int gradeSum = grades.keySet().stream().reduce(0,
                (a, subj) -> a + getLastGrade(subj).value, Integer::sum);
        if (subjCount == 0) {
            return 0.0f;
        }
        return gradeSum * 1.0f / subjCount;
    }

    public boolean canGetRedDiploma() {
        boolean result =
                grades.keySet().stream().noneMatch(
                        subj -> getLastGrade(subj) == Grade.Unsatisfactory
                ) &&
                grades.keySet().stream().noneMatch(
                        subj -> getLastGrade(subj) == Grade.Satisfactory
                ) &&
                getAverageGrade() >= 4.75f &&
                qualificationWorkGrade == Grade.Great;
        return result;
    }

    public boolean canGetRaisedScholarship() {
        boolean result =
                grades.keySet().stream().noneMatch(
                        subj -> getLastGrade(subj) == Grade.Unsatisfactory
                ) &&
                grades.keySet().stream().noneMatch(
                        subj -> getLastGrade(subj) == Grade.Satisfactory
                ) &&
                getAverageGrade() >= 4.5f;
        return result;
    }

    // returns last student's grade on the subject
    private Grade getLastGrade(Subject subj) {
        var gradeList = grades.get(subj);
        return gradeList.get(gradeList.size() - 1);
    }
}