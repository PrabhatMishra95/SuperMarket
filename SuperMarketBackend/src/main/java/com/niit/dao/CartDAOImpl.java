package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Cart;

@Repository("cartDAO")
@Transactional
public class CartDAOImpl implements CartDAO
{

	@Autowired
	SessionFactory sessionFactory; 
	
	
	public boolean addToCart(Cart cart) 
	{
		try
		{
		sessionFactory.getCurrentSession().save(cart);	
		return true;
		}
		catch(Exception e)
		{
		return false;	
		}
	}

	public boolean deleteFromCart(Cart cart) {
		try
		{
			sessionFactory.getCurrentSession().delete(cart);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public boolean updateCart(Cart cart) 
	{
		try
		{
			sessionFactory.getCurrentSession().update(cart);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public Cart getCart(int cartItemId) 
	{
		Session session=sessionFactory.openSession();
		Cart cartItem=(Cart)session.get(Cart.class,cartItemId);
		return cartItem;
	}

	@SuppressWarnings("unchecked")
	public List<Cart> listCart(String username) 
	{
		Session session=sessionFactory.openSession();
		List<Cart> listCartItems=(List<Cart>)session.createQuery("from Cart where username=:username and status='NP'").setParameter("username", username).list();
		return listCartItems;
	}

}
