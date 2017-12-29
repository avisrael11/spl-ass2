package bgu.spl.a2.sim.privateStates;

import java.util.HashMap;

import bgu.spl.a2.PrivateState;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState{

	private HashMap<String, Integer> grades = new HashMap<>();
	private long signature = 0;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}

	public boolean removeCourse(String course){
		if (grades.containsKey(course)) {
			grades.remove(course);
			return true;
		}
		return false;
	}

	public boolean addCourse(String course, int grade ){
		if (grades.containsKey(course)) {
			return false;
		}
		grades.put(course, grade);
		return true;
	}

	public void setSignature(long signature){
		this.signature = signature;
	}
}
