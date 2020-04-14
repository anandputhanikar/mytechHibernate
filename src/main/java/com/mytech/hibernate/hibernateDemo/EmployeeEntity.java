package com.mytech.hibernate.hibernateDemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
// @org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL)
@Table(name = "EMP", uniqueConstraints = { @UniqueConstraint(columnNames = "ID"),
		@UniqueConstraint(columnNames = "EMAIL") })
public class EmployeeEntity {

	// private static final long serialVersionUID = -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private int employeeId;

	/**
	 * we can user @EmbeddedId instead of @Id, @GeneratedValue and @Embedded
	 * (since using object), if the class primary key would be an another object
	 * for eg: @EmbeddedId private Address address; if so then address object
	 * will be primary key and if any duplicate records inserted in address
	 * object then it says this field must be unique because it's primary key.
	 */

	@Column(name = "EMAIL", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "FIRST_NAME", unique = false, nullable = false, length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", unique = false, nullable = false, length = 100)
	private String lastName;

	@Temporal(TemporalType.DATE)
	private Date joinedDate;

	/**
	 * (1) if this employee require two different address for home and office,
	 * then we need to override column name in address for we can achieve this
	 * by attributeOverride annotation. @Embedded here it's optional for which
	 * "address" value object has been mentioned as @Embeddable annotation
	 */

	/*
	 * @Embedded
	 * 
	 * @AttributeOverrides({
	 * 
	 * @AttributeOverride(name="street",
	 * column=@Column(name="HOME_STREET_NAME")),
	 * 
	 * @AttributeOverride(name="city", column=@Column(name="HOME_CITY_NAME")),
	 * 
	 * @AttributeOverride(name="state", column=@Column(name="HOME_STATE_NAME")),
	 * 
	 * @AttributeOverride(name="pincode", column=@Column(name="HOME_PIN_CODE"))
	 * }) private Address homeAddress;
	 * 
	 * @Embedded private Address officeAddress;
	 */

	/**
	 * (2) If want list of address and created separate table in db, in order do
	 * this create list of address attribute and annotate
	 * with @CollectionOfElements - where @ElementCollection will
	 * mappingException. It create new table with primary key as ;;
	 * Emp_listOfAddresses (Emp_ID, ,,, So we can user joinTable & joincolumns
	 * to for custom name.
	 * 
	 * (3)In order to have identifier for value object i.e Address then will use @CollectionId
	 * "hilo" is type of generator which provided by hibernate.
	 */

	@CollectionOfElements
	@JoinTable(name="EMPLOYEE_ADDRESS",
	 joinColumns=@JoinColumn(name="EMPLOYEE_ID"))
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = {@Column(name="ADDRESS_ID")}, generator = "hilo-gen", type = @Type(type="long"))
	private Collection<Address> listOfAddresses = new ArrayList();

	public Collection<Address> getListOfAddresses() {
		return listOfAddresses;
	}

	public void setListOfAddresses(Collection<Address> listOfAddresses) {
		this.listOfAddresses = listOfAddresses;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}