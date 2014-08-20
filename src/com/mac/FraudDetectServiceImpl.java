package com.mac;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.util.List;

import com.mac.service.IFraudDetectorService;

public class FraudDetectServiceImpl {

	private static IFraudDetectorService fDetector;

	public static List<String> getCreditCardFraudulentList(List<String>trnList, Date date, BigDecimal priceThreshold) throws ParseException{
		fDetector=new CreditCardFraudDetector(trnList, date, priceThreshold);
		return fDetector.getFraudulentList();
	}
	
}