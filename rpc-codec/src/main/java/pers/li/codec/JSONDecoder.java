package pers.li.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的反序列化实现
 */
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> tClass) {
        return JSON.parseObject(bytes, tClass);
    }
}
