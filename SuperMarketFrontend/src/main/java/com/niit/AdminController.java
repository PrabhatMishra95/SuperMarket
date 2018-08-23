package com.niit;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.dao.CategoryDAO;
import com.niit.dao.ItemDAO;
import com.niit.model.Category;
import com.niit.model.Items;

@Controller
public class AdminController {

	
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	ItemDAO itemDAO;
	
	@RequestMapping(value="/admin")
	public String adminPage(@ModelAttribute("category")Category category){
		return "adminadd";
	}
	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public ModelAndView saveCategoryData(@ModelAttribute("category") Category category) {
		ModelAndView mv = new ModelAndView();
		categoryDAO.addCategory(category);
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value="/proupdate",method=RequestMethod.GET)
	public String displayProductPage(Model m)
	{
		List<Items> listItems=itemDAO.listItems();
		m.addAttribute("listItems", listItems);
		
		return "proupdate";
	}
	
	@RequestMapping(value="/updateItem/{itemId}")
	public String updateCartItem(@PathVariable("itemId") int itemId,@RequestParam("price") int price,HttpSession session,Model m)
	{

		Items items=itemDAO.getItems(itemId);
		items.setPrice(price);
		itemDAO.updateItems(items);

		List<Items> listItems=itemDAO.listItems();
		m.addAttribute(listItems);
		return "proupdate";
		
	}
	
	@RequestMapping(value="/deleteItem/{itemId}")
	public String deleteProduct(@PathVariable("itemId")int itemId,HttpSession session,Model m)
	{
		
		Items items=itemDAO.getItems(itemId);
		itemDAO.deleteItems(items);
		
		List<Items> listItems=itemDAO.listItems();
		m.addAttribute(listItems);
		return "proupdate";
	}
}