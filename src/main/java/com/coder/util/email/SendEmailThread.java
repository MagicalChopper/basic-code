package com.coder.util.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//发送邮件的线程类
public class SendEmailThread implements Runnable {

    private static final Log log = LogFactory.getLog(SendEmailThread.class);

    private String to;//收件人
    private String subject;//邮件主题
    private String content;//邮件内容

    public SendEmailThread() {
        super();
    }
    public SendEmailThread(String to, String subject, String content) {
        super();
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public void run() {
        try{
            sendEmail(to, subject, content);
        }catch(Exception e){
            log.error("发送邮件异常"+e.getMessage());
        }
    }

    /**
     * 发送邮件的方法
     * @param to
     * @param subject
     * @param content
     * @throws IOException
     * @throws MessagingException
     */
    public static void sendEmail(String to,String subject,String content) throws IOException, MessagingException{
        Properties properties = new Properties();
        InputStream resourceStream = null;

        try{
            //读取email.properties文件
            resourceStream = SendEmailThread.class.
                    getClassLoader().getResourceAsStream("email.properties");
            //加载流文件到properties
            properties.load(resourceStream);

            properties.put("mail.smtp.host", properties.get("mail.smtp.host"));
            properties.put("mail.smtp.port", properties.get("port"));
            properties.put("mail.smtp.auth", true);

            //构造身份验证信息;
            Authenticator authenticator =
                    new EmailAuthenticator(properties.get("username").toString()
                            , properties.get("password").toString());

            //通过保存有身份验证信息的对象,构建邮件发送的会话连接
            Session	sendMailSession = Session.getInstance(properties,authenticator);

            //通过发送邮件的会话构建消息类型
            MimeMessage mailMessage = new MimeMessage(sendMailSession);

            //设置发件人的信息
            mailMessage.setFrom(
                    new InternetAddress(properties.getProperty("username"),"员工系统邮箱")//员工系统是发件人的username
            );

            //设置收信人
            mailMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(to)
            );

            //设置主题
            mailMessage.setSubject(subject,"UTF-8");

            //设置邮件发送日期
            mailMessage.setSentDate(new Date());

            //是否添加附件,Multipart本身是一个文件容器
            Multipart multiPart = new MimeMultipart();

            //创建一个邮件对象体
            BodyPart html = new MimeBodyPart();
            html.setContent(content.trim(),"text/html;charset=utf-8");

            //将要发送的邮件体添加到容器中
            multiPart.addBodyPart(html);

            //将容器挂载到消息
            mailMessage.setContent(multiPart);

            //执行smtp协议发送消息
            Transport.send(mailMessage);

        }finally{
            //关闭流
            if (resourceStream != null) {
                resourceStream.close();
            }
        }
    }
}

//Authenticator是用来进行通过网络进行身份验证的类
class EmailAuthenticator extends Authenticator{
    String username;
    String password;

    public EmailAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username,password);
    }
}