package Adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import beans.Category;

public class CategoryAdapter extends XmlAdapter<String, Category> {

	@Override
	public String marshal(Category category) throws Exception {
		return Category.getString(category);
	}

	@Override
	public Category unmarshal(String string) throws Exception {
		return Category.getType(string);
	}

}
