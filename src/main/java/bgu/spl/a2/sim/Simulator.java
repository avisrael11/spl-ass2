/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.io.*;
import java.util.*;
import java.lang.System;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.ActionAndPrivateState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {


	public 	static ActorThreadPool atp;
	private static JsonObject jsonObj;
	private static Warehouse wh;
	private static CountDownLatch ActionPending;

	public Simulator() {
	}

	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){

    	System.out.println("starting...");

		// Phase - build computer
		JsonArray computersArray = jsonObj.get("Computers").getAsJsonArray();
		HashMap<String, Computer> ComputersCollection = ActionFactory.ComputerBuilder(computersArray);
		wh = new Warehouse(ComputersCollection);
		atp.start();

		//////////////////////////
		// Phase 1
		runPhase("Phase 1");
		System.out.println("and of phase 111111111111111111111111111111111 " + Thread.currentThread().getId());

		// Phase 2
		runPhase("Phase 2");
		System.out.println("and of phase 2222222222222222222222222222222222" + Thread.currentThread().getId());

		// Phase 3
		runPhase("Phase 3");

		///////////////////////////

		HashMap<String, PrivateState> simulationResult = end();

		try {
			FileOutputStream fResult = new FileOutputStream("result.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fResult);
			oos.writeObject(simulationResult);
		} catch (FileNotFoundException e) {
			System.out.println("The file is not found");
		} catch (IOException e) {
			e.toString();
		}
    }


	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		atp = myActorThreadPool;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String, PrivateState> end(){


		try {
			atp.shutdown();
		}catch(InterruptedException ignored){}
		return new HashMap<String, PrivateState> (atp.getActors());
	}

	/**
	 * parse the jsonfile and create mutch threads
	 * returns list of private states
	 */
	public static int main(String [] args){
		String path 			= "test.json";//args[0];
		JsonParser jsonParse 	= new JsonParser();
		int numThread; // Number of thread to create - from json

		System.out.println("executing...");
		try{
			jsonObj = jsonParse.parse(new FileReader(path)).getAsJsonObject();
		}catch(FileNotFoundException e){System.out.println("exeption");}

		numThread = jsonObj.get("threads").getAsInt();
		System.out.println(numThread);

		ActorThreadPool atpMain = new ActorThreadPool(numThread);
		attachActorThreadPool(atpMain);
		start();

		return 0;

	}

	//private method for run phase by ActionFactory
	private static void runPhase(String phase){
		JsonArray phaseActions					 				 = jsonObj.get(phase).getAsJsonArray();
		LinkedList<ActionAndPrivateState> actionAndPrivateStates = ActionFactory.PhaseBuilder(phaseActions,atp,wh);
		System.out.println("num of actions in phase: " + actionAndPrivateStates.size() + " " + Thread.currentThread().getId());

		ActionPending							  				 = new CountDownLatch(actionAndPrivateStates.size());

		for (ActionAndPrivateState action : actionAndPrivateStates) {
			action.getAction().getResult().subscribe(()->ActionPending.countDown());
			atp.submit(action.getAction(), action.getAction().getId(), action.getPrivateState());
		}
		try{
			System.out.println("waiting for all threads " + Thread.currentThread().getId());

			ActionPending.await();
		}catch(InterruptedException e){System.out.println(e.getMessage());}
	}
}
