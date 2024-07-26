package com.main.project.encurtaqui.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.main.project.encurtaqui.domain.entity.Url;
import com.main.project.encurtaqui.exceptions.BusinessException;
import com.main.project.encurtaqui.repository.UrlRepository;

@Service
public class UrlService {

	
	@Autowired
	UrlRepository repository;

	private static final List<Character> ALLOWED_CHARS = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '+');

	
	public int countClicks(String shortUrl) {
		Url url = getOriginalUrlByShortUrl(shortUrl);
        url.setClicks(url.getClicks() + 1);
        url.setLastClicked(LocalDateTime.now());
        repository.save(url);
        return url.getClicks();
	}

	
	public Url getOriginalUrlByShortUrl(String shortUrl) {
		return this.repository.findByShortUrl(shortUrl)
				.orElseThrow(() -> new BusinessException(String.format("'%s' not found", shortUrl)));
	}
	

	public List<Url> getAllShortUrl() {
		return repository.findAll();
	}
	
	public Url findOrSaveUrl(String originalUrl) {
		Url url;
		
		Optional<Url> optinalUrl = repository.findByOriginalUrl(originalUrl);
		if (optinalUrl.isPresent()) {
			url = optinalUrl.get();
		} else {
			Url urlToSave = Url.builder().originalUrl(originalUrl)
					.shortUrl(generateShortUrl(originalUrl))
					.createdAt(LocalDateTime.now())
					.lastClicked(LocalDateTime.now())
					.build();
			url = this.repository.save(urlToSave);

		}
		return url;
	}

	public static String generateShortUrl(String originalUrl) {
		byte[] hash = Hashing.sha256().hashString(originalUrl, StandardCharsets.UTF_8).asBytes();

		StringBuilder shortUrl = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			int index = hash[i] & 0xFF;
			shortUrl.append(ALLOWED_CHARS.get(index % ALLOWED_CHARS.size()));
		}
		return shortUrl.toString();

	}
}
