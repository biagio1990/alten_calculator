package ch.alten.calculator;

import java.text.DecimalFormat;

import ch.alten.calculator.core.ServiceUtils;
import ch.alten.calculator.core.exceptions.CalculatorInstanceException;
import ch.alten.calculator.core.interfaces.IServiceUtils;
import ch.alten.calculator.core.interfaces.IServiceUtils.ProductTypes;
import ch.alten.calculator.model.Product;
import ch.alten.calculator.model.ProductsMap;

/**
 * 
 * @author biagio.dangelo
 * 
 * Calculate sales taxes for a selected basket.
 * The rules are:
 * 		- Apply basic sales tax at a rate of 10% on all goods EXCEPT books, food, and medical products.
 * 		- Apply sales tax applicable on all imported goods at a rate of 5%, with NO EXCEPTIONS.
 * 		- Apply the rounding rules for sales tax nearest to 0.05.
 */
public class CalculateSalesTaxes {
	
	public static void main( String[] args ) {
       
		if (args != null && args.length > 0){
			
			/* Service Utils instance */
			IServiceUtils serviceUtils = new ServiceUtils();
			
			ProductsMap prodMap = new ProductsMap();
			double totalSalesTaxes = 0, totalTaxedPrices = 0;
			DecimalFormat dec = new DecimalFormat("0.00");
			/* Set a default vale in case of NaN input */
			int input = IServiceUtils.DEFAULT_FILE_SELECTED;
			
			try {
				input = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe){
				nfe.printStackTrace();
			}
		
			try {
				/*
				 * The first step is the creation of a Products Map, 
				 * like Shopping Basket in Java Object with a JAXB instance.
				 */
				prodMap = (ProductsMap) ServiceUtils.getJAXBInstanceFromFile("/basket_" + input + ".xml");
			} catch (CalculatorInstanceException e) {
				System.out.println("An Error occurred: " + e.getMessage());
			}
			
			for( Product product : prodMap.getProductsMap() ) {
				double importedSalesTaxes = 0, salesTaxes = 0;
				double productPrice = Double.parseDouble( product.getPrice().replace(",", ".") );
				
				if ( product.getQuantity() > 1 ){
					productPrice *= product.getQuantity();
				}
				
				ProductTypes productType;
				try {
					productType = ProductTypes.valueOf(product.getType());
				} catch (Exception e){
					/* 
					 * When user set an incorrect value of Product Type,
					 * set the default OTHER product type 
					 */
					productType = ProductTypes.OTHER;
				}
				
				switch (productType) {
				case BOOK:
					// DO NOTHING, No sales taxes to apply
					break;
				case FOOD:
					// DO NOTHING, No sales taxes to apply
					break;
				case MEDICAL:
					// DO NOTHING, No sales taxes to apply
					break;
				default:
					salesTaxes = ServiceUtils.returnCalculatedSalesTaxes( productPrice , IServiceUtils.TAXES_RATE);
					
					if ( ! serviceUtils.isModulusOperatorEqualToZero( salesTaxes )){
						double lastDecimalNum = ServiceUtils.getLastDecimalNumber( salesTaxes );
						if (ServiceUtils.isGreaterThanOrEqualToPointFive( lastDecimalNum )){
							salesTaxes = ServiceUtils.calculateDifferenceToUnit(lastDecimalNum, salesTaxes);
						} else {
							salesTaxes = ServiceUtils.calculateDifferenceToPointFive(lastDecimalNum, salesTaxes);
						}
					}
					
					break;
				}
				
				if (product.isImported()){
					importedSalesTaxes = ServiceUtils.returnCalculatedSalesTaxes( productPrice , IServiceUtils.IMPORTED_TAXES_RATE);
					if ( ! serviceUtils.isModulusOperatorEqualToZero( importedSalesTaxes )) {
						double lastDecimalNum = ServiceUtils.getLastDecimalNumber( importedSalesTaxes );
						if (ServiceUtils.isGreaterThanOrEqualToPointFive( lastDecimalNum )){
							importedSalesTaxes = ServiceUtils.calculateDifferenceToUnit(lastDecimalNum, importedSalesTaxes);
						} else {
							importedSalesTaxes = ServiceUtils.calculateDifferenceToPointFive(lastDecimalNum, importedSalesTaxes);
						}
					}
				}
				
				double taxedPrice = ServiceUtils.returnProductPriceTaxed( productPrice, salesTaxes, importedSalesTaxes );
				
				/* Sum of all prices taxed */
				totalTaxedPrices += taxedPrice;
				/* Sum of all sales taxes */
				totalSalesTaxes += salesTaxes + importedSalesTaxes;
				
				System.out.println(
						product.getQuantity()
						+ " " + product.getDescription()
						+ " " + dec.format( taxedPrice ));
			}
			
			System.out.println("Sales Taxes: " + dec.format(totalSalesTaxes) );
			System.out.println("Total: " + dec.format(totalTaxedPrices) );
		}
	}
}
