package pers.li.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder jsonEncoder = new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(1);
        testBean.setName("hhh");
        byte[] encode = jsonEncoder.encode(testBean);
        System.err.println(encode.length);

        JSONDecoder jsonDecoder = new JSONDecoder();
        TestBean decode = jsonDecoder.decode(encode, TestBean.class);
        System.err.println(decode.toString().equals(testBean.toString()));
    }
}
