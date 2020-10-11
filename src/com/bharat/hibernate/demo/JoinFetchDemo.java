package com.bharat.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.bharat.hibernate.demo.entity.Course;
import com.bharat.hibernate.demo.entity.Instructor;
import com.bharat.hibernate.demo.entity.InstructorDetails;

public class JoinFetchDemo {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				                                    .addAnnotatedClass(InstructorDetails.class)
				                                    .addAnnotatedClass(Instructor.class)
				                                    .addAnnotatedClass(Course.class)
				                                    .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			int theId = 1;
			session.beginTransaction();
			Query<Instructor> query = session.createQuery("select i from Instructor i "
					                                     + "JOIN FETCH i.course "
					                                     + "where i.id=:theInstructorId", Instructor.class);
			query.setParameter("theInstructorId", theId);
			Instructor tempInstructor = query.getSingleResult();
			System.out.println("\n\n" + tempInstructor);
			session.getTransaction().commit();
			session.close();
			System.out.println("\n\n" + tempInstructor.getCourse());
			System.out.println("\n\nDone");
		}
		finally {
			session.close();
			factory.close();
		}
	}
}


