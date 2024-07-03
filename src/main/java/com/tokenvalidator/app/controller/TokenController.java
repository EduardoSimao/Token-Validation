package com.tokenvalidator.app.controller;

import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.tomcat.util.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokenvalidator.app.domain.user.AuthenticationDTO;
import com.tokenvalidator.app.domain.user.LoginResponseDTO;
import com.tokenvalidator.app.domain.user.RegisterDTO;
import com.tokenvalidator.app.domain.user.ValidateTokenDTO;
import com.tokenvalidator.app.domain.user.user;
import com.tokenvalidator.app.infra.security.TokenService;
import com.tokenvalidator.app.model.Token;
import com.tokenvalidator.app.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class TokenController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Value("${api.security.token.secret}")
    private String secret;

	@PostMapping(value="/validate")
	public String validate( @RequestBody @Valid Token token){
		try{

			String[] split_string = token.getValue().split("\\.");

			String base64EncodedBody = split_string[1];

			Base64 base64Url = new Base64(true);

			String body = new String(base64Url.decode(base64EncodedBody));
            
			return ValidateTokenDTO.validateToken(body);

		}catch(IllegalArgumentException error){
			//Caso não seja JWT não seja valido
            System.out.println("Token in invalid");

			return "Falso";
		}
       
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		// var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		// var auth = this.authenticationManager.authenticate(usernamePassword);

		// var  token = tokenService.generateToken((user) auth.getPrincipal());
		//return ResponseEntity.ok(new LoginResponseDTO(token));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

		if(this.userRepository.findBySeed(data.seed()) != null) return ResponseEntity.badRequest().build();

		//String encryptedPassword = new BCryptPasswordEncoder().encode(data.seed());

		var  token = tokenService.generateToken(data);
		
		user newUser = new user(data.name(), data.seed(), data.role(), token);

		this.userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}
	
	
}




