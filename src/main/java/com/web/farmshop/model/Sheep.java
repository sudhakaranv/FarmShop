package com.web.farmshop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@JacksonXmlRootElement(localName = "sheep")
public class Sheep extends Animal {
	
	public String getType()
	{
		return "sheep";
	}
	
}
