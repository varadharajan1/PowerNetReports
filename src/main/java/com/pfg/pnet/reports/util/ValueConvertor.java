package com.pfg.pnet.reports.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValueConvertor {

	private static final Logger logger = Logger.getLogger(ValueConvertor.class.getName());
	private ValueConvertor() { }
	
	public static long convertToLong(String date){
		long result = 0;
		logger.log(Level.INFO, "ValueConvertor: convertToLong: {0}", date );
		if (Validator.isValidDate(date)) {
			try {
				SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date myDate = newDateFormat.parse(date);
				
				newDateFormat.applyPattern("yyyyMMdd");
				String myDateString = newDateFormat.format(myDate);
				
				result = Long.parseLong(myDateString);
			} catch (ParseException e) {
				logger.log(Level.INFO, "ValueConvertor: convertToLong; error while converting the date: {0}", date );
				logger.log(Level.INFO, "ValueConvertor: {0}", e.getMessage());
			}
		}
		logger.log(Level.INFO, "ValueConvertor: convertToLong; result: {0}", result );
		return result;
	}
	
	public static String convertToDateString(long date){
		String result = "";
		logger.log(Level.INFO, "ValueConvertor: convertToDateString: {0}", date );
		if (date > 0) {
			try {
				SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date myDate = newDateFormat.parse(""+date);
				
				result = newDateFormat.format(myDate);

			} catch (ParseException e) {
				logger.log(Level.INFO, "ValueConvertor: convertToDateString; error while converting the date: {0}", date );
				logger.log(Level.INFO, "ValueConvertor: {0}", e.getMessage());
			}
		}
		logger.log(Level.INFO, "ValueConvertor: convertToDateString: result: {0}", result );
		return result;
	}

	public static int convertToInt(String numbers){
		int result = 0;
		logger.log(Level.INFO, "ValueConvertor: convertToInt; numbers: {0}", numbers );
		if (Validator.isAllNumbers(numbers)) {
			result = Integer.parseInt(numbers);
		}
		logger.log(Level.INFO, "ValueConvertor: convertToInt; result: {0}", result );
		return result;
	}
    
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
      
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
}
