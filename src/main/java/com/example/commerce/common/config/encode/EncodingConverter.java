package com.example.commerce.common.config.encode;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

@Converter
public class EncodingConverter implements AttributeConverter<String, String> {

    private static final String UTF_8 = "UTF-8";
//    @Value("${com.secretKey}")
    private static String SECRET_KEY;

    @Autowired
    private Environment env;

    private String SECRET;

    @PostConstruct
    void init() {
        try {
            SECRET_KEY = env.getProperty("com.secretKey");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // SHA512
    private static String getSHA512() {
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String plainText) {
        if (plainText == null)
            return null;

        String strKey = getSHA512();
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(strKey, UTF_8));
        return new String(Hex.encodeHex(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)))).toLowerCase(Locale.ROOT);
    }

    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String encodedText) {
        if (encodedText == null)
            return null;

        String strKey = getSHA512();
        final Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(strKey, UTF_8));
        return new String(decryptCipher.doFinal(Hex.decodeHex(encodedText.toCharArray())));
    }

    private static SecretKeySpec generateMySQLAESKey(String key, String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding))
                finalKey[i++%16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
