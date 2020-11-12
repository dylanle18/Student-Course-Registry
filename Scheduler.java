import java.util.Set;
import java.util.TreeMap;

public class Scheduler {
	TreeMap<String, ActiveCourse> schedule;

	/**
	 * constructor for scheduler class
	 * 
	 * @param courses the active courses for the schedule
	 */
	public Scheduler(TreeMap<String, ActiveCourse> courses) {
		schedule = courses;
	}

	/**
	 * sets the day and time of the course on the schedule
	 * 
	 * @param courseCode the course code
	 * @param day        the day of the lecture
	 * @param startTime  the start time of the lecture
	 * @param duration   the lecture duration
	 */
	public void setDayAndTime(String courseCode, String day, int startTime, int duration) throws RuntimeException {
		if (unknownCourse(courseCode)) {
			throw new UnknownCourse("Unknown course: " + courseCode);
		} else if (invalidDay(day)) {
			throw new InvalidDay("Invalid Lecture Day");
		} else if (invalidTime(startTime, duration)) {
			throw new InvalidTime("Invalid Lecture Time");
		} else if (invalidDuration(duration)) {
			throw new InvalidDuration("Invalid Lecture Duration");
		} else if (lectureTimeCollision(courseCode, day, startTime, duration)) {
			throw new LectureTimeCollision("Invalid Lecture Collision");
		} else {
			ActiveCourse ac = schedule.get(courseCode);
			ac.setLectureDay(day);
			ac.setLectureStart(startTime);
			ac.setLectureDuration(duration);
			System.out.println(courseCode + " was Added to the Schedule");
		}
	}

	/**
	 * checks to see if the course is unknown
	 * 
	 * @param courseCode the course code
	 * @return true if it is an unknown course
	 */
	private boolean unknownCourse(String courseCode) {
		if (schedule.containsKey(courseCode)) {
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the day is invalid
	 * 
	 * @param day the day to check
	 * @return true it its invalid
	 */
	private boolean invalidDay(String day) {
		String[] weekDays = { "MON", "TUE", "WED", "THUR", "FRI" };
		for (String weekDay : weekDays) {
			if (day.equals(weekDay)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks to see if the time is invalid
	 * 
	 * @param startTime the start time of the lecture
	 * @param duration  the lecture duration
	 * @return true if time is invalid
	 */
	private boolean invalidTime(int startTime, int duration) {
		if (startTime % 100 != 0) {
			return true;
		}
		if (startTime < 800 || startTime + duration * 100 > 1700) {
			return true;
		}
		return false;
	}

	/**
	 * checks if the duration is invalid
	 * 
	 * @param duration the lecture duration
	 * @return true if duration is invalid
	 */
	private boolean invalidDuration(int duration) {
		if (duration != 1 && duration != 2 && duration != 3) {
			return true;
		}
		return false;
	}

	/**
	 * checks if lecture collides with another lecture
	 * 
	 * @param couseCode the course code
	 * @param day       the day to be inserted
	 * @param startTime the start of the lecture
	 * @param duration  the lecture duration
	 * @return ture if the lecture collides with another
	 */
	private boolean lectureTimeCollision(String couseCode, String day, int startTime, int duration) {
		Set<String> codeSet = schedule.keySet();

		for (String code : codeSet) {
			ActiveCourse ac = schedule.get(code);
			String acDay = ac.getLectureDay();
			int acStartTime = ac.getLectureStart();
			int acDuration = ac.getLectureDuration();

			if (day.equals(acDay)) {
				if (startTime == acStartTime) {
					return true;
				}
				if (startTime < acStartTime && startTime + duration * 100 > acStartTime) {
					return true;
				}
				if (acStartTime < startTime && acStartTime + acDuration * 100 > startTime) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * clears the course from the schedule
	 * 
	 * @param courseCode the course code
	 */
	public void clearSchedule(String courseCode) {
		if (schedule.containsKey(courseCode)) {
			ActiveCourse ac = schedule.get(courseCode);
			ac.setLectureDay("");
			ac.setLectureStart(0);
			ac.setLectureDuration(0);
			System.out.println(courseCode + " was Cleared from Schedule");
		}
	}

	/**
	 * prints the schedule
	 */
	public void printSchedule() {
		// stores the course codes in a 2d array
		String[][] schTable = new String[9][5];
		Set<String> codeSet = schedule.keySet();
		for (String code : codeSet) {
			// gets the y and x postions of the course code
			ActiveCourse ac = schedule.get(code);
			int yPos = getYPos(ac.getLectureStart());
			int xPos = getXPos(ac.getLectureDay());
			// if the course code is supposed to be scheduled insert it into the array
			if (yPos < 9 && xPos < 5) {
				for (int i = 0; i < ac.getLectureDuration(); i++) {
					schTable[yPos + i][xPos] = code;
				}
			}
		}
		// prints the days
		System.out.print("\t Mon\t Tue\t Wed\t Thur\t Fri");
		// prints the times and course codes
		for (int i = 0; i < schTable.length; i++) {
			System.out.printf("\n%04d", (i + 8) * 100);
			for (int j = 0; j < schTable[i].length; j++) {
				// tabs if there is no code
				if (schTable[i][j] == null) {
					System.out.print("\t");
				} else {
					System.out.print("\t" + schTable[i][j]);
				}
			}
		}
		System.out.println("");
	}

	/**
	 * gets the y position of course based on the start times
	 * 
	 * @param start the start time of the course
	 * @return the y position
	 */
	private int getYPos(int start) {
		switch (start) {
			case 800:
				return 0;
			case 900:
				return 1;
			case 1000:
				return 2;
			case 1100:
				return 3;
			case 1200:
				return 4;
			case 1300:
				return 5;
			case 1400:
				return 6;
			case 1500:
				return 7;
			case 1600:
				return 8;
			default:
				return 9;
		}
	}

	/**
	 * gets the x position of the course based on the day
	 * 
	 * @param day the day of the course
	 * @return the x postion
	 */
	private int getXPos(String day) {
		switch (day) {
			case "MON":
				return 0;
			case "TUE":
				return 1;
			case "WED":
				return 2;
			case "THUR":
				return 3;
			case "FRI":
				return 4;
			default:
				return 5;
		}
	}
}

class UnknownCourse extends RuntimeException {
	private static final long serialVersionUID = 7508376190412106583L;

	public UnknownCourse() {
	}

	public UnknownCourse(String message) {
		super(message);
	}

}

class InvalidDay extends RuntimeException {
	private static final long serialVersionUID = -1571233839662067099L;

	public InvalidDay() {
	}

	public InvalidDay(String message) {
		super(message);
	}

}

class InvalidTime extends RuntimeException {
	private static final long serialVersionUID = -3923660331762054679L;

	public InvalidTime() {
	}

	public InvalidTime(String message) {
		super(message);
	}

}

class InvalidDuration extends RuntimeException {
	private static final long serialVersionUID = -3649835493352847005L;

	public InvalidDuration() {
	}

	public InvalidDuration(String message) {
		super(message);
	}

}

class LectureTimeCollision extends RuntimeException {
	private static final long serialVersionUID = 1153587384368903446L;

	public LectureTimeCollision() {
	}

	public LectureTimeCollision(String message) {
		super(message);
	}

}