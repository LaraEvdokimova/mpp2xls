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
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskContainer;
import net.sf.mpxj.TaskField;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook; //.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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

import static java.lang.System.err;

public class TestOne extends Object {

	public static void xmlTest() {
		File xmlFile = new File("employee.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Employee.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Employee employee = (Employee) jaxbUnmarshaller.unmarshal(xmlFile);
			System.out.println(employee);
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

		String filename = prop.getProperty("infile");
		String outfile = prop.getProperty("outfile");
		// ProjectReader reader = new UniversalProjectReader();
		MPPReader reader = new MPPReader();
		reader.setPreserveNoteFormatting(true);
		ProjectFile projectFile = reader.read(filename);

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Contacts");

		// Row headerRow = sheet.createRow(0);
		// String[] columns = null;
		// for (int i = 0; i < columns.length; i++) {
		// Cell cell = headerRow.createCell(i);
		// cell.setCellValue(columns[i]);
		// //cell.setCellStyle(headerCellStyle);
		// }

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

		// Tables : Columns
		for (Table table : projectFile.getTables()) {
			out.println("Table: " + table.getName());
			for (Column col : table.getColumns()) {
				out.print("Col: " + col);
			}
		}

		// // Calendars : Links Resource to Task
		// ProjectCalendarContainer pcc = project.getCalendars();
		// Iterator<ProjectCalendar> pci = pcc.iterator();
		// for(int i = 0; pci.hasNext(); i++) {
		// //Task t = tc.get(i);
		// ProjectCalendar pc = pci.next();
		// System.out.println(pc.toString());
		// if(i>10) break;
		// }
		//
		//
		// Tasks
		TaskContainer tc = projectFile.getTasks(); // .forEach(null);
		System.out.println("Number of tasks: " + tc.size());

		int rowNum = 1;

		Iterator<Task> it = tc.iterator();
		for (int i = 0; it.hasNext(); i++) {
			// Task t = tc.get(i);
			Task task = it.next();
			// task.getEnterpriseCustomField(TaskField.TEXT1);

			// Row row = sheet.createRow(rowNum++);
			Row row = sheet.createRow(i + 1); // row 0 has headers

			if (task.getName().equalsIgnoreCase("7.1.2 Tax Accounting")) {
				// task.getStart(TaskField.TEXT1.);

				String alias = "ACNT Jira Key";
				// FieldType test =
				// projectFile.getCustomFields().getFieldByAlias(FieldTypeClass.TASK,
				// alias);
				// task.setCustomField(test, new String("ABCD") );

				// TaskField field = (TaskField) task.getFieldByAlias(alias);
				// System.out.println("field value: "+field.toString());
				// //field.setValue("1234");
				// for(i=1; i<31; i++) {
				// System.out.println("Text " + i + ": " + task.getText(i));
				// Object fieldtype = task.getFieldByAlias("FIELD10");
				// }

				Class c = task.getClass();
				int colNum = 0;
				for (Method m : c.getDeclaredMethods()) {
					String mname = m.getName();
					System.out.println(mname);
					// if (m.getName().startsWith("get")) {
					// // out.format("invoking %s()\n", mname);
					// try {
					// m.setAccessible(true);
					// Object o = m.invoke(task);
					// // out.format("%s() returned %b%n", mname,
					// // o.toString() );
					// // out.println(o);
					// out.format("%s() returned %s\n", mname, o == null ?
					// "null" : o.toString());
					// // Handle any exceptions thrown by method to be
					//
					// row.createCell(colNum++).setCellValue(o.toString() !=
					// null?o.toString():"null");
					//
					// // invoked.
					// } catch (NullPointerException x) {
					// Throwable cause = x.getCause();
					// err.format("invocation of %s failed: %s\n", mname,
					// cause.getMessage());
					// } catch (InvocationTargetException x) {
					// Throwable cause = x.getCause();
					// err.format("invocation of %s failed: %s\n", mname,
					// cause.getMessage());
					// } catch (IllegalAccessException x) {
					// Throwable cause = x.getCause();
					// err.format("invocation of %s failed: %s\n", mname,
					// cause.getMessage());
					// } catch (IllegalArgumentException x) {
					// Throwable cause = x.getCause();
					// err.format("invocation of %s failed: %s\n", mname,
					// "Illegal Argument");
					// }
					// }
				}

				int j = 10;
				System.out.println("Text " + j + ": " + task.getText(j));
				task.setText(10, "woo hoo");
				System.out.println("Text " + j + ": " + task.getText(j));

				Object fieldtype = task.getFieldByAlias("JIRA ID");
				System.out.println(fieldtype);

				task.setFieldByAlias(alias, new String("ABCD"));

				// FieldType
				// TaskField tf = new TaskField();
				// task.set(alias, "arg1");
				System.out.printf("%s : %s\n", alias, task.getFieldByAlias(alias));

				task.setFieldByAlias(alias, 1234);
				System.out.printf("%s : %s\n", alias, task.getFieldByAlias(alias));

				// for (CustomField customField : projectFile.getCustomFields())
				// {
				// // System.out.println("Alias:|"+customField.getAlias()+"|");
				// // 1225038340 / net.sf.mpxj.CustomField@49049a04 / ACNT Jira
				// Key (Unknown TaskField(32834))
				// //if (!customField.getAlias().equalsIgnoreCase("ACNT Jira
				// Key"))
				// // continue;
				// //System.out.println(task.getName().toString());
				//
				// // if( task.getFieldByAlias(customField.getAlias()) != null
				// // )
				// System.out.printf("%s : %s\n", customField.getAlias(),
				// task.getFieldByAlias(customField.getAlias()));
				// }
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
		// for (Task task : project.getTasks())
		// {
		// System.out.println("Task: " + task.getName() + " ID=" + task.getID()
		// + " Unique ID=" + task.getUniqueID());
		// }

		try {
			FileOutputStream fileOut = new FileOutputStream(outfile);
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws MPXJException {

//		xmlTest();
		// propertiesTest();

	} // main()
}
