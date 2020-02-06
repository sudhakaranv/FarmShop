package com.web.farmshop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "flock")
public class Flock {

	@JacksonXmlProperty(localName = "animal")
	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty("flock")
	private List<Animal> flockOfAnimal;

	public List<Animal> getFlockOfAnimal() {
		return flockOfAnimal;
	}

	public void setFlockOfAnimal(List<Animal> flockOfAnimal) {
		this.flockOfAnimal = flockOfAnimal;
	}

}