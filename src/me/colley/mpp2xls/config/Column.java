package me.colley.mpp2xls.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.poi.ss.usermodel.CellType;

import me.colley.mpp2xls.config.xml.MyCellStyle;
import me.colley.mpp2xls.config.xml.MyParameter;

//import me.colley.mpp2xls.config.xml.Parameter;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.List;

@XmlRootElement(name = "column")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Column implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	public String name;
	public String type;
	public String method;
	public String of;
	public MyCellStyle cellstyle;
	// public Class<?>[] params;
	// public Parameter[] params;
	public List<MyParameter> params;

	public Column() {
		super();
	}

	public Column(Integer id, String name, String method, String type) {
		super();
		this.id = id;
		this.name = name;
		this.method = method;
		this.type = type;
	}

	// Setters and Getters

	@Override
	public String toString() {
		return "Column [id=" + id + ", name=" + name + ", type=" + type + ", of=" + of + ", method=" + method + "]";
	}

	public Class<?> getParameterTypes() {
		Class<?> ret;
		switch (this.params.size()) {
		// case 0:
		// break;
		case 1:
			MyParameter o = this.params.iterator().next();
			// String className = o.getClass().toString();
			switch (o.type) {
			case "int":
			case "java.lang.Integer":
				ret = Integer.TYPE;
				break;
			case "java.lang.Number":
				ret = Double.class;
				break;
			case "java.lang.String":
				ret = String.class;
				break;
			default:
				ret = o.getClass();
			}
			break;
		default:
			// //Class<?> ret;
			// Class[] cArg = new Class[1];
			//
			// int i = 0;
			// while(pi.hasNext()) {
			// Parameter p = (Parameter) pi.next();
			// Class<?> type = p.getType();
			// cArg[i++] = Long.class;
			//
			// }
			// break;
			ret = null;
		}

		return ret;
	}

	public Object getInvokeParams() {
		Object ret = null;
		switch (this.params.size()) {
		case 1:
			MyParameter p = (MyParameter) this.params.iterator().next();
			// String className = p.type;//.toString();
			// switch(className) {
			// case "java.lang.Integer":
			// ret = p.value;
			// break;
			// default:
			// ret = o.getClass();
			// }
			ret = p.value;
			break;
		default:
			ret = null;
		}
		return ret;
	}

	public CellType getCellType() {
		// TODO Auto-generated method stub
		CellType ct = null;
		switch (this.type) {
		case "java.lang.String":
			ct = CellType.STRING;
			break;
		case "int":
		case "long":
		case "float":
		case "double":
		case "java.lang.Float":
		case "java.lang.Double":
		case "java.lang.Long":
		case "java.lang.Number":
		case "java.lang.Integer":
			ct = CellType.NUMERIC;
			break;
		default:
			ct = CellType.STRING;
			break;
		}
		return ct;
	}
}
