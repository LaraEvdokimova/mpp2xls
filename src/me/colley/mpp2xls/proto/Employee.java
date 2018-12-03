package me.colley.mpp2xls.proto;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String firstName;
	private String lastName;
	private Department department;

	public Employee() {
		super();
		System.out.println("Employee.java init without params");
	}

	public Employee(int id, String fName, String lName, Department department) {
		super();
		System.out.println("Employee.java init with params");
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
		this.department = department;
	}

	// Setters and Getters
	public void setId(Integer val) {
		this.id = val;
	}
	public void setFirstName(String val) {
		this.firstName = val;
	}
	public void setLastName(String val) {
		this.lastName = val;
	}
	public void setDepartment(Department val) {
		this.department = val;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ",lastName=" + lastName + ", department="
				+ department + "]";
	}
}