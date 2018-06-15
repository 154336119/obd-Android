package com.slb.frame.http2.retrofit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Converter;

/**
 * Created by Gifford on 2017/8/24.
 */

public class MyFastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

	private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

	private Type mType;

	private ParserConfig config;
	private int featureValues;
	private Feature[] features;

	MyFastJsonResponseBodyConverter(Type type, ParserConfig config, int featureValues,
                                    Feature... features) {
		mType = type;
		this.config = config;
		this.featureValues = featureValues;
		this.features = features;
	}

	@Override
	public T convert(ResponseBody value) throws IOException {
		try {
			String resultStr=decrypt(value);
			return JSON.parseObject(resultStr, mType, config, featureValues,
					features != null ? features : EMPTY_SERIALIZER_FEATURES);
		}catch (Exception ex){
			return null;
		}
		finally {
			value.close();
		}
	}

	/**
	 * 解密过程
	 *
	 * @param responseBody
	 * @return
	 * @throws IOException
	 */
	private String decrypt(ResponseBody responseBody)
	{
		try {
			Charset charset = Charset.forName("UTF-8");
			MediaType contentType = responseBody.contentType();
			if (contentType != null) {
				charset = contentType.charset();
			}
			BufferedSource bufferedSource = responseBody.source();
			bufferedSource.request(Long.MAX_VALUE);
			okio.Buffer buffer = bufferedSource.buffer();
			String oldBody = buffer.clone().readString(charset);
			String newBody = null;
			Logger.d(newBody);
			return newBody;
		}
		catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
