package me.colley.mpp2xls.config.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "defaults")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Defaults {

	//@XmlRootElement(name = "dateFormat")
	public String dateFormat;
	
}
