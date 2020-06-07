package ar.edu.utn.frba.dds.Usuario;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    // Hasheo para la persistencia seguro de las passwords
    public byte[] sha256(String password) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }
    public static String hash(byte[] bytes)
    {
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder hexaString = new StringBuilder(number.toString(16));
        while (hexaString.length() < 32)
        {
            hexaString.insert(0, '0');
        }
        return hexaString.toString();
    }
    public String hashear(String cadena) throws NoSuchAlgorithmException {
        return hash(sha256(cadena));
    }
}
