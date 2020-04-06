package me.colley.mpp2xls.config.xml;

public class MyCellStyle {

	public short indentation;
	
	public MyCellStyle(String t) {
		switch(t) {
			case "indentation": 
				this.indentation = 2;
		}
	}
}
