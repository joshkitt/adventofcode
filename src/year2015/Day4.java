package year2015;

import sun.security.provider.MD5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by josh on 12/18/15.
 */
public class Day4 {

    private static String input = "yzbqklnj";

    public static void main(String[] args) throws NoSuchAlgorithmException {


        int pad = 53720356;

        while (true) {
            String plaintext = input + pad++;

//            System.out.println("plaintext = " + plaintext);

            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);

            if (hashtext.startsWith("00000")) {
                System.out.println("hashtext = " + hashtext);
                System.exit(0);
            }
        }
    }
}
