//package com.team.unanimous.Email;
//
//import com.team.unanimous.dto.requestDto.EmailRequestDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//@Service
//public class EmailService {
//    private String nickName = "Unanimous";
//    private String sendMail = "";
//    private String username = "";
//    private String password = "";
//
//    public ResponseEntity mailSend(EmailRequestDto emailRequestDto){
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.naver.com");
//        props.put("mail.smtp.port", 465);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.ssl.trust", "smtp.naver.com");
//        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//        session.setDebug(true);
//
//        try {
//            Message mimeMessage = new MimeMessage(session);
//            System.out.println("메일 전송 시작");
//            System.out.println(mimeMessage);
//            mimeMessage.setFrom(new InternetAddress(sendMail, nickName));
//            System.out.println("보내는 메일" + "닉네임");
//            System.out.println(sendMail+nickName);
//            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequestDto.getUsername()));
//            System.out.println("받는 메일");
//            mimeMessage.setSubject("Unanimous 인증 번호입니다.");
//            System.out.println("제목");
//            mimeMessage.setText("내용");
//            System.out.println("내용");
//            Transport.send(mimeMessage);
//            return ResponseEntity.ok("이메일 전송 성공");
//        } catch (Exception e){
//            return ResponseEntity.ok("이메일 전송 실패");
//        }
//    }
//}
