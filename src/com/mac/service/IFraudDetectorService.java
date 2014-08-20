package com.mac.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface IFraudDetectorService {
	List<String> getFraudulentList() throws ParseException;
	void setTrnList(List<String> trnList);
	void setPriceThreshold(BigDecimal priceThreshold);
}
