package com.web.farmshop.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * @author Sudhakaran Vasudevan
 *
 */
@Component
@JacksonXmlRootElement(localName = "flock")
public class FlockInput {

	@JacksonXmlProperty(localName = "sheep")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Sheep> sheeps;

	public List<Sheep> getSheeps() {
		return sheeps;
	}

	public void setSheeps(List<Sheep> sheeps) {
		this.sheeps = sheeps;
	}
	
	@JacksonXmlProperty(localName = "lamb")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Lamb> lambs;

	public List<Lamb> getLambs() {
		return lambs;
	}

	public void setLambs(List<Lamb> lambs) {
		this.lambs = lambs;
	}
	
	
	@JacksonXmlProperty(localName = "goat")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Goat> goats;

	public List<Goat> getGoats() {
		return goats;
	}

	public void setGoats(List<Goat> goats) {
		this.goats = goats;
	}
	
	
}