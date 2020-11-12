import java.util.ArrayList;


public class Student implements Comparable<Student> {
    private String name;
    private String id;
    private ArrayList<CreditCourse> courses;

    /**
     * initializes the student object
     * 
     * @param name the name of the student
     * @param id   the ID of the student
     */
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        courses = new ArrayList<CreditCourse>();
    }

    /**
     * gets the ID of the student
     * 
     * @return the ID as a string
     */
    public String getId() {
        return id;
    }

    /**
     * gets the name of the student
     * 
     * @return the name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * gets an array list of all the students courses
     * 
     * @return array list of the credit courses
     */
    public ArrayList<CreditCourse> getCourses() {
        return courses;
    }

    /**
     * adds a course to the credit course list for the student
     * 
     * @param courseName the name of the course
     * @param courseCode the code of the couse
     * @param descr      the description of the course
     * @param format     amount of lec and lab of the course
     * @param sem        the semester the course is being taken
     * @param grade      the grade the student has in the course
     */
    public void addCourse(String courseName, String courseCode, String descr, String format, String sem, double grade) {
        // create a CreditCourse object
        // set course active
        // add to courses array list
        CreditCourse course = new CreditCourse(courseName, courseCode, descr, format, sem, grade);
        course.setActive();
        courses.add(course);
    }

    /**
     * prints the students transcript (all completed courses)
     */
    public void printTranscript() {
        for (CreditCourse cc : courses) {
            // if the course is inactive
            if (!cc.getActive()) {
                System.out.println(cc.displayGrade());
            }
        }
    }

    /**
     * prints all the active courses the student in currently enrolled in
     */
    public void printActiveCourses() {
        for (CreditCourse cc : courses) {
            // if the course is active
            if (cc.getActive()) {
                System.out.println(cc.getInfo() + "\n" + cc.getDescription() + "\n" + cc.getFormat());
            }
        }
    }

    /**
     * drops the student of an active course
     * 
     * @param courseCode the code of the course to be dropped
     */
    public void removeActiveCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            // if course is in the credit course list and it is active
            if (courses.get(i).getCode().equals(courseCode) && courses.get(i).getActive()) {
                courses.remove(i);
            }
        }
    }

    /**
     * prints the student ID and name when printing a student
     */
    public String toString() {
        return "Student ID: " + id + " Name: " + name;
    }

    /**
     * checks to see if this student has the same ID as another student
     */
    public boolean equals(Object other) {
        // whether name and ID are the same
        return this.getId().equals(((Student) other).getId());
    }

    /**
     * compareTo method to compare by name of student
     */
    public int compareTo(Student otherStudent) {
        return this.getName().compareTo(otherStudent.getName());
    }
}
