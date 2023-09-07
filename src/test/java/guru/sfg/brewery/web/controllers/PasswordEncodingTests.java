package guru.sfg.brewery.web.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    private static String PASSWORD =  "password";

    private static String TIGER =  "tiger";


    @Test
    public void testBcrypt(){
        //por default es 10, entre mayor sea el numero , mas va a tardar en encriptar
        PasswordEncoder bcript = new BCryptPasswordEncoder(10);
        System.out.println(bcript.encode(PASSWORD));
        System.out.println(bcript.encode(PASSWORD));
        System.out.println(bcript.encode(TIGER));
        Assert.assertTrue(bcript.matches(PASSWORD,bcript.encode(PASSWORD)));
        Assert.assertTrue(bcript.matches(TIGER,bcript.encode(TIGER)));
    }

    @Test
    public void testCha256(){
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(TIGER));
        Assert.assertTrue(sha256.matches(PASSWORD,sha256.encode(PASSWORD)));
        Assert.assertTrue(sha256.matches(TIGER,sha256.encode(TIGER)));
    }

    @Test
    public void testLdap(){
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode(PASSWORD));
        String encodedPwd = ldap.encode(PASSWORD);
        Assert.assertTrue(ldap.matches(PASSWORD,encodedPwd));
    }

    @Test
    public void testNoOp(){
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp);
    }

    @Test
    public void hashingExample(){
       System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
       System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

       String salted = PASSWORD.concat("ThisIsMySaltValue");

       System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
