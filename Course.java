public class Course {
    private String code;
    private String name;
    private String description;
    private String format;

    /**
     * default constructor for course class sets each param to an empty string
     */
    public Course() {
        this.code = "";
        this.name = "";
        this.description = "";
        this.format = "";
    }

    /**
     * constructor for course class with parameters
     * 
     * @param name  the name of the course
     * @param code  the course code
     * @param descr the description of the course
     * @param fmt   the format of the course # of Lec and Lab
     */
    public Course(String name, String code, String descr, String fmt) {
        this.code = code;
        this.name = name;
        this.description = descr;
        this.format = fmt;
    }

    /**
     * gets the course code
     * 
     * @return the course code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * gets the course name
     * 
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * gets the course format
     * 
     * @return the format of the course
     */
    public String getFormat() {
        return format;
    }

    /**
     * gets the description of the course
     * 
     * @return the description of the course
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the course info (code and name)
     * 
     * @return the code and name of the course
     */
    public String getInfo() {
        return code + " - " + name;
    }

    /**
     * given a numeric grade it converts it into a letter grade
     * 
     * @param score the numeric grade
     * @return the grade represented in letter form
     */
    public static String convertNumericGrade(double score) {
        // fill in code
        if (score >= 90) {
            return "A+";
        } else if (score >= 85) {
            return "A";
        } else if (score >= 80) {
            return "A-";
        } else if (score >= 77) {
            return "B+";
        } else if (score >= 73) {
            return "B";
        } else if (score >= 70) {
            return "B-";
        } else if (score >= 67) {
            return "C+";
        } else if (score >= 63) {
            return "C";
        } else if (score >= 60) {
            return "C-";
        } else if (score >= 57) {
            return "D+";
        } else if (score >= 53) {
            return "D";
        } else if (score >= 50) {
            return "D-";
        } else {
            return "F";
        }
    }
}
