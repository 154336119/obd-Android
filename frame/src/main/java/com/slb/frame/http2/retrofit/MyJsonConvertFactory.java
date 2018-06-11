package com.slb.frame.http2.retrofit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Gifford on 2017/8/24.
 */

public class MyJsonConvertFactory extends Converter.Factory {

	private ParserConfig mParserConfig = ParserConfig.getGlobalInstance();
	private int featureValues = JSON.DEFAULT_PARSER_FEATURE;
	private Feature[] features;

	private SerializeConfig serializeConfig;
	private SerializerFeature[] serializerFeatures;

	public static MyJsonConvertFactory create() {
		return new MyJsonConvertFactory();
	}

	private MyJsonConvertFactory() {
	}


	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
		return new MyFastJsonResponseBodyConverter<>(type, mParserConfig, featureValues, features);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		return new MyFastJsonRequestBodyConverter<>(serializeConfig, serializerFeatures);
	}

	public ParserConfig getParserConfig() {
		return mParserConfig;
	}

	public MyJsonConvertFactory setParserConfig(ParserConfig config) {
		this.mParserConfig = config;
		return this;
	}

	public int getParserFeatureValues() {
		return featureValues;
	}

	public MyJsonConvertFactory setParserFeatureValues(int featureValues) {
		this.featureValues = featureValues;
		return this;
	}

	public Feature[] getParserFeatures() {
		return features;
	}

	public MyJsonConvertFactory setParserFeatures(Feature[] features) {
		this.features = features;
		return this;
	}

	public SerializeConfig getSerializeConfig() {
		return serializeConfig;
	}

	public MyJsonConvertFactory setSerializeConfig(SerializeConfig serializeConfig) {
		this.serializeConfig = serializeConfig;
		return this;
	}

	public SerializerFeature[] getSerializerFeatures() {
		return serializerFeatures;
	}

	public MyJsonConvertFactory setSerializerFeatures(SerializerFeature[] features) {
		this.serializerFeatures = features;
		return this;
	}

}
