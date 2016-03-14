package com.jrsql.conversion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.support.DefaultConversionService;

import com.rutledgepaulv.github.argconverters.OperatorSpecificConverter;
import com.rutledgepaulv.github.argconverters.StringToQueryValueConverter;
import com.rutledgepaulv.github.argconverters.NoOpConverter;

public class JsonModelConversionService extends DefaultConversionService {
	public static List<StringToQueryValueConverter> getConverters(File jsonModelFile) throws IOException {
		List<StringToQueryValueConverter> converters = new ArrayList<StringToQueryValueConverter>();
		converters.add(new OperatorSpecificConverter());
		converters.add(new JsonModelTypeConverter(jsonModelFile));
		converters.add(new NoOpConverter());
		return converters;
	}
}
