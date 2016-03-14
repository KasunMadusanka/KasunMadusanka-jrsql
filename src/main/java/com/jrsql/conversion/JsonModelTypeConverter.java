package com.jrsql.conversion;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import com.google.common.io.Files;
import com.rutledgepaulv.github.argconverters.StringToQueryValueConverter;
import com.rutledgepaulv.github.structs.ConversionInfo;
import com.rutledgepaulv.github.structs.Lazy;

import mjson.Json;

/**
 * 
 * @author kmadusanka [kmadusanka@virtusa.com]
 *
 * Convert RSQL query String values to relevant types mapped with the given Json Sample Model
 *
 */
public class JsonModelTypeConverter implements StringToQueryValueConverter {

	private ConversionService conversionService;
	private Json jsonSample;
	
	/**
	 * Using DefaultConversionService with given json data file
	 * @param jsonFile
	 * @throws IOException 
	 */
	public JsonModelTypeConverter(File jsonFile) throws IOException {
		this.conversionService = new DefaultConversionService();
		this.jsonSample = Json.read(Files.toString(jsonFile, java.nio.charset.Charset.forName("UTF-8")));
	}
	
	/**
	 * 
	 * @param jsonFile
	 * @param conversionService
	 * @throws IOException 
	 */
	public JsonModelTypeConverter(File jsonFile, ConversionService conversionService) throws IOException {
		this.conversionService = conversionService;
		this.jsonSample = Json.read(Files.toString(jsonFile, java.nio.charset.Charset.forName("UTF-8")));
	}
	
	@Override
	public Lazy<Object> convert(ConversionInfo info) {
		return Lazy.fromFunc(() -> {

            String[] pathToField = info.getPathToField().split(".");
            Json item = jsonSample;
            for (String path : pathToField) {
				item = item.at(path);
			}
            
            Class<?> targetType;
            
			if(item.isString())
				targetType = String.class;
			else if(item.isNumber())
				targetType = Integer.class;
			else if(item.isBoolean())
				targetType = Boolean.class;
			else if(item.isArray())
				targetType = List.class;
			else if(item.isObject())
				targetType = Object.class;
			else
				return new Exception("Value type of " + item + " cannot convert to supported types!");
            
            return convert(info, targetType);
        });
	}

	
	/**
	 * Convert object type to targetType by using given conversion service
	 * @param info
	 * @param targetType
	 * @return
	 */
	private Object convert(ConversionInfo info, Class<?> targetType) {
        if(!conversionService.canConvert(info.getArgument().getClass(), targetType)) {
            throw new UnsupportedOperationException("Cannot convert " + info.getArgument() + "into type " + targetType.getSimpleName());
        }
        try {
            return conversionService.convert(info.getArgument(), targetType);
        }catch(Exception e) {
            throw new UnsupportedOperationException("Cannot convert " + info.getArgument() + "into type " + targetType.getSimpleName());
        }
    }
}
