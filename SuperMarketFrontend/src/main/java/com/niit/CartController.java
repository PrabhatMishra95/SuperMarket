package com.niit;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niit.dao.CartDAO;
import com.niit.dao.ItemDAO;
import com.niit.model.Cart;
import com.niit.model.Items;


@Controller
public class CartController 
{
	@Autowired
	CartDAO cartDAO;
	
	@Autowired
	ItemDAO itemDAO;
	
	@RequestMapping(value="/showCart")
	public String showCart(Model m,HttpSession session)
	{
		String username=(String)session.getAttribute("username");
		List<Cart> listCartItems=cartDAO.listCart(username);
		
		m.addAttribute("listCartItems", listCartItems);
		m.addAttribute("totalCost", this.calculateTotalCost(listCartItems));
		session.setAttribute("CartItems",listCartItems.size());
		
		return "Cart";
	}
	
	@RequestMapping(value="/addToCart/{itemId}")
	public String addToCart(@PathVariable("itemId") int itemId,@RequestParam("quantity")int quantity,HttpSession session,Model m)
	{
		
		Cart cartItem=new Cart();
		Items items=itemDAO.getItems(itemId);
		String username=(String)session.getAttribute("username");
		
		cartItem.setItemId(itemId);
		cartItem.setItemName(items.getItemName());
		cartItem.setQuantity(quantity);
		cartItem.setPrice(items.getPrice());
		cartItem.setStatus("NP");
		cartItem.setUsername(username);
		
		cartDAO.addToCart(cartItem);
		
		List<Cart> listCartItems=cartDAO.listCart(username);
		
		m.addAttribute("listCartItems", listCartItems);
		m.addAttribute("totalCost", this.calculateTotalCost(listCartItems));
		session.setAttribute("CartItems",listCartItems.size());
		
		return "Cart";
	}
	
	@RequestMapping(value="/deleteCartItem/{cartItemId}")
	public String deleteCartItem(@PathVariable("cartItemId")int cartItemId,Model m,HttpSession session)
	{
		
		Cart cartItem=cartDAO.getCart(cartItemId);
		cartDAO.deleteFromCart(cartItem);
		
		String username=(String)session.getAttribute("username");
		List<Cart> listCartItems=cartDAO.listCart(username);
		
		m.addAttribute("listCartItems", listCartItems);
		m.addAttribute("totalCost", this.calculateTotalCost(listCartItems));
		session.setAttribute("CartItems",listCartItems.size());
		
		return "Cart";
	}
	
	@RequestMapping(value="/updateCartItem/{cartItemId}")
	public String updateCartItem(@PathVariable("cartItemId") int cartItemId,@RequestParam("qty") int quantity,Model m,HttpSession session)
	{

		Cart cartItem=cartDAO.getCart(cartItemId);
		cartItem.setQuantity(quantity);
		cartDAO.updateCart(cartItem);
		
		String username=(String)session.getAttribute("username");
		List<Cart> listCartItems=cartDAO.listCart(username);
		
		m.addAttribute("listCartItems", listCartItems);
		m.addAttribute("totalCost", this.calculateTotalCost(listCartItems));
		session.setAttribute("CartItems",listCartItems.size());
		
		return "Cart";
		
	}
	public int calculateTotalCost(List<Cart> cartItems)
	{
		int totalCost=0;
		int count=0;
		
		while(count<cartItems.size())
		{
			totalCost=totalCost+(cartItems.get(count).getPrice()*cartItems.get(count).getQuantity());
			count++;
		}
		
		return totalCost;
	}
}