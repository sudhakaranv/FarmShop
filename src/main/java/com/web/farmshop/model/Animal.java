package com.web.farmshop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "animal")
public class Animal {

	@JacksonXmlProperty
	private String name;
	@JacksonXmlProperty
	private String type;
	@JacksonXmlProperty
	private char sex;
	@JacksonXmlProperty
	private int wool;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public int getWool() {
		return wool;
	}

	public void setWool(int wool) {
		this.wool = wool;
	}

}
