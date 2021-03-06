package me.colley.mpp2xls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.mpxj.Column;
import net.sf.mpxj.CustomField;
import net.sf.mpxj.CustomFieldContainer;
import net.sf.mpxj.FieldType;
import net.sf.mpxj.FieldTypeClass;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectCalendar;
import net.sf.mpxj.ProjectCalendarContainer;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Table;
import net.sf.mpxj.TableContainer;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskContainer;
import net.sf.mpxj.TaskField;
import net.sf.mpxj.ganttproject.schema.Field;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook; //.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import me.colley.mpp2xls.proto.Employee;
import me.colley.mpp2xls.proto.PropertyReader;

import static java.lang.System.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import me.colley.mpp2xls.config.Config;
import me.colley.mpp2xls.config.xml.XMLConfigReader;

import static java.lang.System.err;

public class MPPReadToXLSX extends Object {

	public static void main(String[] args) throws MPXJException {

		xmlConfigReaderTest();
		// xmlTest();
		// propertiesTest();

	} // main()

	public static void xmlConfigReaderTest() {
		// ConfigReader configReader = new ConfigReader("");
		// System.out.println(config.infile);
		// System.out.println(config.outfile);

		XMLConfigReader cReader = new XMLConfigReader("config.xml");
		Config config = cReader.getConfig();
		ProjectFile projectFile;

		@SuppressWarnings("unchecked")
		List<me.colley.mpp2xls.config.Column> columns = (List<me.colley.mpp2xls.config.Column>) config.getColumns();
		// for (me.colley.mpp2xls.config.Column col : columns) {
		// System.out.println(col.toString());
		// }

		// read the file
		try {
			projectFile = readFile(config);
			parseFile(projectFile, config);
		} catch (MPXJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void parseFile(ProjectFile pf, Config config) {

		Workbook workbook;
		workbook = new HSSFWorkbook();
		// Sheet sheet = workbook.createSheet("Contacts");
		// createCustomFieldsSheet(pf, workbook);
		// createTablesSheet(pf, workbook);
		createTasksSheet(pf, workbook, config);
		// this.createResourcesSheet();

		try {
			FileOutputStream fileOut = new FileOutputStream(config.outfile);
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createCustomFieldsSheet(Workbook workbook) {
		Sheet sheet = workbook.createSheet("Custom Fields");
		// // Custom fields
		// CustomFieldContainer cfc = projectFile.getCustomFields();
		// Iterator<CustomField> cfi = cfc.iterator();
		// for(int i = 0; cfi.hasNext(); i++) {
		// //Task t = tc.get(i);
		// CustomField cf = cfi.next();
		// System.out.println(cf.hashCode()+ " / "+cf.toString()+ "/"
		// +cf.getAlias() + " (" + cf.getFieldType() + ")" );
		//
		// //if(i>10) break;
		// }
	}

	private void createTablesSheet(ProjectFile pf, Workbook workbook) {
		Sheet sheet = workbook.createSheet("Tables");
		TableContainer tc = pf.getTables();
		System.out.println("Number of tables: " + tc.size());
		// Tables : Columns
		for (Table table : tc) {
			out.println("Table: " + table.getName());
			for (Column col : table.getColumns()) {
				out.print("Col: " + col);
			}
		}
		// return sheet;
	}

	/**
	 * T A S K S
	 * 
	 * @param pf
	 * @param wb
	 * @param config
	 */
	private static void createTasksSheet(ProjectFile pf, Workbook wb, Config config) {
		Sheet sheet = wb.createSheet("Tasks");
		TaskContainer tc = pf.getTasks();
		System.out.println("Number of tasks: " + tc.size());

		Iterator<me.colley.mpp2xls.config.Column> ic = config.columns.iterator();

		// HEADER ROW
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < config.columns.size(); i++) {
			me.colley.mpp2xls.config.Column c = config.columns.get(i);
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(c.name);
			// cell.setCellStyle(headerCellStyle);
		}

		int styleOffset = wb.getNumCellStyles();
		CellStyle styles0 = wb.createCellStyle(); styles0.setIndention((short) (0) );
		CellStyle styles1 = wb.createCellStyle(); styles1.setIndention((short) (1) );
		CellStyle styles2 = wb.createCellStyle(); styles2.setIndention((short) (2) );
		CellStyle styles3 = wb.createCellStyle(); styles3.setIndention((short) (3) );
		CellStyle styles4 = wb.createCellStyle(); styles4.setIndention((short) (4) );
		CellStyle styles5 = wb.createCellStyle(); styles5.setIndention((short) (5) );
		CellStyle styles6 = wb.createCellStyle(); styles6.setIndention((short) (6) );
		CellStyle styles7 = wb.createCellStyle(); styles7.setIndention((short) (7) );
		CellStyle styles8 = wb.createCellStyle(); styles8.setIndention((short) (8) );
		CellStyle styles9 = wb.createCellStyle(); styles9.setIndention((short) (9) );
		CellStyle styles10 = wb.createCellStyle(); styles10.setIndention((short) (10) );
		CellStyle styles11 = wb.createCellStyle(); styles11.setIndention((short) (11) );
		CellStyle styles12 = wb.createCellStyle(); styles12.setIndention((short) (12) );
				
//		CellStyle styles[] = {
//				styles0,styles1,styles2,styles3,styles4,styles5,
//				styles6,styles7,styles8,styles9,styles10,styles11,styles12 
//		};		
		System.out.println("styleOffset: "+styleOffset );
		
		CellStyle cellStyleDate = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		String dateFormat = config.dateFormat;
		cellStyleDate.setDataFormat(
		    createHelper.createDataFormat().getFormat(dateFormat));
		
		System.out.println("getNumCellStyles(): "+wb.getNumCellStyles() );

		// DATA ROWS
		Iterator<Task> it = tc.iterator();
		for (int i = 0; it.hasNext(); i++) {
			// Task t = tc.get(i);
			Task task = it.next();
			// task.getEnterpriseCustomField(TaskField.TEXT1);
			// task.getOutlineCode(index)
			// task.getText(index)
			task.getID();

			// Row row = sheet.createRow(rowNum++);
			Row row = sheet.createRow(i + 1); // row 0 has headers

			// Loop at Config Columns
			ic = config.columns.iterator();
			for (int j = 0; ic.hasNext(); j++) {
				me.colley.mpp2xls.config.Column col = ic.next();
				
//				System.out.println("col.name: "+col.name);	
//				System.out.println("col.cellstyle: "+col.cellstyle);	
				
				try {
					Method method;
					Object val;
					Cell cell = row.createCell(j);
					cell.setCellType(col.getCellType());
					// Field f = (Field) task.getFieldByAlias(c.name);
					if (col.method != null) {
						// task.getOutlineCode(1);
						// task.getCurrentValue(field)
						Class<?> paramTypes = col.getParameterTypes();
						if(paramTypes != null) 
							method = task.getClass().getMethod(col.method, paramTypes);
						else 
							method = task.getClass().getMethod(col.method);
						method.setAccessible(true);
						Object args = col.getInvokeParams();
						val = method.invoke(task, args);
						// String toStr = val != null ? val.toString() : "";
						// cell.setCellValue(toStr);
						// //cell.getCellStyle().setIndention((short) 1);
					} else {
						method = task.getClass().getMethod("get" + col.name);
						method.setAccessible(true);
						val = method.invoke(task);
					} // col.method

					if (val != null) {
						switch (cell.getCellTypeEnum()) {
						case NUMERIC:
							switch (val.getClass().getName()) {
							case "java.util.Date":
								Date dateVal = (Date) val;
								cell.setCellValue(dateVal);
								cell.setCellStyle(cellStyleDate);
								break;
							case "java.lang.Integer":
								Integer intVal = (Integer) val;
								cell.setCellValue(intVal);
								break;
							case "java.lang.Long":
								Long lVal = (Long) val;
								cell.setCellValue(lVal);
								break;
							case "java.lang.Float":
								Float fVal = (Float) val;
								cell.setCellValue(fVal);
								break;
							case "java.lang.Number":
							case "java.lang.Double":
								Double dVal = new Double((double) val);
								cell.setCellValue(dVal);
								break;
							}
							break;
						case STRING:
						default:
							String toStr = val != null ? val.toString() : "";
							cell.setCellValue(toStr);
						}
					} // col.val

//					// Col/cell format
//					if(col.cellstyle != null) {
//						System.out.print("col.cellstyle.toString(): "+ col.cellstyle.toString());
//						switch(col.cellstyle.toString()) {
//							case "setIndentation":
//								
//								CellStyle style;
//	//							style = (CellStyle) col.cellstyle;
//							    style = wb.createCellStyle();
//							    style.setIndention((short) 1);
//								cell.setCellStyle(style);
//						}
////						CellStyle style;
////						style = (CellStyle) col.cellstyle;
//////					    style = wb.createCellStyle();
//////					    style.setIndention((short) 1);
//////					    style.setAlignment(HorizontalAlignment.CENTER);
//////					    style.setWrapText(true);
//////					    style.setBorderRight(BorderStyle.THIN);
//////					    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//////					    style.setBorderLeft(BorderStyle.THIN);
//////					    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//////					    style.setBorderTop(BorderStyle.THIN);
//////					    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//////					    style.setBorderBottom(BorderStyle.THIN);
//////					    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//////					    style.setDataFormat(doubleDataFormat);
////						cell.setCellStyle(style);
//					}

				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NullPointerException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR: c.name = " + col.name);
					e.printStackTrace();
				}
			} // Row
			
			// get OutlineLevel
			int outlineLevel = 0;
			ic = config.columns.iterator();
			for (int j = 0; ic.hasNext(); j++) {
				me.colley.mpp2xls.config.Column col = ic.next();
				if(col.name.equals("OutlineLevel")) {
					outlineLevel = (int) row.getCell(j).getNumericCellValue();
//					System.out.println("outlineLevel: "+outlineLevel);
				}
			}

			ic = config.columns.iterator();
			for (int j = 0; ic.hasNext(); j++) {
				me.colley.mpp2xls.config.Column col = ic.next();
				//System.out.println(col.name +": "+col.cellstyle);
				if( col.cellstyle.equals("indentation")) {
					Cell cell = row.getCell(j);
//					System.out.println("col.cellstyle: "+ col.cellstyle 
//							+ ": " + outlineLevel
//							+ " : "
//							+ cell.getStringCellValue() );
					//cellStyleIndentation.setIndention((short) (outlineLevel));
//					cell.setCellStyle(styles[outlineLevel]);
					cell.setCellStyle(wb.getCellStyleAt(outlineLevel+styleOffset));
				}
			}
			
			// else {
			// // System.out.println(task.getName().toString());
			// // CustomField cf = t.
			// for (CustomField customField : projectFile.getCustomFields()) {
			// // System.out.println("/" + customField.getAlias() + "/");
			//
			// // 1225038340 / net.sf.mpxj.CustomField@49049a04 / ACNT Jira
			// // Key (Unknown TaskField(32834))
			// if (!customField.getAlias().equalsIgnoreCase("ACNT Jira Key"))
			// continue;
			// System.out.println(task.getName().toString());
			//
			// // if( task.getFieldByAlias(customField.getAlias()) != null
			// // )
			// System.out.printf("%s : %s\n", customField.getAlias(),
			// task.getFieldByAlias(customField.getAlias()));
			// }
			// if (i > 10)
			// break;
			// }
		}

		// tasks = 0-based, line 0 has data
		// sheet = 0-based, line 0 has headers
		sheet.setRowSumsBelow(false);
		it = tc.iterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i == tc.size())
				break;
			int end = seekGroupRows(tc, i);
			if (end > i) {
				sheet.groupRow(i + 2, end + 1);
			}
		}
	}

	public static int seekGroupRows(TaskContainer tc, int from) {
		int marker = from;
		if (from > tc.size())
			return from;
		int pl = 0;
		try{
			pl = tc.get(from).getOutlineLevel(); // parent level
		} catch (NullPointerException e) {
			pl=9;
		}
		int sz = tc.size();
		for (int i = from + 1; i < sz; i++) {
			int cl = 0;
			try {
				cl = tc.get(i).getOutlineLevel(); // child level
			} catch(NullPointerException n) {
				cl = 0;
			}
			if (cl > pl) {
				marker = i;
			}
			if (cl <= pl) {
				return marker;
			}
		}
		return marker;
	}

	public void createCalendarsSheet(Workbook wb) {
		Sheet sheet = wb.createSheet("Calendars");
		// // Calendars : Links Resource to Task
		// ProjectCalendarContainer pcc = project.getCalendars();
		// Iterator<ProjectCalendar> pci = pcc.iterator();
		// for(int i = 0; pci.hasNext(); i++) {
		// //Task t = tc.get(i);
		// ProjectCalendar pc = pci.next();
		// System.out.println(pc.toString());
		// if(i>10) break;
		// }
		// return sheet;
	}

	public static ProjectFile readFile(Config config) throws MPXJException {
		String filename = config.infile; // prop.getProperty("infile");
		MPPReader reader = new MPPReader();
		reader.setPreserveNoteFormatting(true);
		ProjectFile projectFile = reader.read(filename);
		return projectFile;
	}

	public static void xmlTest() {
		File xmlFile = new File("employee.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Employee.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// Employee employee = (Employee)
			// jaxbUnmarshaller.unmarshal(xmlFile);
			// System.out.println(employee);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void propertiesTest() throws MPXJException {
		PropertyReader propReader = new PropertyReader();
		Properties prop = new Properties();
		try {
			prop = propReader.getProperties();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}

/*
 * if (task.getName().equalsIgnoreCase("7.1.2 Tax Accounting")) { //
 * task.getStart(TaskField.TEXT1.);
 * 
 * String alias = "ACNT Jira Key"; // FieldType test = //
 * projectFile.getCustomFields().getFieldByAlias(FieldTypeClass.TASK, // alias);
 * // task.setCustomField(test, new String("ABCD") );
 * 
 * // TaskField field = (TaskField) task.getFieldByAlias(alias); //
 * System.out.println("field value: "+field.toString()); //
 * //field.setValue("1234"); // for(i=1; i<31; i++) { //
 * System.out.println("Text " + i + ": " + task.getText(i)); // Object fieldtype
 * = task.getFieldByAlias("FIELD10"); // }
 * 
 * Class c = task.getClass(); int colNum = 0; for (Method m :
 * c.getDeclaredMethods()) { String mname = m.getName();
 * System.out.println(mname); // if (m.getName().startsWith("get")) { // //
 * out.format("invoking %s()\n", mname); // try { // m.setAccessible(true); //
 * Object o = m.invoke(task); // // out.format("%s() returned %b%n", mname, //
 * // o.toString() ); // // out.println(o); // out.format("%s() returned %s\n",
 * mname, o == null ? // "null" : o.toString()); // // Handle any exceptions
 * thrown by method to be // //
 * row.createCell(colNum++).setCellValue(o.toString() != //
 * null?o.toString():"null"); // // // invoked. // } catch (NullPointerException
 * x) { // Throwable cause = x.getCause(); //
 * err.format("invocation of %s failed: %s\n", mname, // cause.getMessage()); //
 * } catch (InvocationTargetException x) { // Throwable cause = x.getCause(); //
 * err.format("invocation of %s failed: %s\n", mname, // cause.getMessage()); //
 * } catch (IllegalAccessException x) { // Throwable cause = x.getCause(); //
 * err.format("invocation of %s failed: %s\n", mname, // cause.getMessage()); //
 * } catch (IllegalArgumentException x) { // Throwable cause = x.getCause(); //
 * err.format("invocation of %s failed: %s\n", mname, // "Illegal Argument"); //
 * } // } }
 * 
 * int j = 10; System.out.println("Text " + j + ": " + task.getText(j));
 * task.setText(10, "woo hoo"); System.out.println("Text " + j + ": " +
 * task.getText(j));
 * 
 * Object fieldtype = task.getFieldByAlias("JIRA ID");
 * System.out.println(fieldtype);
 * 
 * task.setFieldByAlias(alias, new String("ABCD"));
 * 
 * // FieldType // TaskField tf = new TaskField(); // task.set(alias, "arg1");
 * System.out.printf("%s : %s\n", alias, task.getFieldByAlias(alias));
 * 
 * task.setFieldByAlias(alias, 1234); System.out.printf("%s : %s\n", alias,
 * task.getFieldByAlias(alias));
 * 
 * // for (CustomField customField : projectFile.getCustomFields()) // { // //
 * System.out.println("Alias:|"+customField.getAlias()+"|"); // // 1225038340 /
 * net.sf.mpxj.CustomField@49049a04 / ACNT Jira // Key (Unknown
 * TaskField(32834)) // //if (!customField.getAlias().equalsIgnoreCase("ACNT
 * Jira // Key")) // // continue; //
 * //System.out.println(task.getName().toString()); // // // if(
 * task.getFieldByAlias(customField.getAlias()) != null // // ) //
 * System.out.printf("%s : %s\n", customField.getAlias(), //
 * task.getFieldByAlias(customField.getAlias())); // }
 */
