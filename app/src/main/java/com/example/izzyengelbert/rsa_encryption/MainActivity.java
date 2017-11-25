package com.example.izzyengelbert.rsa_encryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "AsymmetricAlgorithmRSA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Original text
        String theTestText = "This is just a simple test!";
        TextView tvorig = (TextView) findViewById(R.id.tvorig);
        tvorig.setText("\n[ORIGINAL]:\n" + theTestText + "\n");

        // Generate key pair for 1024-bit RSA encryption and decryption
        Key publicKey = null;
        Key privateKey = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e(TAG, "RSA key pair error");
        }

        // Encode the original data with RSA private key
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA/ECB/OAEPPadding");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "RSA encryption error");
        }
        TextView tvencoded = (TextView) findViewById(R.id.tvencoded);
        tvencoded.setText("[ENCODED]:\n" + Base64.encodeToString(encodedBytes, Base64.DEFAULT) +"\n");

        // Decode the encoded data with RSA public key
        TextView tvdecoded = (TextView) findViewById(R.id.tvdecoded);
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA/ECB/OAEPPadding");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "RSA decryption error");
        }
        String temp = new String(decodedBytes);
        tvdecoded.setText("[ENCODED]:\n" + temp +"\n");

        /*String tes = "testest";
        byte[] bytes = tes.getBytes();
        String tes1 = "";
        for(int i=0;i<bytes.length;++i){
            //String temp = new String(bytes[i]);
            tes1 += bytes[i]+" ";
        }
        tvdecoded.setText(tes1);*/
    }
}
