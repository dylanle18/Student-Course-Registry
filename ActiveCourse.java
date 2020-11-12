import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ActiveCourse extends Course {
    private ArrayList<Student> students;
    private String semester;
    private String lectureDay;
    private int lectureStart;
    private int lectureDuration;

    /**
     * initializes the active course object
     * 
     * @param name     the name of the course
     * @param code     the course code
     * @param descr    the description of the course
     * @param fmt      the amount of lec and lab in the course
     * @param semester the course semester
     * @param students a list students in the course
     */
    public ActiveCourse(String name, String code, String descr, String fmt, String semester,
            ArrayList<Student> students) {
        super(name, code, descr, fmt);
        this.semester = semester;
        this.students = new ArrayList<>(students);
        lectureDay = "";
        lectureStart = 0;
        lectureDuration = 0;
    }

    public String getLectureDay() {
        return lectureDay;
    }

    public void setLectureDay(String day) {
        lectureDay = day;
    }

    public int getLectureStart() {
        return lectureStart;
    }

    public void setLectureStart(int startTime) {
        lectureStart = startTime;
    }

    public int getLectureDuration() {
        return lectureDuration;
    }

    public void setLectureDuration(int duration) {
        lectureDuration = duration;
    }

    /**
     * gets the student list
     * 
     * @return the array lis of students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * used to add a student to the active course
     * 
     * @param student the student thats going to be added
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * remvoes a student from the active course using studentID
     * 
     * @param studentId the ID of the student
     */
    public void removeStudent(String studentId) {
        // find the student in array list of students
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(studentId)) {
                students.remove(i);
            }
        }
    }

    /**
     * gets the semester
     * 
     * @return the semester of the course
     */
    public String getSemester() {
        return semester;
    }

    /**
     * prints all the students in this course
     */
    public void printClassList() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    /**
     * prints all students and their grades in this course
     */
    public void printGrades() {
        for (Student s : students) {
            System.out.println(s.getId() + " " + s.getName() + " " + getGrade(s.getId()));
        }
    }

    /**
     * find the grade of the student using the studentID
     * 
     * @param studentId the id of the student
     * @return a double of the grade of the student if student not found returns 0
     */
    public double getGrade(String studentId) {
        Student student = findStudent(studentId, students);

        // if the student was found look for the credit course
        if (student != null) {
            CreditCourse course = findCreditCourse(this.getCode(), student.getCourses());
            // if course found return it
            if (course != null) {
                return course.getGrade();
            }
        }
        return 0;
    }

    /**
     * gets the description of the active course with the semester and number of
     * students enrolled
     */
    public String getDescription() {
        return super.getDescription() + "\n" + getFormat() + " " + semester + " Enrolment: " + students.size();
    }

    /**
     * gets just the description without the semester and enrolment
     * 
     * @return the description from the super class
     */
    public String getJustDescription() {
        return super.getDescription();
    }

    /**
     * sorts the students in the course by name
     */
    public void sortByName() {
        Collections.sort(students, new NameComparator());
    }

    /**
     * comparator class to compare students by name
     */
    private class NameComparator implements Comparator<Student> {
        public int compare(Student a, Student b) {
            return a.compareTo(b);
        }
    }

    /**
     * sorts the students in the course by ID
     */
    public void sortById() {
        Collections.sort(students, new IdComparator());
    }

    /**
     * comparator class to compare students by ID
     */
    private class IdComparator implements Comparator<Student> {
        public int compare(Student a, Student b) {
            return a.getId().compareTo(b.getId());
        }
    }

    /**
     * used to find a student in an array list of students
     * 
     * @param studentId   the ID of the student to be found
     * @param studentList the array list of students
     * @return the student with the correct ID
     */
    private Student findStudent(String studentId, ArrayList<Student> studentList) {
        for (Student s : studentList) {
            if (s.getId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    /**
     * used to find a credit course in an array list of credit courses
     * 
     * @param courseCode the course code of the credit course to be found
     * @param courseList the array list of credit courses
     * @return the credit course that matches the course code
     */
    private CreditCourse findCreditCourse(String courseCode, ArrayList<CreditCourse> courseList) {
        for (CreditCourse cc : courseList) {
            if (cc.getCode().equals(courseCode)) {
                return cc;
            }
        }
        return null;
    }
}
