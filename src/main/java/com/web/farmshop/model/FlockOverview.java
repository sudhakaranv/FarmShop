package com.web.farmshop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@Component
public class FlockOverview {
	
	
	private FlockInput flockInput;

	private List<Animal> flock;
	
	/**
	 * @return List<Animal> flock 
	 * 
	 * This method combines the sheeps, lambs and goats list and return list of animals 
	 * for flock overview 
	 */
	public List<Animal> getFlock() {
		
		flock=new ArrayList<>();
		List<Sheep> sheeps=flockInput.getSheeps();
		List<Lamb> lambs=flockInput.getLambs();
		List<Goat> goats=flockInput.getGoats();
		
		if(sheeps!=null)
		flock.addAll(sheeps);
		if(lambs!=null)
		flock.addAll(lambs);
		if(goats!=null)
		flock.addAll(goats);
		
		return flock;
	}

	public void setFlockInput(FlockInput flockInput) {
		this.flockInput = flockInput;
	}
	
	

}
