package com.mall.common.utils;


import cn.hutool.extra.template.engine.freemarker.FreemarkerTemplate;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/*邮件工具*/
@Component
@Slf4j
public class MailServiceImpl {


    //邮件的发送者
    @Value("${spring.mail.username}")
    private String from;


    @Resource
    private JavaMailSender javaMailSender;


    @Autowired
    private FreeMarkerConfigurer configurer;

    /*发送文本消息*/
    public void sendTextMessageMail(String sendTo, String title, String msg) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(msg);


        javaMailSender.send(simpleMailMessage);
    }

    /**
     发送携带附件的邮件
     sendTo      接收人
     title       标题
     msg         消息
     inputStream 附件
     fileName    附件名称
     */
    public void sendFileMessageMail(String sendTo, String title, String msg, InputStream inputStream, String fileName) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(msg);

        mimeMessageHelper.addAttachment(fileName, new InputStreamResource(inputStream));
        javaMailSender.send(mimeMessage);
    }

    /**
     发送html邮件
     sendTo       接收人
     title        标题
     msg          消息体
     templateName 模板名称
     */
    public void sendHtmlMessageMail(String sendTo, String title, Map<String, Object> msg, String templateName) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(title);


        Template template = configurer.getConfiguration().getTemplate(templateName);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, msg);
        mimeMessageHelper.setText(html,true);
        javaMailSender.send(mimeMessage);
    }

}
