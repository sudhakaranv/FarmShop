package com.web.farmshop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Eigenaar
 *
 */
@JacksonXmlRootElement(localName = "sheep")
public class Sheep extends Animal {
	
	public String getType()
	{
		return "sheep";
	}
	
}
