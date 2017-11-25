package database.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Crypto {
    public String hashPassword(String password, String salt)
    {
        String digested = digestText(password, "SHA-256");
        if(digested != null)
        {
            digested = digestText(salt.concat(digested), "SHA-256");
        }
        return digested;
    }
    
    public String digestText(String text, String algorithm)
    {
        try{
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] mdbytes = md.digest(text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String saltGenerator()
    {
        Random rng = new Random();
        rng.setSeed(System.currentTimeMillis());
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++)
        {
            sb.append((char)(rng.nextInt(27)+97));
        }
        
        return sb.toString();
    }
}
