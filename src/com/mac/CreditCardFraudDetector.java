package com.mac;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mac.service.IFraudDetectorService;

/**
 * @author Froilan
 *
 */
public class CreditCardFraudDetector implements IFraudDetectorService {
	
	public static final int CR_NUM=0, TSTAMP=1, PRICE=2, DATE=0;
	public static final String TOKEN="T";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	private List<String> trnList;
	private Date date;
	private BigDecimal priceThreshold;
	

	/**
	 * @param trnList
	 * @param date
	 * @param priceThreshold
	 */
	public CreditCardFraudDetector(List<String> trnList, Date date,
			BigDecimal priceThreshold) {
		this.trnList = trnList;
		this.date = date;
		this.priceThreshold = priceThreshold;
	}
	

	public List<String> getTrnList() {
		return trnList;
	}

	public void setTrnList(List<String> trnList) {
		this.trnList = trnList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPriceThreshold() {
		return priceThreshold;
	}

	public void setPriceThreshold(BigDecimal priceThreshold) {
		this.priceThreshold = priceThreshold;
	}



	@Override
	public List<String> getFraudulentList() throws ParseException {
		Set<String> fraudSet=new HashSet<>();
		Map<String, BigDecimal> trnPriceMap=new HashMap<>();
		
		for(String trn : trnList) {
			String[] trnDetails=trn.split(", ");
			String[] dateDetailStrings=trnDetails[TSTAMP].split(TOKEN);
			Date trnDate=this.sdf.parse((dateDetailStrings)[DATE]);
			
			if (!this.date.equals(trnDate))
				continue;
		      
			BigDecimal totalPrice;
			String crNum=trnDetails[CR_NUM], price=trnDetails[PRICE];
			
			if(trnPriceMap.get(crNum) != null) {
				totalPrice=trnPriceMap.get(crNum).add(new BigDecimal(price));
				trnPriceMap.put(crNum, totalPrice);
			} else {
				totalPrice=new BigDecimal(price);
				trnPriceMap.put(crNum, totalPrice);
			}

			if(totalPrice.compareTo(this.priceThreshold)>0) fraudSet.add(crNum);
		}
		
		return new ArrayList<>(fraudSet);
	}

}
