package main.java.com.example.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {

    @GetMapping("/accounts")
	public String create() {
		return "OK";
	}

}
