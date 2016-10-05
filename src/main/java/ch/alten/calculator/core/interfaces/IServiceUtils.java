package ch.alten.calculator.core.interfaces;

public interface IServiceUtils {
	
	public enum ProductTypes {
		BOOK, FOOD, MEDICAL, OTHER
	}
	public static double TAXES_RATE = 0.10;
	public static double IMPORTED_TAXES_RATE = 0.05;
	public static int DEFAULT_FILE_SELECTED = 1;
	
	public boolean isModulusOperatorEqualToZero (double value);
}
