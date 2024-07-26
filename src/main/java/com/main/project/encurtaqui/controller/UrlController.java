package com.main.project.encurtaqui.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.project.encurtaqui.domain.entity.Url;
import com.main.project.encurtaqui.service.UrlService;
import com.main.project.encurtaqui.util.UrlScheduled;

@RestController
@RequestMapping("/v1/encurtaqui")
public class UrlController {

	@Autowired
	private UrlService urlService;

	
	@GetMapping("/{shortUrl}")
	public ResponseEntity redirectToOriginalUrl(@PathVariable String shortUrl) throws URISyntaxException {
		Url urlByshort = this.urlService.getOriginalUrlByShortUrl(shortUrl);
		String redirectTo = urlByshort.getOriginalUrl();
		this.urlService.countClicks(shortUrl);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(new URI(redirectTo));
		return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping
	public List<Url> getUrls() {
		return urlService.getAllShortUrl();
	}

	@PostMapping
	public ResponseEntity<Url> returnShortUrlFromOriginalUrl(@RequestParam(value = "originalUrl") String originalUrl) {
		Url url = this.urlService.findOrSaveUrl(originalUrl);
		return ResponseEntity.status(HttpStatus.CREATED).body(url);
	}

}
