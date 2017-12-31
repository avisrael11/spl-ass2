package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private Integer availableSpots		= 0;
	private Integer registered			= 0;
	private List<String> regStudents	= new LinkedList<>();
	private List<String> prequisites	= new LinkedList<>();
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {

	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public boolean addSpaces(int numSpaces){
			if (availableSpots != -1) {
				availableSpots += numSpaces;
				return true;
			}
		return false;
	}

	public void setPrequisites(List<String> list){
		prequisites = list;
	}

	public void setAvailableSpots(int availableSpots) {
			this.availableSpots = availableSpots;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public boolean registerStudent(String student){
			if(availableSpots > 0){
				++registered;
				--availableSpots;
				regStudents.add(student);
				return true;
			}
		return false;
	}

	public void unRegisterStudent(String student){
		if(regStudents.remove(student) && registered > 0){
			--registered;
			++availableSpots;
		}
	}
}
