package ch.alten.calculator.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author biagio.dangelo
 *
 * This class represent the single Product of a Shopping Basket
 */

@XmlRootElement(name = "product")
public class Product {

	int quantity;
	String description;
	String price;
	String type;
	boolean imported;
	
	public boolean isImported() {
		return imported;
	}

	@XmlElement
	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public int getQuantity() {
		return quantity;
	}

	@XmlElement
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPrice() {
		return price;
	}

	@XmlElement
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
}
