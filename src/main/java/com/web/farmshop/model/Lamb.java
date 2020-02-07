package com.web.farmshop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "lamb")
public class Lamb extends Animal {
	
	public String getType()
	{
		return "lamb";
	}
	
}
