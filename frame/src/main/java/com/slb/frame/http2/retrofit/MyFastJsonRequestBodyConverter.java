package com.slb.frame.http2.retrofit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Gifford on 2017/8/24.
 */

public class MyFastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
	private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
	private SerializeConfig serializeConfig;
	private SerializerFeature[] serializerFeatures;

	MyFastJsonRequestBodyConverter(SerializeConfig config, SerializerFeature... features) {
		serializeConfig = config;
		serializerFeatures = features;
	}

	@Override
	public RequestBody convert(T value) throws IOException {
		byte[] content;
		if (serializeConfig != null) {
			if (serializerFeatures != null) {
				content = JSON.toJSONBytes(value, serializeConfig, serializerFeatures);
			} else {
				content = JSON.toJSONBytes(value, serializeConfig);
			}
		} else {
			if (serializerFeatures != null) {
				content = JSON.toJSONBytes(value, serializerFeatures);
			} else {
				content = JSON.toJSONBytes(value);
			}
		}
		return RequestBody.create(MEDIA_TYPE, content);
	}
}
