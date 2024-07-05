package com.view;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
public class EmployeeOperation {
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		SessionFactory sessionFactory=HibernateUtil.getSesFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Criteria criteria=session.createCriteria(Employee.class);
		List<Employee>empList=criteria.list();
		for(Employee emp:empList)
		{
			System.out.println("ID="+emp.getId()+emp.getName()+emp.getSalary());
		}
		criteria=session.createCriteria(Employee.class).add(Restrictions.eq("id", new Integer(2)));
		Employee emp=(Employee) criteria.uniqueResult();
		System.out.println("Name="+emp.getName() );
		empList=session.createCriteria(Employee.class)
				.addOrder(Order.desc("id"))
				.setFirstResult(0)
				.setMaxResults(2)
				.list();
		for(Employee emp4: empList)
		{
			System.out.println("Paginated Employees"+emp4.getId()+ emp4.getName());
		}
		empList=session.createCriteria(Employee.class)
				.add(Restrictions.like("name","_a%"))
				.list();
		for(Employee emp4:empList)
		{
			System.out.println("Employee having 'm' in name::"+emp4.getName());
		}
		Criteria c=session.createCriteria(Employee.class);
		c.setProjection(Projections.rowCount());
		empList=c.add(Restrictions.like("name","%m%")).list();
		System.out.println("Number of Employees wiht m in name="+empList.get(0));
		Criteria d=session.createCriteria(Employee.class);
		empList=d.setProjection(Projections.sum("salary")).list();
		System.out.println("Sum fo Salaries="+empList.get(0));
		tx.commit();
		sessionFactory.close();
		}
}