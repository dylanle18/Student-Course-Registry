public class CreditCourse extends Course {
    private String semester;
    private double grade;
    private boolean active;

    /**
     * initializes the credit course object
     * 
     * @param name     the name of the course
     * @param code     the code of the course
     * @param descr    the course description
     * @param fmt      the format of the course
     * @param semester the semester the course was taken
     * @param grade    the grade the student received in the course
     */
    public CreditCourse(String name, String code, String descr, String fmt, String semester, double grade) {
        // add code
        super(name, code, descr, fmt);
        this.semester = semester;
        this.grade = grade;
        active = true;
    }

    /**
     * gets the grade
     * 
     * @return the numeric grade of the student in this course
     */
    public double getGrade() {
        return grade;
    }

    /**
     * sets the grade
     * 
     * @param grade the numeric grade
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * gets the status
     * 
     * @return that status of the course
     */
    public boolean getActive() {
        // add code and remove line below
        return active;
    }

    /**
     * sets the active status to ture
     */
    public void setActive() {
        // add code
        active = true;
    }

    /**
     * sets the active status to false
     */
    public void setInactive() {
        // add code
        active = false;
    }

    /**
     * to display information of the student, the semester they took the course and
     * the grade they got
     * 
     * @return a string to be printed
     */
    public String displayGrade() {
        return getInfo() + " " + semester + " " + convertNumericGrade(grade);
    }
}