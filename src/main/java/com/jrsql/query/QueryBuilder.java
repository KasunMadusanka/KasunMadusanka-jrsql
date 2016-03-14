package com.jrsql.query;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.jrsql.conversion.JsonModelConversionService;
import com.rutledgepaulv.github.ComparisonToCriteriaConverter;
import com.rutledgepaulv.github.RsqlMongoAdapter;
import com.rutledgepaulv.github.argconverters.StringToQueryValueConverter;

public class QueryBuilder {
	
	private List<StringToQueryValueConverter> converters;
	private RsqlMongoAdapter adapter;
	
	/**
	 * Using JsonModelConversionService to convert
	 * @param jsonModelFile
	 */
	public QueryBuilder(File jsonModelFile) {
		try {
			this.converters = JsonModelConversionService.getConverters(jsonModelFile);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Using given converters
	 * @param converters
	 */
	public QueryBuilder(List<StringToQueryValueConverter> converters) {
		this.converters = converters;
		init();
	}
	
	private void init() {
		ComparisonToCriteriaConverter comCriConverter = new ComparisonToCriteriaConverter(converters);
		adapter = new RsqlMongoAdapter(comCriConverter);
	}
	
	public Query build(String rsqlString) {
		Criteria cri = adapter.getCriteria(rsqlString, null);
		return Query.query(cri);
	}
	
	public Query build(String rsqlString, int limit, int offset) {
		Query query = build(rsqlString);
		query.limit(limit);
		query.skip(offset);
		return query;
	}
	
	public Query build(String rsqlString, Sort.Direction orderDirerction, String... orderBy) {
		Query query = build(rsqlString);
		query.with(new Sort(orderDirerction, orderBy));
		return query;
	}
	
	public Query build(String rsqlString, int limit, int offset, Sort.Direction orderDirerction, String... orderBy) {
		Query query = build(rsqlString, limit, offset);
		query.with(new Sort(orderDirerction, orderBy));
		return query;
	}
}
