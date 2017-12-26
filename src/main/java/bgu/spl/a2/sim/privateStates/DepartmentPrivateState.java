package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState{
	private List<String> courseList		= new LinkedList<>();
	private List<String> studentList	= new LinkedList<>();
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {

	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public boolean addCourse(String courseName){

		if(!courseList.contains(courseName)) {
			courseList.add(courseName);
			return true;
		}
		return false;
	}

	public boolean addStudent(String studentID){

		if(!studentList.contains(studentID)) {
			studentList.add(studentID);
			return true;
		}
		return false;
	}

	public boolean removeCourse(String courseName){

		if(courseList.contains(courseName)) {
			courseList.remove(courseName);
			return true;
		}
		return false;
	}

}
