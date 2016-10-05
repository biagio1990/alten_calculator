package ch.alten.calculator.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author biagio.dangelo
 *
 * This class represent the Shopping Basket.
 */

@XmlRootElement()
public class ProductsMap {
	
	private List<Product> products = new ArrayList<Product>();

	public List<Product> getProductsMap() {
		return products;
	}
	public void setProductsMap(List<Product> products) {
		this.products = products;
	}
}
