package com.checkout;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.product.Product;
import com.promotion.Promotion;
import com.testdata.TestData;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckoutTests {

	@Test
	public void checkoutTestCreateObjectSuccess() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestRandomProductPromotion());
		
		// Test
		Checkout checkout = new Checkout(promotions);
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getPromotions(), Is.is(promotions));
	}
	
	@Test
	public void checkoutTestCreateObjectSuccessNullPromotions() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = null;
		
		// Test
		Checkout checkout = new Checkout(promotions);
		
		// Results
		assertNotNull(checkout);
	}
	
	@Test
	public void checkoutTestCreateObjectSuccessEmptyPromotions() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		
		// Test
		Checkout checkout = new Checkout(promotions);
		
		// Results
		assertNotNull(checkout);
	}
	
	@Test
	public void checkoutTestScanProductSuccessOneProduct() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestRandomProductPromotion());
		Checkout checkout = new Checkout(promotions);
		Product testProduct = TestData.generateTestProduct001();
		
		// Test
		checkout.scan(testProduct);
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getBasket().get(testProduct), Is.is(1));
	}
	
	@Test
	public void checkoutTestScanProductSuccessMultipleProducts() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestRandomProductPromotion());
		Checkout checkout = new Checkout(promotions);
		Product testProduct = TestData.generateTestProduct001();
		Random random = new Random();
		int productCount = random.nextInt(5) + 1;
		
		// Test
		for (int i = 0; i < productCount; i++) {
			checkout.scan(testProduct);
		}
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getBasket().get(testProduct), Is.is(productCount));
	}
	
	@Test
	public void checkoutTestRemoveProductSuccess() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestRandomProductPromotion());
		Checkout checkout = new Checkout(promotions);
		Product testProduct = TestData.generateTestProduct001();
		checkout.scan(testProduct);
		checkout.scan(testProduct);
		
		// Test
		checkout.removeProduct(testProduct);
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getBasket().get(testProduct), Is.is(1));
	}
	
	@Test
	public void checkoutTestRemoveProductFailure() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestRandomProductPromotion());
		Checkout checkout = new Checkout(promotions);
		Product testProduct = TestData.generateTestProduct001();
		checkout.scan(testProduct);
		
		// Test
		checkout.removeProduct(testProduct);
		
		// Results
		assertNotNull(checkout);
		assertNull(checkout.getBasket().get(testProduct));
	}
}
