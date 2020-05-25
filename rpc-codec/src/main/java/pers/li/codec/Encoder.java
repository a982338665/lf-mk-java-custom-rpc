package pers.li.codec;

/**
 * 序列化 ：对象转化为二进制数组
 */
public interface Encoder {

    byte[] encode (Object object);

}
