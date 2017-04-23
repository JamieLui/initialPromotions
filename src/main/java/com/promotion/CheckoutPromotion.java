package com.promotion;

import java.math.BigDecimal;
import java.util.Map;

import javax.xml.bind.ValidationException;

import com.product.Product;

public class CheckoutPromotion implements Promotion {
	
	private BigDecimal minimumAmountSpent;
	private int percentageDiscount;
	
	public CheckoutPromotion(final BigDecimal minimumAmountSpent, final int percentageDiscount) throws ValidationException {
		setMinimumAmountSpent(minimumAmountSpent);
		setPercentageDiscount(percentageDiscount);
	}
	
	public int getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(final int percentageDiscount) throws ValidationException {
		if (percentageDiscount < 0) {
			throw new ValidationException("PercentageDiscount should be over 0%");
		} else if (percentageDiscount >= 100) {
			throw new ValidationException("PercentageDiscount should not be over 100%");
		}
		
		this.percentageDiscount = percentageDiscount;
	}
	
	public BigDecimal getMinimumAmountSpent() {
		return minimumAmountSpent;
	}

	public void setMinimumAmountSpent(final BigDecimal minimumAmountSpent) throws ValidationException {
		if (minimumAmountSpent == null || minimumAmountSpent.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException("Minimum Amount Spent should be a positive number");
		}
		
		this.minimumAmountSpent = minimumAmountSpent;
	}
	

	@Override
	public BigDecimal processPromotion(final BigDecimal currentPrice, final Map<Product, Integer> basket) {
		
		BigDecimal totalPrice = PromotionUtils.calculateCurrentTotalPrice(currentPrice, basket);
		
		if (totalPrice.compareTo(getMinimumAmountSpent()) > 0){
			int multiplier = 100 - percentageDiscount;
			return totalPrice.multiply(BigDecimal.valueOf(multiplier)).divide(BigDecimal.valueOf(100));
		}
		
		return totalPrice;
	}
}
