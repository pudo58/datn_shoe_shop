package org.datn.app.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ChangePasswordRequest;
import org.datn.app.core.entity.User;
import org.datn.app.core.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.ValueRef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public User doInsert(User user) {
        user.setCreated(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User doUpdateById(User user, Long aLong) {
        User user1 = userRepo.findById(aLong).get();
        user1.setAddress(user.getAddress());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setFullName(user.getFullName());
        user1.setModified(new Date());
        return userRepo.save(user1);
    }

    @Override
    public User doDeleteById(Long aLong) {
        userRepo.deleteById(aLong);
        return null;
    }

    @Override
    public User findById(Long aLong) {
        return userRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userRepo.findAll(pageable);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        // get Claim from token
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("vHPxbmyhXq".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                if(decodedJWT.getExpiresAt().getTime() + 500_000 > System.currentTimeMillis()){
                    String username = decodedJWT.getSubject();
                    User user =userRepo.findByUsername(username);
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    String accessToken = JWT.create()
                            .withSubject(user.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles", user.getRole())
                            .sign(algorithm);
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", accessToken);
                    tokens.put("refresh_token", refreshToken);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(),tokens);
                }
            }catch (Exception e) {
                response.setHeader("error", e.getMessage());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Map<String, String> changePassword(ChangePasswordRequest request) {
        Map<String, String> result = new HashMap<>();
        User user = userRepo.findByUsername(request.getUsername());
        if (user != null) {
            if (new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
                user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
                user.setModified(new Date());
                userRepo.save(user);
                result.put("message", "?????i m???t kh???u th??nh c??ng");
                result.put("status", String.valueOf(HttpStatus.OK.value()));
            } else {
                result.put("message", "M???t kh???u c?? kh??ng kh???p");
                result.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            }
        } else {
            result.put("message", "T??n ????ng nh???p kh??ng t???n t???i");
            result.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        return result;
    }

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file, Principal principal) {
        try {
           User user = userRepo.findByUsername(principal.getName());
           if(user == null)
               return ResponseEntity.ok("B???n ch??a ????ng nh???p");
          else{
               Path filePath = Paths.get("C:\\Users\\Admin\\Desktop\\code\\datn_shoe_shop\\src\\server\\html\\image\\user", file.getOriginalFilename());
                   user.setAvatar(file.getOriginalFilename());
                   if(userRepo.save(user) != null)
                       Files.write(filePath, file.getBytes());
                   return ResponseEntity.ok(user.getAvatar());
           }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("C???p nh???t ???nh ?????i di???n th???t b???i");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
