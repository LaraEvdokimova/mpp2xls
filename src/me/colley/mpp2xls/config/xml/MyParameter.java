package me.colley.mpp2xls.config.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parameter")
@XmlAccessorType(XmlAccessType.PROPERTY)
/**
 * @deprecated
 */
public class MyParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	public String type;
	public Object value;

	public MyParameter() {
		super();
	}

	public MyParameter(Integer id, String type, Object value) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
	}

	// Setters and Getters

	@Override
	public String toString() {
		return "Column [id=" + id 
				+ ", type=" + type 
				+ ", value=" + value.toString() + "]";
	}
}