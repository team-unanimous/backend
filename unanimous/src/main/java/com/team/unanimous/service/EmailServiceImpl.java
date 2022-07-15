//package com.team.unanimous.service;
//
//import java.io.File;
//
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailServiceImpl implements MailService {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private JavaMailSenderImpl javaMailSender;
//
//    @Override
//    public void sendMail(MailDto mailDto) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // use multipart (true)
//
//            InternetAddress[] toAddress = MailUtil.listToArray(mailDto.getToAddressList(), "UTF-8");
//            InternetAddress[] ccAddress = MailUtil.listToArray(mailDto.getCcAddressList(), "UTF-8");
//            InternetAddress[] bccAddress = MailUtil.listToArray(mailDto.getBccAddressList(), "UTF-8");
//
//            mimeMessageHelper.setSubject(MimeUtility.encodeText(mailDto.getSubject(), "UTF-8", "B")); // Base64 encoding
//            mimeMessageHelper.setText(mailDto.getContent(), mailDto.isUseHtmlYn());
//            mimeMessageHelper.setFrom(new InternetAddress(mailDto.getFromAddress(), mailDto.getFromAddress(), "UTF-8"));
//            mimeMessageHelper.setTo(toAddress);
//            mimeMessageHelper.setCc(ccAddress);
//            mimeMessageHelper.setBcc(bccAddress);
//
//            if(!CollectionUtils.isEmpty(mailDto.getAttachFileList())) {
//                for(AttachFileDto attachFileDto: mailDto.getAttachFileList()) {
//                    FileSystemResource fileSystemResource = new FileSystemResource(new File(attachFileDto.getRealFileNm()));
//                    mimeMessageHelper.addAttachment(MimeUtility.encodeText(attachFileDto.getAttachFileNm(), "UTF-8", "B"), fileSystemResource);
//                }
//            }
//
//            javaMailSender.send(mimeMessage);
//
//            logger.info("MailServiceImpl.sendMail() :: SUCCESS");
//        } catch (Exception e) {
//            logger.info("MailServiceImpl.sendMail() :: FAILED");
//            e.printStackTrace();
//        }
//
//    }
//
//}

