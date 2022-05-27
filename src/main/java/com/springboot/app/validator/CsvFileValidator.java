package com.springboot.app.validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.util.ProductDetailsField;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class CsvFileValidator {
	
	public Boolean isTheFileValied(MultipartFile file) throws IOException
	{
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(false);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseRecords = parser.parseAllRecords(inputStream);
		Record record = parseRecords.get(0);
		String dummyString = record.toString();
		ProductDetailsField[] productDetailsFields = ProductDetailsField.values();
		for(ProductDetailsField field : productDetailsFields)
		{
			if(!dummyString.contains(field.toString()))
				return false;	
		}
		return true;	
	}
}
