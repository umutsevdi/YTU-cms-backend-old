package com.cms.services;

import java.time.LocalDateTime;

import com.cms.CmsApplication;
import com.cms.email.ConfirmMailMessage;
import com.cms.email.MailService;
import com.cms.models.Singleton;
import com.cms.models.documents.User;
import com.cms.models.registration.MailConfirmationToken;
import com.cms.models.registration.RegistrationRequest;
import com.cms.repositories.MailConfirmationTokenRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailConfirmationTokenService {
    private final MailConfirmationTokenRepository mailConfirmationTokenRepository;
    private final UserService userService;
    private final MailService mailService;

    public void sendToken(RegistrationRequest request) throws Exception {
        if (userService.findByMail(request.getMail()) == null)
            throw new Exception("UserNotFoundException");
        String token = Singleton.generateRandomNumber(14)+System.currentTimeMillis()%1000000;
        try{
            mailService.sendMail("no-reply@kys.yildiz.edu.tr",request.getMail(),new ConfirmMailMessage(request.getName(), CmsApplication.link + "/auth/confirm?token=" + token));
            mailConfirmationTokenRepository
                    .insert(new MailConfirmationToken(request.getMail(), LocalDateTime.now().plusDays(3L), token));
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
     
    }

    public void confirmMail(String token) throws Exception {
        MailConfirmationToken mailConfirmation = mailConfirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new Exception("InvalidTokenException"));
        if (mailConfirmation.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new Exception("ExpiredTokenException");
        } else {
            User user = userService.findByMail(mailConfirmation.getMail());
            user.setMailConfirmed(true);
            userService.editElement(user);
            mailConfirmationTokenRepository.delete(mailConfirmation);
        }
    }

    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
                + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
                + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
                + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                + "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
                + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                + "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
                + "                  \n" + "                    </td>\n"
                + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                + "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
                + "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
                + "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
                + "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
                + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                + "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
                + "      <td>\n" + "        \n"
                + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                + "                  <tbody><tr>\n"
                + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
                + "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
                + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
                + "  </tbody></table>\n" + "\n" + "\n" + "\n"
                + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                + "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
                + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
                + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                + "        \n"
                + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Say??n "
                + name
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Y??ld??z Teknik ??niversitesi Kul??p Y??netim Sistemi'ne ho?? geldiniz. Kay??t i??lemini tamamlamak i??in a??a????daki linke t??klay??n: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
                + link
                + "\">Kay??t Ol</a> </p></blockquote>\n Link 3 g??n i??erisinde ge??erlili??ini yitirecektir. <p>Y??ld??z Teknik ??niversitesi Sa??l??k K??lt??r Spor Daire Ba??kanl??????</p>"
                + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
                + "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
                + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
    }
}
