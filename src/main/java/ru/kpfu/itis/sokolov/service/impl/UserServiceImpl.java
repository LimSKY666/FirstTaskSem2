package ru.kpfu.itis.sokolov.service.impl;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.sokolov.config.MailConfig;
import ru.kpfu.itis.sokolov.dto.CreateUserDto;
import ru.kpfu.itis.sokolov.dto.UserDto;
import ru.kpfu.itis.sokolov.model.User;
import ru.kpfu.itis.sokolov.repository.UserRepository;
import ru.kpfu.itis.sokolov.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, JavaMailSender javaMailSender, MailConfig mailConfig) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.javaMailSender = javaMailSender;
        this.mailConfig = mailConfig;
    }

    @Override
    public UserDto getByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
        }
        return userRepository.getUserByEmail(email).stream().map(UserDto::fromModel).findFirst().orElse(null);
    }

    @Override
    public UserDto getById(Integer id) {
        return userRepository.findById(id).stream().map(UserDto::fromModel).findFirst().orElse(null);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public UserDto signUp(CreateUserDto createUserDto, String url) {
        String code = RandomString.make(64);
        String encodedPassword = encoder.encode(createUserDto.getPassword());
        User user = new User(createUserDto.getName(), createUserDto.getEmail(), code, encodedPassword);
        User savedUser = userRepository.save(user);
        sendVerificationMail(createUserDto.getEmail(), createUserDto.getName(), code, url);
        return UserDto.fromModel(user);
    }

    @Override
    public List<UserDto> getAllStepan() {
        return userRepository.findAllStepanUser().stream().map(UserDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllByName(String name) {
        return userRepository.findAllByName(name).stream().map(UserDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user != null) {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void sendVerificationMail(String mail, String name, String code, String url) {
        String from = mailConfig.getFrom();
        String sender = mailConfig.getSender();
        String subject = mailConfig.getSubject();
        String content = mailConfig.getContent();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(from, sender);

            helper.setTo(mail);
            helper.setSubject(subject);

            content = content.replace("{name}", name);
            content = content.replace("{url}", url + "/verification?code=" + code);

            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }
}