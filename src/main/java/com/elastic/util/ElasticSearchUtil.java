package com.elastic.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.List;
import java.util.function.Supplier;
public class ElasticSearchUtil {

    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery(){
        val  matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithNameField(String fieldValue){
        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithNameField(fieldValue)));
        return supplier;
    }
    public static Supplier<Query> supplierWithFieldAndValue(String fieldValue,String fieldNAme){
        Supplier<Query> supplier = ()->Query.of(q->q.match(searchQueryWithFieldAndNAme(fieldValue,fieldNAme)));
        return supplier;
    }

    private static MatchQuery matchQueryWithNameField(String fieldValue){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field("name").query(fieldValue).build();

    }

    private static MatchQuery searchQueryWithFieldAndNAme(String fieldValue,String fieldName){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field(fieldName).query(fieldValue).build();
    }

    public static Supplier<Query> createSupplierQuery(String approximateProductName){
        Supplier<Query> supplier = ()->Query.of(q->q.fuzzy(createFuzzyQuery(approximateProductName)));
        return  supplier;
    }
    public static FuzzyQuery createFuzzyQuery(String approximateProductName){
        val fuzzyQuery  = new FuzzyQuery.Builder();
        return  fuzzyQuery.field("name").value(approximateProductName).build();

    }
    public static Supplier<Query>  supplierQueryForMultiMatchQuery(String key , List<String> fields){
        Supplier<Query> supplier = ()->Query.of(q->q.multiMatch(multiMatchQuery(key, fields)));
        return supplier;
    }
    private static MultiMatchQuery multiMatchQuery(String key , List<String> fields ){
        return new MultiMatchQuery.Builder().query(key).fields(fields).build();
    }


}