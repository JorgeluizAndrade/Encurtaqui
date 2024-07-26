package com.main.project.encurtaqui.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.main.project.encurtaqui.domain.entity.Url;

public interface UrlRepository extends MongoRepository<Url, String>{
	 Optional<Url> findByShortUrl(String shortUrl);
	 Optional<Url> findByOriginalUrl(String shortUrl);
}
