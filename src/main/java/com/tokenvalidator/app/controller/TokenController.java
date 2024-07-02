package com.tokenvalidator.app.controller;

import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokenvalidator.app.domain.user.AuthenticationDTO;
import com.tokenvalidator.app.domain.user.LoginResponseDTO;
import com.tokenvalidator.app.domain.user.RegisterDTO;
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

	@PostMapping(value="/validate")
	public String validate( @RequestBody Token token) {

		// altere esse metodo para atender as regras de definidas no readme.
		// você pode modificar o tipo de retorno, importar outros pacotes, criar mais classes.
		// existe uma pasta chamada Model para gerenciar o objeto Token

		// Imprimindo o input recebido
		//System.out.println(token.getValue());

		return "false";
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var  token = tokenService.generateToken((user) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

		if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		
		user newUser = new user(data.name(), data.email(), encryptedPassword, data.role());

		this.userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}
	
	
}




