package com.checkout;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.product.Product;
import com.promotion.Promotion;
import com.testdata.TestData;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PromotionTests {
	
	@Test
	public void checkoutTestGetPromotionalTotalPromotionsTest1() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestProductPromotion());
		promotions.add(TestData.generateTestCheckoutPromotion());
		
		// Test
		Checkout checkout = new Checkout(promotions);
		checkout.scan(TestData.generateTestProduct001());
		checkout.scan(TestData.generateTestProduct002());
		checkout.scan(TestData.generateTestProduct003());
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getPromotionalTotal(), Is.is(BigDecimal.valueOf(66.78)));
	}
	
	@Test
	public void checkoutTestGetPromotionalTotalPromotionsTest2() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestProductPromotion());
		promotions.add(TestData.generateTestCheckoutPromotion());
		
		Product product001 = TestData.generateTestProduct001();
		
		// Test
		Checkout checkout = new Checkout(promotions);
		checkout.scan(product001);
		checkout.scan(TestData.generateTestProduct003());
		checkout.scan(product001);
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getPromotionalTotal(), Is.is(BigDecimal.valueOf(36.95)));
	}
	
	@Test
	public void checkoutTestGetPromotionalTotalPromotionsTest3() throws ValidationException {
		
		// Setup
		List<Promotion> promotions = new ArrayList<Promotion>();
		promotions.add(TestData.generateTestProductPromotion());
		promotions.add(TestData.generateTestCheckoutPromotion());
		Product product001 = TestData.generateTestProduct001();
		
		// Test
		Checkout checkout = new Checkout(promotions);
		checkout.scan(product001);
		checkout.scan(TestData.generateTestProduct002());
		checkout.scan(product001);
		checkout.scan(TestData.generateTestProduct003());
		
		// Results
		assertNotNull(checkout);
		assertThat(checkout.getPromotionalTotal(), Is.is(BigDecimal.valueOf(73.76)));
	}
}
