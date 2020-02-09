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
		flock.addAll(flockInput.getSheeps());
		flock.addAll(flockInput.getLambs());
		flock.addAll(flockInput.getGoats());
		return flock;
	}

	public void setFlockInput(FlockInput flockInput) {
		this.flockInput = flockInput;
	}
	
	

}
