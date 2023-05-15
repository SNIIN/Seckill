package cn.edu.xmu.core.utils;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class MD5UtilTest {

    @Test
    public void testBackToDb() {
        String result = MD5Util.backToDb("8bfd65987109c13dce00ed56c9061320", "SFAasf1aw5");
        assertEquals("6808bdafa082b4596840ada7d7af81b6", result);
    }
}
