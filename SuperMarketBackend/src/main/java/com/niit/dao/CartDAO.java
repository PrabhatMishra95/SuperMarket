package com.niit.dao;

import java.util.List;

import com.niit.model.Cart;

public interface CartDAO {
	public boolean addToCart(Cart cart);
	public boolean deleteFromCart(Cart cart);
	public boolean updateCart(Cart cart);
	public Cart getCart(int cartItemId);
	public List<Cart> listCart(String username);
}


