package com.hertz.digital.platform.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Utilities related to encryption and decryption
 */
public class EncryptDecryptUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptDecryptUtils.class);

    /**
     * Default Constructor
     */
    private EncryptDecryptUtils() {

    }

    /**
     * Base64 Group ENCODE
     * @param string
     * @return
     */
    public static byte[] encodeBase64(String string) {
        return Base64.encodeBase64(string.getBytes());
    }

    /**
     * Base64 Group DECODE
     * @param string
     * @return
     */
    public static String decodeBase64(String string) throws UnsupportedEncodingException {
        byte[] bytes = Base64.decodeBase64(string.getBytes());

        return new String(bytes, "UTF-8");
    }

    /**
     * Encryption method for API
     *
     * @param key
     * @param initVector
     * @param value
     * @return
     */
    public static void doEncrypt(Map<String, Object> configMap, String key, String initVector, File file) {
        try {

            IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(stringtoByteArray(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            SealedObject sealedObject = new SealedObject((Serializable) configMap, cipher);
            CipherOutputStream cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(file)), cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();
            
        } catch (Exception e) {
            LOGGER.error("EncryptDecryptUtils.doEncrypt(): Exception while encrypting value {}", e);
        }
    }

    /**
     * Decryption method for API
     *
     * @param key
     * @param initVector
     * @param encrypted
     * @return
     */
    public static Object doDecrypt(String key, String initVector, InputStream istream) {
        try {
            IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(stringtoByteArray(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
            ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
            SealedObject sealedObject;
            try {
                sealedObject = (SealedObject) inputStream.readObject();
                inputStream.close();
                return sealedObject.getObject(cipher);
            } catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
            	LOGGER.error("EncryptDecryptUtils.doDecrypt(): Exception while getting object {}", e);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("EncryptDecryptUtils.doDecrypt(): Exception while decrypting value {}", e);
        }

        return null;
    }

    /**
     * HEX to Byte
     *
     * @param s
     * @return
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    
    private static byte[] stringtoByteArray(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        byte[] bytesOfMessage = key.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] b = md.digest(bytesOfMessage);
        return b;
    }
}
