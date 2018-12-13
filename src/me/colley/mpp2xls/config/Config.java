package me.colley.mpp2xls.config;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import me.colley.mpp2xls.config.xml.Defaults;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;

	public String infile;
	public String outfile;
	public String dateFormat;
//	public Defaults defaults;
	public List<Column> columns = new ArrayList<Column>();

	public Config() {
		super();
		System.out.println("Employee.java init without params");
	}

	public Config(String infile, String outfile, String dateFormat, List<Column> columns) {
		super();
		System.out.println("Employee.java init with params");
		this.infile = infile;
		this.outfile = outfile;
		this.columns = columns;
	}

	// Setters and Getters
	
	@Override
	public String toString() {
		return "Config [infile=" + infile + ", outfile=" + outfile + "]";
	}

	public Object getColumns() {
		return this.columns;
	}
}