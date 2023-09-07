package guru.sfg.brewery.web.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    private static String PASSWORD =  "password";

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
