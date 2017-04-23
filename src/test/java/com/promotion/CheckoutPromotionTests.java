package com.promotion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.ValidationException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.product.Product;
import com.testdata.TestData;

public class CheckoutPromotionTests {
	
	@Test
	public void checkoutPromotionTestCreateObjectSuccess() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(randomGenerator.nextInt(100));
		int percentageDiscount = randomGenerator.nextInt(100);
		
		// Test
		CheckoutPromotion checkoutPromotion = new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
	
		// Result
		assertNotNull(checkoutPromotion);
		assertThat(checkoutPromotion.getMinimumAmountSpent(), Is.is(minimumSpentAmount));
		assertThat(checkoutPromotion.getPercentageDiscount(), Is.is(percentageDiscount));
	}
	
	@Test
	public void checkoutPromotionTestCreateObjectFailureMinusPercentage() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(randomGenerator.nextInt(100));
		int percentageDiscount = -10;
		ValidationException validationException = null;
		
		// Test
		try {
			new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Result
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("PercentageDiscount should be over 0%"));
	}
	
	@Test
	public void checkoutPromotionTestCreateObjectFailureOneHundredPercentageOff() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(randomGenerator.nextInt(100));
		int percentageDiscount = 100;
		ValidationException validationException = null;
		
		// Test
		try {
			new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Result
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("PercentageDiscount should not be over 100%"));
	}
	
	@Test
	public void checkoutPromotionTestCreateObjectFailureMinusMinimumSpend() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(-randomGenerator.nextInt(100));
		int percentageDiscount = randomGenerator.nextInt(100);
		ValidationException validationException = null;
		
		// Test
		try {
			new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Result
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Minimum Amount Spent should be a positive number"));
	}
	
	@Test
	public void checkoutPromotionTestCreateObjectFailureNullMinimumSpend() throws ValidationException {
		
		// Setup
		Random randomGenerator = new Random();
		BigDecimal minimumSpentAmount = null;
		int percentageDiscount = randomGenerator.nextInt(100);
		ValidationException validationException = null;
		
		// Test
		try {
			new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		} catch (ValidationException e) {
			validationException = e;
		}
	
		// Result
		assertNotNull(validationException);
		assertThat(validationException.getMessage(), Is.is("Minimum Amount Spent should be a positive number"));
	}
	
	@Test
	public void checkoutPromotionTestProcessApplicablePromotion() throws ValidationException {
		
		// Setup
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(50);
		int percentageDiscount = 10;
		CheckoutPromotion checkoutPromotion = new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestProduct001(), 1);
		basket.put(TestData.generateTestProduct002(), 1);
		basket.put(TestData.generateTestProduct003(), 1);
		
		// Test
		BigDecimal processPromotionTotalCost = checkoutPromotion.processPromotion(null, basket);
		
		// Result
		assertNotNull(checkoutPromotion);
		assertThat(processPromotionTotalCost, Is.is(BigDecimal.valueOf(66.78)));	
	}

	
	@Test
	public void checkoutPromotionTestProcessNotApplicablePromotion() throws ValidationException {
		
		// Setup
		BigDecimal minimumSpentAmount = BigDecimal.valueOf(100);
		int percentageDiscount = 10;
		CheckoutPromotion checkoutPromotion = new CheckoutPromotion(minimumSpentAmount, percentageDiscount);
		
		Map<Product, Integer> basket = new HashMap<Product, Integer>();
		basket.put(TestData.generateTestProduct001(), 1);
		basket.put(TestData.generateTestProduct002(), 1);
		basket.put(TestData.generateTestProduct003(), 1);
		
		BigDecimal currentTotal = PromotionUtils.calculateCurrentTotalPrice(basket);
		
		// Test
		BigDecimal processPromotionTotalCost = checkoutPromotion.processPromotion(currentTotal, basket);
		
		// Result
		assertNotNull(checkoutPromotion);
		assertThat(processPromotionTotalCost, Is.is(currentTotal));	
	}
}
