package com.arjun.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arjun.model.VIPRequest;
import com.arjun.model.Volunteer;
import com.arjun.service.LlamaService;

@RestController
@RequestMapping("/api")
public class LlamaController {

	@Autowired
	private LlamaService llamaService;

	@PostMapping("/getLlamaResponse")
	public String getLlamaResponse(@RequestBody String message) throws Exception {
		return llamaService.getLlamaResponse(message);
	}

}
