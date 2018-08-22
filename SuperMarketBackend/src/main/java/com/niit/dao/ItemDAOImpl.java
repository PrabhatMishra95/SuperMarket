package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Items;

@Repository("itemDAO")
public class ItemDAOImpl implements ItemDAO {
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public boolean addItems(Items item) {
		try
		{
		sessionFactory.getCurrentSession().save(item);
		return true;
		}
		catch(Exception e)
		{
		System.out.println("Exception Arised:"+e);
		return false;
		}
	}

	@Transactional
	public boolean deleteItems(Items item) 
	{
		try
		{
		sessionFactory.getCurrentSession().delete(item);
		return true;
		}
		catch(Exception e)
		{
		return false;
		}
	}

	@Transactional
	public boolean updateItems(Items item) {
		try
		{
		sessionFactory.getCurrentSession().update(item);
		return true;
		}
		catch(Exception e)
		{
		return false;
		}
	}

	public Items getItems(int itemId) {
		Session session=sessionFactory.openSession();
		Items item=(Items)session.get(Items.class,itemId);
		session.close();
		return item;
	}

	@SuppressWarnings("unchecked")
	public List<Items> listItems() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Items");
		List<Items> listItems=query.list();
		session.close();
		return listItems;
	}

}
