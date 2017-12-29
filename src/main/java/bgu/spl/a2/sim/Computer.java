package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

public class Computer {

	String computerType;
	long failSig;
	long successSig;
	private SuspendingMutex mutex;

	private final int passGrade = 56;
	
	public Computer(String computerType) {
		this.computerType = computerType;
	}


	public void setFailSig (long fSig){
		this.failSig = fSig;
	}

	public void setSuccessSig (long sSig){
		this.successSig = sSig;
	}

	public void setMutex(SuspendingMutex mutex) {
		this.mutex = mutex;
	}

	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 */
	public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades){
		boolean confirmCourse = true;

		for (String courseName : courses){
		    if(!coursesGrades.containsKey(courseName) || coursesGrades.get(courseName) < passGrade){
		        confirmCourse = false;
		        break;
            }
        }
        return confirmCourse? successSig : failSig;
	}

	public void free(){
		mutex.up(this);
	}
}
