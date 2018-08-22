package com.niit.dao;

import java.util.List;

import com.niit.model.Items;

public interface ItemDAO {
	public boolean addItems(Items item);
	public boolean deleteItems(Items item);
	public boolean updateItems(Items item);
	public Items getItems(int itemId);
	public List<Items> listItems();

}
