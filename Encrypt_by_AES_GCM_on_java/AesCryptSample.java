package aescryptsample;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;


public class AesCryptSample {

	public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 12; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes

    public static void main(String args[]) throws Exception {

    	// Setting
        byte[] message = "GCMDecriptTest".getBytes(StandardCharsets.UTF_8);

        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        final byte[] nonce = new byte[GCM_NONCE_LENGTH];
        secureRandom.nextBytes(nonce);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
        byte[] aad = "AAD_String".getBytes(StandardCharsets.UTF_8);

        // Encrypt
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        cipher.updateAAD(aad);
        byte[] cipherText = cipher.doFinal(message);

        // Print Encript Result
        System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        System.out.println("nonce: " + Base64.getEncoder().encodeToString(nonce));
        System.out.println("aad: " + Base64.getEncoder().encodeToString(aad));
        System.out.println("Encrypted text: " + Base64.getEncoder().encodeToString(cipherText));

        // Test weather we can decript or not in java.
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        cipher.updateAAD(aad);
        byte[] plainTextBytes = cipher.doFinal(cipherText);
        String plainText = new String(plainTextBytes);

        System.out.println("Decrypted text: " + new String(plainText));
    }

}

