package pers.li.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder jsonEncoder = new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(1);
        testBean.setName("hhh");
        byte[] encode = jsonEncoder.encode(testBean);
        System.err.println(encode.length);
    }
}
