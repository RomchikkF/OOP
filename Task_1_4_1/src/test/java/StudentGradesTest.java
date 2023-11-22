import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentGradesTest {

    Subject subj0 = new Subject("OOP");
    Subject subj1 = new Subject("Math");
    Subject subj2 = new Subject("History");
    Subject subj3 = new Subject("PE");
    Subject subj4 = new Subject("OS");

    StudentGrades getStudent0() {
        return new StudentGrades();
    }

    StudentGrades getStudent1() {
        StudentGrades student = new StudentGrades();
        student.addGrade(subj0, Grade.Great);
        student.addGrade(subj1, Grade.Good);
        return student;
    }


    StudentGrades getStudent2() {
        StudentGrades student = new StudentGrades();
        student.addGrade(subj0, Grade.Unsatisfactory);
        student.addGrade(subj0, Grade.Satisfactory);
        student.addGrade(subj0, Grade.Good);
        student.addGrade(subj0, Grade.Great);
        student.addGrade(subj1, Grade.Satisfactory);
        student.addGrade(subj1, Grade.Great);
        student.addGrade(subj2, Grade.Satisfactory);
        student.addGrade(subj2, Grade.Great);
        student.addGrade(subj3, Grade.Satisfactory);
        student.addGrade(subj3, Grade.Great);
        student.addGrade(subj4, Grade.Satisfactory);
        return student;
    }


    StudentGrades getStudent3() {
        StudentGrades student = new StudentGrades();
        student.addGrade(subj0, Grade.Great);
        student.addGrade(subj1, Grade.Good);
        student.addGrade(subj1, Grade.Great);
        student.addGrade(subj2, Grade.Great);
        student.addGrade(subj3, Grade.Good);
        student.qualificationTaskGrade = Grade.Great;
        return student;
    }

    @Test
    void testAvg0() {
        StudentGrades student = getStudent0();
        assertEquals(0.0f, student.getAverageGrade());
    }
    @Test
    void testAvg1() {
        StudentGrades student = getStudent1();
        assertEquals(4.5f, student.getAverageGrade());
    }
    @Test
    void testAvg2() {
        StudentGrades student = getStudent2();
        assertEquals(4.6f, student.getAverageGrade());
    }
    @Test
    void testAvg3() {
        StudentGrades student = getStudent3();
        assertEquals(4.75f, student.getAverageGrade());
    }

    @Test
    void testRed0() {
        StudentGrades student = getStudent0();
        assertFalse(student.canGetRedDiploma());
    }
    @Test
    void testRed1() {
        StudentGrades student = getStudent1();
        assertFalse(student.canGetRedDiploma());
    }
    @Test
    void testRed2() {
        StudentGrades student = getStudent2();
        assertFalse(student.canGetRedDiploma());
    }
    @Test
    void testRed3() {
        StudentGrades student = getStudent3();
        assertTrue(student.canGetRedDiploma());
    }

    @Test
    void testScholarship0() {
        StudentGrades student = getStudent0();
        assertFalse(student.canGetRaisedScholarship());
    }
    @Test
    void testScholarship1() {
        StudentGrades student = getStudent1();
        assertTrue(student.canGetRaisedScholarship());
    }
    @Test
    void testScholarship2() {
        StudentGrades student = getStudent2();
        assertFalse(student.canGetRaisedScholarship());
    }
    @Test
    void testScholarship3() {
        StudentGrades student = getStudent3();
        assertTrue(student.canGetRaisedScholarship());
    }
}