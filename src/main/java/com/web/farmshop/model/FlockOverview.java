package com.web.farmshop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Eigenaar
 *
 */
@Component
public class FlockOverview {
	
	
	private FlockInput flockInput;

	private List<Animal> flock;
	
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
