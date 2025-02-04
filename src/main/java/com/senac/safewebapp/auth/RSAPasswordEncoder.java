package com.senac.safewebapp.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAPasswordEncoder implements PasswordEncoder {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public RSAPasswordEncoder(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            byte[] passwordBytes = rawPassword.toString().getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't encrypt password with RSA", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encodedPassword);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedPassword = new String(decryptedBytes, StandardCharsets.UTF_8);
            return rawPassword.toString().equals(decryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't decrypt password with RSA", e);
        }
    }
}
