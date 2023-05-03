package cn.edu.xmu.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static Integer saltLength = 5;
    private static String md5(String src) {
        return DigestUtils.md2Hex(src);
    }


    /**
     * 传入 源数据+盐 返回md5加密结果
     * @param src
     * @param salt
     * @return
     */
    public static String backToDb(String src, String salt) {
        return md5(String.format("%c%c%c%s%c",
                salt.charAt(2), salt.charAt(5), salt.charAt(1),
                src,
                salt.charAt(7)));
    }

//    /**
//     * 随机生成2*saltLength长度的盐
//     * @return
//     */
//    public static String randomSalt() {
//        SecureRandom sr = new SecureRandom();
//        byte[] salt = new byte[saltLength];
//        sr.nextBytes(salt);
//        return Hex.encodeHexString(salt);
//    }


//    private static String frontToBack(String src) {
//        String salt = "SFAasf1aw5"; // 同前端
//        return md5(String.format("%c%c%s%c%c",
//                salt.charAt(1), salt.charAt(3),
//                src,
//                salt.charAt(0), salt.charAt(7)));
//    }
}
