package pers.li.codec;

/**
 * 反序列化 ：二进制数组转对象
 */
public interface Decoder {

    <T> T decode(byte[] bytes,Class<T> tClass);

}
