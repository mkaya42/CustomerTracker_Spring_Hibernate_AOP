package com.mky.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mky.springdemo.entity.Customer;
import com.mky.springdemo.util.SortUtils;

/*There will be some crud operations via objects*/
@Repository
public class CustomerDAOImpl implements CustomerDAO {

	/* SessionFactory injected */
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		/* Create Session */
		Session currentSession = sessionFactory.getCurrentSession();
		/* Create Query */
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by last_name asc", Customer.class);

		/* Execute query and get result list and return */
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		Session currentSession = sessionFactory.getCurrentSession();
		// currentSession.save(theCustomer);
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("delete from Customer where id =:customerId");
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> getCustomers(String theSearchName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = null;

		if (theSearchName != null && theSearchName.trim().length() > 0) {

			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

		} else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

	@Override
	public List<Customer> getCustomers(int theSortField) {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		String theFieldName = null;
		switch (theSortField) {
		case SortUtils.FIRST_NAME:
			theFieldName = "firstName";
			break;
		case SortUtils.LAST_NAME:
			theFieldName = "lastName";
			break;
		case SortUtils.EMAIL:
			theFieldName = "email";
			break;
		default:
			// if nothing matches the default to sort by lastName
			theFieldName = "lastName";
		}
		String queryString = "from Customer order by " + theFieldName;
		Query<Customer> theQuery =currentSession.createQuery(queryString, Customer.class);
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

}
