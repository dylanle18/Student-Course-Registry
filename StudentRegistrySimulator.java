import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

public class StudentRegistrySimulator {
    public static void main(String[] args) {

        Registry registry = null;
        try {
            registry = new Registry();
        } catch (FileNotFoundException e) {
            System.out.println("student.txt File Not Found");
            return;
        } catch (NoSuchElementException e) {
            System.out.println("Bad File student.txt");
            return;
        }

        Scheduler schedule = new Scheduler((TreeMap<String, ActiveCourse>) registry.getCourses());

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals(""))
                continue;

            Scanner commandLine = new Scanner(inputLine);
            String command = null;
            if (commandLine.hasNext()) {
                command = commandLine.next();
            }

            if (command == null || command.equals(""))
                continue;

            else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST")) {
                registry.printAllStudents();
            } else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT")) {
                commandLine.close();
                scanner.close();
                return;
            } else if (command.equalsIgnoreCase("REG")) {
                try {
                    String studentName = commandLine.next();
                    String studentID = commandLine.next();
                    if (!isStringOnlyAlphabet(studentName)) {
                        System.out.println("Invalid Characters in Name " + studentName);
                    }
                    if (!isNumeric(studentID)) {
                        System.out.println("Invalid Characters in ID " + studentID);
                    }
                    if (isStringOnlyAlphabet(studentName) && isNumeric(studentID)) {
                        registry.addNewStudent(studentName, studentID);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("DEL")) {
                try {
                    String studentID = commandLine.next();
                    if (isNumeric(studentID)) {
                        registry.removeStudent(studentID);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("ADDC")) {
                try {
                    String studentID = commandLine.next();
                    String courseCode = commandLine.next().toUpperCase();
                    registry.addCourse(studentID, courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("DROPC")) {
                try {
                    String studentID = commandLine.next();
                    String courseCode = commandLine.next().toUpperCase();
                    registry.dropCourse(studentID, courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("PAC")) {
                // print all active courses
                registry.printActiveCourses();
            } else if (command.equalsIgnoreCase("PCL")) {
                // get course code string
                // print class list (i.e. students) for this course
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    registry.printClassList(courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("PGR")) {
                // get course code string
                // print name, id and grade of all students in active course
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    registry.printGrades(courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("PSC")) {
                // get student id string
                // print all credit courses of student
                try {
                    String studentID = commandLine.next();
                    registry.printStudentCourses(studentID);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("PST")) {
                // get student id string
                // print student transcript
                try {
                    String studentID = commandLine.next();
                    registry.printStudentTranscript(studentID);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("SFG")) {
                // set final grade of student
                // get course code, student id, numeric grade
                // use registry to set final grade of this student (see class Registry)
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    String studentID = commandLine.next();
                    double grade = commandLine.nextDouble();
                    registry.setFinalGrade(courseCode, studentID, grade);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("SCN")) {
                // get course code
                // sort list of students in course by name (i.e. alphabetically)
                // see class Registry
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    registry.sortCourseByName(courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("SCI")) {
                // get course code
                // sort list of students in course by student id
                // see class Registry
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    registry.sortCourseById(courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("SCH")) {
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    String day = commandLine.next().toUpperCase();
                    int startTime = commandLine.nextInt();
                    int duration = commandLine.nextInt();
                    schedule.setDayAndTime(courseCode, day, startTime, duration);
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid or Missing Parameters");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (command.equalsIgnoreCase("CSCH")) {
                try {
                    String courseCode = commandLine.next().toUpperCase();
                    schedule.clearSchedule(courseCode);
                } catch (Exception e) {
                    System.out.println("Invalid or Missing Parameters");
                }
            } else if (command.equalsIgnoreCase("PSCH")) {
                schedule.printSchedule();
            } else {
                System.out.println("Invalid Command");
            }
            System.out.print("\n>");
            commandLine.close();
        }
        scanner.close();
    }

    private static boolean isStringOnlyAlphabet(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}