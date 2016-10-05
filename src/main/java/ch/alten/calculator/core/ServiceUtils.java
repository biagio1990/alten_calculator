package ch.alten.calculator.core;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ch.alten.calculator.CalculateSalesTaxes;
import ch.alten.calculator.core.exceptions.CalculatorInstanceException;
import ch.alten.calculator.core.interfaces.IServiceUtils;
import ch.alten.calculator.model.ProductsMap;

/**
 * 
 * @author biagio.dangelo
 *
 * Service Utils class that perform all the operations related to sales taxes and instance creation.
 */

public class ServiceUtils implements IServiceUtils{
	
	@Override
	public boolean isModulusOperatorEqualToZero (double value){
		/*
		 * In order to rounding at the nearest 0.05,
		 * check whether the value (multiplied for 100) is multiple of 5.
		 */
		return value * 100 % 5 == 0;
	}
	
	public static double returnCalculatedSalesTaxes(double price, double taxes){
		return Math.round( price * ( taxes * 100)) / 100.0;
	}
	
	public static double getLastDecimalNumber (double value){
		return Double.parseDouble(
				String.valueOf( value ).substring( 
						String.valueOf(value).indexOf(".") + 2));
	}
	
	public static boolean isGreaterThanOrEqualToPointFive (double value){
		return value >= 5.0;
	}
	
	public static double calculateDifferenceToUnit (double decimalNum, double value){
		
		/* As the function name says, we calculate the difference from value to 1
		 * For example: 
		 * 		value = 2.38
		 * 		difference = 
		 * 				2.38 + 
		 * 				( 10 - 8 ( the 8 is the last digit of 2.38) ) / 100)
		 * 		like 2.38 + (0.10 - 0.08) = 2.40
		 */
		double difference = value + ( (10 - decimalNum ) / 100);
		value = Math.round(difference * 100) / 100.0;
		
		return value;
	}
	
	public static double calculateDifferenceToPointFive (double decimalNum, double value){
		
		/* As the function name says, we calculate the difference from value to 0.5
		 * For example: 
		 * 		value = 2.33
		 * 		difference = 
		 * 				2.33 + 
		 * 				( 5 - 3 ( the 3 is the last digit of 2.33) ) / 100)
		 * 		like 2.33 + (0.05 - 0.03) = 2.35
		 */
		double difference = value + ( (5 - decimalNum ) / 100);
		value = Math.round(difference * 100) / 100.0;
		
		return value;
	}
	
	public static double returnProductPriceTaxed (double price, double taxes, double importTaxes){
		return price + taxes + importTaxes;
	}
	
	public static Object getJAXBInstanceFromFile (String files) 
			throws CalculatorInstanceException{
		
		try{
			/* Create new JAXB instance of ProductsMap */
			JAXBContext jaxbContext = JAXBContext.newInstance(ProductsMap.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			URL urlFile = CalculateSalesTaxes.class.getResource(files);
			File fi = null;
			try {
				fi = Paths.get(urlFile.toURI()).toFile();
			} catch (URISyntaxException e) {
				throw new CalculatorInstanceException(e.getMessage());
			}
			
			return jaxbUnmarshaller.unmarshal( fi );
		} catch (JAXBException e){
			throw new CalculatorInstanceException(e.getMessage());
		}
		
	}
}
