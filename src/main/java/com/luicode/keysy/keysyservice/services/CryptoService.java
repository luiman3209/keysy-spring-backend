package com.luicode.keysy.keysyservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service 
public class CryptoService {
    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_SIZE = 12;

    private SecretKey secretKey;

    @Value("${encryption.key}") 
    private String encryptionKey;

    @PostConstruct
    public void init() {
        if (encryptionKey == null || encryptionKey.isEmpty()) {
            throw new IllegalStateException("Encryption key is missing! Set 'encryption.key' in application.properties");
        }
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(encryptionKey), AES);
        System.out.println("CryptoService initialized successfully.");
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM);
        byte[] iv = new byte[IV_SIZE];
        SecureRandom.getInstanceStrong().nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(AES_GCM);
        byte[] iv = Arrays.copyOfRange(decoded, 0, IV_SIZE);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        byte[] decryptedData = cipher.doFinal(Arrays.copyOfRange(decoded, IV_SIZE, decoded.length));
        return new String(decryptedData);
    }

    public static String generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(256);
        SecretKey key = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
