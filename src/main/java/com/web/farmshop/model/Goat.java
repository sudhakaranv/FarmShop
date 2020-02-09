package com.web.farmshop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@JacksonXmlRootElement(localName = "goat")
public class Goat extends Animal{
	
	public String getType()
	{
		return "goat";
	}

}
