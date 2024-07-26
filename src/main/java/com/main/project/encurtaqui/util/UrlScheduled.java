package com.main.project.encurtaqui.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.stereotype.Component;

import com.main.project.encurtaqui.domain.entity.Url;
import com.main.project.encurtaqui.repository.UrlRepository;

import ch.qos.logback.classic.Logger;


@Component
public class UrlScheduled {
	@Autowired
	private UrlRepository urlRepository;
	
	
    private final Logger logger = (Logger) LoggerFactory.getLogger(SchedulingConfiguration.class);

    private static final String TIME_ZONE = "America/Sao_Paulo";


	@Scheduled(cron = "*/20 * * * * *", zone = TIME_ZONE)
	public void deleteInativeUrl() {
		LocalDateTime lastClikOneMonthAgo = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);

		List<Url> urls = urlRepository.findAll();

		for (Url url : urls) {
			if (url.getLastClicked() == null || url.getLastClicked().isBefore(lastClikOneMonthAgo)) {
				urlRepository.delete(url);
			}
		}
	}

}
