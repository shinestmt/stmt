package com.hanghang.codestore.mail;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-7-6 下午03:15:08
 */
public class GmailSender {
        public static boolean sendMail(final String gmail, final String password,
                        String nickname, String email, String subject, String body) {
                
        	//中文
        	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
                Properties props = System.getProperties();
                props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.port", "465");
                props.setProperty("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                        @Override
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(gmail, password);
                                        }
                                });

                Message msg = new MimeMessage(session);

                try {
                        msg.setFrom(new InternetAddress(gmail, nickname));
                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                                        email, false));
                        msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "b"));
                        msg.setText(body);
                        msg.setSentDate(new Date());
                        Transport.send(msg);
                        return true;
                } catch (AddressException e) {
                        return false;
                } catch (MessagingException e) {
                        return false;
                } catch (UnsupportedEncodingException e) {
                        return false;
                }
        }

        public static void main(String[] args) {
                GmailSender.sendMail("service@appmail.org.ru", "1qaz2wsx,*",
                                "Weather Service", "itcontent@gmail.com", "中文测试", "中文测试正文");
        }
}