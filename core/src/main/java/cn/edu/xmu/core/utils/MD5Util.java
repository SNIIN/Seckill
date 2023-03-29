package cn.edu.xmu.core.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

public class MD5Util {
    private static Integer saltLength = 5;
    private static String md5(String src) {
        return DigestUtils.md2Hex(src);
    }

    public static String backToDb(String src, String salt) {
        return md5(String.format("%c%c%c%s%c",
                salt.charAt(2), salt.charAt(5), salt.charAt(1),
                src,
                salt.charAt(7)));
    }

    public static String randomSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[saltLength];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

//    public static void main(String args[]) {
//        String a = frontToBack("77");
//        System.out.println(a);
//        String salt = randomSalt();
//        System.out.println(salt);
//        String b = backToDb("8bfd65987109c13dce00ed56c9061320", salt);
//        String c = frontToDb("77", salt);
//        System.out.println(b);
//        System.out.println(c);
//    }

    private static String frontToBack(String src) {
        String salt = "SFAasf1aw5"; // 同前端
        return md5(String.format("%c%c%s%c%c",
                salt.charAt(1), salt.charAt(3),
                src,
                salt.charAt(0), salt.charAt(7)));
    }

    private static String frontToDb(String src, String salt) {
        return backToDb(frontToBack(src), salt);
    }
}
