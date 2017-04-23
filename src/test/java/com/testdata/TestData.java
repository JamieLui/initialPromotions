package com.testdata;

import java.math.BigDecimal;
import java.util.Random;

import javax.xml.bind.ValidationException;

import com.product.Product;
import com.promotion.CheckoutPromotion;
import com.promotion.ProductPromotion;

public class TestData {
	
	public static Product generateTestProduct001() throws ValidationException {
		Product product = new Product("001", "Travel Card Holder", BigDecimal.valueOf(Double.parseDouble("9.25")));
		return product;
	}
	
	public static Product generateTestProduct002() throws ValidationException {
		Product product = new Product("002", "Personalised cufflinks", BigDecimal.valueOf(Double.parseDouble("45.00")));
		return product;
	}
	
	public static Product generateTestProduct003() throws ValidationException {
		Product product = new Product("003", "Kids T-shirt", BigDecimal.valueOf(Double.parseDouble("19.95")));
		return product;
	}
	
	public static ProductPromotion generateTestProductPromotion() throws ValidationException {
		return new ProductPromotion(generateTestProduct001(), 2, BigDecimal.valueOf(8.50));
	}
	
	public static CheckoutPromotion generateTestCheckoutPromotion() throws ValidationException {
		return new CheckoutPromotion(BigDecimal.valueOf(60), 10);
	}

	public static Product selectRandomTestProduct() throws ValidationException {
		
		Random random = new Random();
		int selectedTestProduct = random.nextInt(2);
		
		if (selectedTestProduct == 0) {
			return generateTestProduct001();
		} else if (selectedTestProduct == 1) {
			return generateTestProduct002();
		} else {
			return generateTestProduct003();
		}
	}
	
	public static ProductPromotion generateTestRandomProductPromotion() throws ValidationException {
		
		Random random = new Random();
		Product randomProduct = selectRandomTestProduct();
		BigDecimal randomPrice = BigDecimal.valueOf(random.nextInt(randomProduct.getPrice().intValue()) + 1);
		
		ProductPromotion productPromotion = new ProductPromotion(randomProduct, random.nextInt(5) + 1, randomProduct.getPrice().subtract(randomPrice));
		return productPromotion;
	}
}
