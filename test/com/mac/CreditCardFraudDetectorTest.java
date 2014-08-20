package com.mac;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mac.FraudDetectServiceImpl;

public class CreditCardFraudDetectorTest {

	SimpleDateFormat sdf;
	List<String> trnList;
	
	@Before
	public void setUp() throws Exception {
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		trnList = new ArrayList<>();
	}

	@Test
	@Ignore
	public void testGetFraudulentListPrice() throws ParseException {
		Date date=sdf.parse("2014-04-29");
		BigDecimal priceThreshold=new BigDecimal("1000");
		trnList = new ArrayList<>();
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e1, 2014-04-29T13:15:54, 500");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e1, 2014-04-29T13:15:54, 500.000001");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 010.00001");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 990");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 1000");
		Assert.assertSame(Arrays.asList("10d7ce2f43e35fa57d1bbf8b1e2","10d7ce2f43e35fa57d1bbf8b1e1"), 
				FraudDetectServiceImpl.getCreditCardFraudulentList(trnList, date, priceThreshold));
	}
	
	
	@Test
	public void testGetFraudulentListDate() throws ParseException {
		Date date=sdf.parse("2014-04-29");
		BigDecimal priceThreshold=new BigDecimal("1000");
		trnList = new ArrayList<>();
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e1, 2010-03-29T13:15:54, 2000.000001");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 010.00001");
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 990");
		Assert.assertEquals(Arrays.asList("10d7ce2f43e35fa57d1bbf8b1e2"), 
				FraudDetectServiceImpl.getCreditCardFraudulentList(trnList, date, priceThreshold));
	}
	
	
	@Test(expected = ParseException.class) 
	@Ignore
	public void testParseException() throws ParseException {
		Date date=sdf.parse("2014-04-29");
		BigDecimal priceThreshold=new BigDecimal("1000");
		trnList = new ArrayList<>();
		trnList.add("10d7ce2f43e35fa57d1bbf8b1e1, A010-03-29T13:15:54, 2000.000001");
		FraudDetectServiceImpl.getCreditCardFraudulentList(trnList, date, priceThreshold);
	}
}
