import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Registry {
    private Map<String, Student> students = new TreeMap<>();
    private Map<String, ActiveCourse> courses = new TreeMap<>();

    public Registry() throws FileNotFoundException, NoSuchElementException {
        File studentFile = new File("students.txt");
        studentFileReader(studentFile);

        ArrayList<Student> list = new ArrayList<Student>();

        // CPS209
        String courseName = "Computer Science II";
        String courseCode = "CPS209";
        String descr = "Learn how to write complex programs!";
        String format = "3Lec 2Lab";
        courses.put("CPS209", new ActiveCourse(courseName, courseCode, descr, format, "W2020", list));
        // CPS511
        courseName = "Computer Graphics";
        courseCode = "CPS511";
        descr = "Learn how to write cool graphics programs";
        format = "3Lec";
        courses.put("CPS511", new ActiveCourse(courseName, courseCode, descr, format, "F2020", list));
        // CPS643
        courseName = "Virtual Reality";
        courseCode = "CPS643";
        descr = "Learn how to write extremely cool virtual reality programs";
        format = "3Lec 2Lab";
        courses.put("CPS643", new ActiveCourse(courseName, courseCode, descr, format, "W2020", list));
        // CPS706
        courseName = "Computer Networks";
        courseCode = "CPS706";
        descr = "Learn about Computer Networking";
        format = "3Lec 1Lab";
        courses.put("CPS706", new ActiveCourse(courseName, courseCode, descr, format, "W2020", list));
        // CPS616
        courseName = "Algorithms";
        courseCode = "CPS616";
        descr = "Learn about Algorithms";
        format = "3Lec 1Lab";
        courses.put("CPS616", new ActiveCourse(courseName, courseCode, descr, format, "W2020", list));
    }

    /**
     * gets the courses
     * 
     * @return the courses
     */
    public Map<String, ActiveCourse> getCourses() {
        return courses;
    }

    /**
     * reads the student file and adds the students to the map
     * 
     * @param file //the student.txt file
     * @throws FileNotFoundException  if file not found
     * @throws NoSuchElementException if name or id is missing
     */
    private void studentFileReader(File file) throws FileNotFoundException, NoSuchElementException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            Scanner studentLine = new Scanner(inputLine);

            String name = studentLine.next();
            String id = studentLine.next();

            Student student = new Student(name, id);
            students.put(id, student);

            studentLine.close();
        }
        scanner.close();
    }

    /**
     * adds a new student to the registry
     * 
     * @param name      the name of the student
     * @param studentId the id of the student
     * @return true or false if the student was added or not
     * @throws IllegalArgumentException if student ID isnt 5 digits
     */
    public boolean addNewStudent(String name, String studentId) throws IllegalArgumentException {
        if (studentId.length() != 5)
            throw new IllegalArgumentException("Student ID Must be 5 Digits Long");
        Student newStudent = new Student(name, studentId);
        if (students.containsKey(studentId)) {
            System.out.println("Student ID not Available");
            return false;
        }
        students.put(studentId, newStudent);
        System.out.println("Student was Added to Registry");
        return true;
    }

    /**
     * removes a student from the registry
     * 
     * @param studentId the ID the student to be removed
     * @return true or false if the student was removed or not
     */
    public boolean removeStudent(String studentId) {
        if (students.containsKey(studentId)) {
            students.remove(studentId);
            System.out.println("Student has been Removed from Registry");
            return true;
        }
        System.out.println("Student not Found");
        return false;
    }

    /**
     * prints out all the registered students
     */
    public void printAllStudents() {
        Set<String> idSet = students.keySet();
        for (String id : idSet) {
            System.out.println(students.get(id));
        }
    }

    /**
     * enroll a student into an active course
     * 
     * @param studentId  the ID of the student
     * @param courseCode the courde code of the active course
     */
    public void addCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        ActiveCourse course = courses.get(courseCode);

        // if the student and the active course are both found
        if (student != null && course != null) {
            // if the student has already taken the course
            if (findCreditCourse(courseCode, student.getCourses()) != null) {
                System.out.print("Student has Already Taken: " + courseCode);
                return;
            }
            // if the student is currently enrolled in the course
            if (findStudent(studentId, course.getStudents()) != null) {
                System.out.print("Student is Already Enrolled in: " + courseCode);
                return;
            }
            // adds the student to the active course and adds the course to the students
            // list of credit courses
            course.addStudent(student);
            student.addCourse(course.getName(), course.getCode(), course.getJustDescription(), course.getFormat(),
                    course.getSemester(), 0);
            System.out.println("Student has been Enrolled into: " + courseCode);
        } else {
            System.out.print("Student not Found");
        }
    }

    /**
     * drops student from a course
     * 
     * @param studentId  the ID of the student
     * @param courseCode the code of the course to be dropped
     */
    public void dropCourse(String studentId, String courseCode) {
        Student student = null;
        ActiveCourse course = courses.get(courseCode);

        // if the active course is found find the student
        if (course != null) {
            student = findStudent(studentId, course.getStudents());
        } else {
            System.out.println("Course could not be Found");
        }
        // if the student is found remove the student from the active course and remove
        // the course from the students list of credit courses
        if (student != null) {
            course.removeStudent(studentId);
            student.removeActiveCourse(courseCode);
            System.out.println("Student has Dropped: " + courseCode);
        } else {
            System.out.println("Student could not be Found");
        }
    }

    /**
     * prints all the active courses
     */
    public void printActiveCourses() {
        Set<String> codeSet = courses.keySet();
        for (String code : codeSet) {
            ActiveCourse ac = courses.get(code);
            System.out.println("\n" + ac.getInfo() + "\n" + ac.getDescription());
        }
    }

    /**
     * prints the class list of course
     * 
     * @param courseCode the code for the course
     */
    public void printClassList(String courseCode) {
        ActiveCourse course = courses.get(courseCode);
        course.printClassList();
    }

    /**
     * sorts the students in a course by name
     * 
     * @param courseCode the code for the course
     */
    public void sortCourseByName(String courseCode) {
        ActiveCourse course = courses.get(courseCode);

        // if the active course is found sort course by name
        if (course != null) {
            course.sortByName();
            System.out.println(courseCode + " has been Sorted by Student Name");
        }
    }

    /**
     * sorts the students in a course by ID
     * 
     * @param courseCode the code for the course
     */
    public void sortCourseById(String courseCode) {
        ActiveCourse course = courses.get(courseCode);

        // if the active course is found sort course by ID
        if (course != null) {
            course.sortById();
            System.out.println(courseCode + " has been Sorted by Student ID");
        }
    }

    /**
     * prints all the students and their grades for a course
     * 
     * @param courseCode the code for the course
     */
    public void printGrades(String courseCode) {
        ActiveCourse course = courses.get(courseCode);

        // if the active course is found print grades of students in course
        if (course != null) {
            course.printGrades();
        }
    }

    /**
     * prints all the active courses of a student
     * 
     * @param studentId the ID of the student
     */
    public void printStudentCourses(String studentId) {
        Student student = students.get(studentId);

        // if the student is found print all active course
        if (student != null) {
            student.printActiveCourses();
        }
    }

    /**
     * prints the transcripts for the student (completed courses)
     * 
     * @param studentId the ID of the student
     */
    public void printStudentTranscript(String studentId) {
        Student student = students.get(studentId);

        // if the student is found print the transcript
        if (student != null) {
            student.printTranscript();
        }
    }

    /**
     * sets the final grade of a student in a active course
     * 
     * @param courseCode the code for the active course
     * @param studentId  the ID of the student
     * @param grade      of the final grade
     * @throws IllegalArgumentException if the grade is out of range
     */
    public void setFinalGrade(String courseCode, String studentId, double grade) throws IllegalArgumentException {
        // find the active course
        // If found, find the student in class list
        // then search student credit course list in student object and find course
        // set the grade in credit course and set credit course inactive
        ActiveCourse ac = courses.get(courseCode);
        Student student = null;
        CreditCourse cc = null;

        // throws an exception if the grade is out of range
        if (grade > 100 || grade < 0) {
            throw new IllegalArgumentException("Grade Out of Range");
        }
        // if the active course is found find the student
        if (ac != null) {
            student = findStudent(studentId, ac.getStudents());
        }
        // if the student is found find the course in the students list of credit
        // courses
        if (student != null) {
            cc = findCreditCourse(courseCode, student.getCourses());
        }
        // if the credit course is found set the final grade and set course to inactive
        // (complete)
        if (cc != null) {
            cc.setGrade(grade);
            cc.setInactive();
            System.out.println("Final Grade was set for student (ID: " + studentId + ") in: " + courseCode);
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
