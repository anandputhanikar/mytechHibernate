package com.mytech.hibernate;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.mytech.hibernate.dto.Address;
import com.mytech.hibernate.dto.EmployeeEntity;

public class TestHibernate {

	public static void main(String[] args) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			// Session session = new Configuration()

			session.beginTransaction();

			// Add new Employee object
			EmployeeEntity emp = new EmployeeEntity();
			emp.setEmail("Raj@gmail.com");
			emp.setFirstName("Raj");
			emp.setLastName("J");
			emp.setJoinedDate(new Date());

			EmployeeEntity emp2 = new EmployeeEntity();
			emp2.setEmail("Janavi@gmail.com");
			emp2.setFirstName("Janavi");
			emp2.setLastName("K");
			emp2.setJoinedDate(new Date());
			
			Address address = new Address();
			address.setStreet("40th cross");
			address.setCity("Hubballi");
			address.setState("Karnataka");
			address.setPincode("560040");

			Address address2 = new Address();
			address2.setStreet("Lavelle road");
			address2.setCity("Banglore");
			address2.setState("Karnataka");
			address2.setPincode("560001");

			emp.getListOfAddresses().add(address);
			emp.getListOfAddresses().add(address2);
			
			emp2.getListOfAddresses().add(address);
			emp2.getListOfAddresses().add(address2);

//			session.save(emp);
//			session.save(emp2);

			session.getTransaction().commit();
			emp = null;
			emp = (EmployeeEntity) session.get(EmployeeEntity.class, 1);
			System.out.println("Employe name recieved is :" + emp.getListOfAddresses().size());
			HibernateUtil.shutdown();
			session.close();

		} catch (HibernateException e) {
			System.out.println("HibernateException: " + e);
		} catch (Exception e) {
			System.out.println("Excep: " + e);
		}
	}
}