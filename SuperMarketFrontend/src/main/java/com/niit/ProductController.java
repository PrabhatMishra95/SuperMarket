package com.niit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.niit.dao.CategoryDAO;
import com.niit.dao.ItemDAO;
import com.niit.model.Category;
import com.niit.model.Items;

@Controller
public class ProductController 
{

	@Autowired
	ItemDAO itemDAO;
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@RequestMapping(value="/product")
	public String showProductPage(Model m)
	{
		Items items=new Items();
		m.addAttribute(items);
		
		m.addAttribute("categoryList", this.getCategories());
		
		return "Product";
	}
	
	@RequestMapping(value="/InsertItems",method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("items") Items items,@RequestParam("pimage") MultipartFile itemImage,Model m)
	{
		itemDAO.addItems(items);
		
		Items items1=new Items(); 
		m.addAttribute(items1);
		
		/*Image Insertion*/ 
		
		String path="C:\\Users\\Po!$on\\git\\SuperMarket\\SuperMarketFrontend\\src\\main\\webapp\\resources\\images\\";
		path=path+String.valueOf(items.getItemId())+".jpg";
		File filepath=new File(path);
		
		if(!itemImage.isEmpty())
		{
			try
			{
				byte[] buffer=itemImage.getBytes();
				FileOutputStream fos=new FileOutputStream(filepath);
				BufferedOutputStream bs=new BufferedOutputStream(fos);
				bs.write(buffer);
				bs.close();
			}
			catch(Exception e)
			{
				m.addAttribute("errorInfo", "There is Exception:"+e.getMessage());
			}
		}
		else
		{
			m.addAttribute("errorInfo", "There is System Problem");
		}
		
		
		/*End of Image Insertion*/
		
		return "index";
	}
	
	@RequestMapping(value="/itemPage",method=RequestMethod.GET)
	public String displayItemPage(Model m)
	{
		List<Items> listItems=itemDAO.listItems();
		m.addAttribute("listItems", listItems);
		
		return "ProductPage";
	}
	
	@RequestMapping(value="/itemDesc/{itemId}",method=RequestMethod.GET)
	public String displayItemDesc(@PathVariable("itemId")int itemId,Model m)
	{
		Items items=(Items)itemDAO.getItems(itemId);
		m.addAttribute("ProductInfo",items);
		return "ProductDesc";
	}
	
	public LinkedHashMap<Integer,String> getCategories()
	{
		List<Category> listCategories=categoryDAO.getCategories();
		
		LinkedHashMap<Integer,String> categoryList=new LinkedHashMap<Integer,String>();
		
		for(Category category:listCategories)
		{
			categoryList.put(category.getCategoryId(), category.getCategoryName());
		}
		
		return categoryList;
	}
	
}