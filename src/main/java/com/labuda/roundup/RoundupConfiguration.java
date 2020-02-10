package com.labuda.roundup;

import com.labuda.roundup.api.ApiException;
import com.labuda.roundup.api.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * This class holds webClient configuration and defines few beans that are re-used in multiple places
 */

@Configuration
public class RoundupConfiguration {

    static Logger logger = LoggerFactory.getLogger(RoundupConfiguration.class);
    static Logger requestResponseLogger = LoggerFactory.getLogger(ApiService.class);

    @Value("classpath:token")
    private Resource tokenResource;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.datetimeformat}")
    private String apiDateTimeFormat;

    /**
     * This is the file with the Starling client token that is used by tests
     * It should be on the classpath and use JVM default encoding
     * I keep it with application.properties, Maven will put it on the classpath
     *
     * @return token used for testing
     */
    @Bean
    public String token() {
        try (BufferedReader reader = Files.newBufferedReader(tokenResource.getFile().toPath())) {
            return String.format("Bearer %s", reader.readLine());
        } catch (IOException e) {
            logger.error("Error reading token", e);
        }
        return null;
    }

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer ->
                                clientCodecConfigurer.defaultCodecs()
                                        .maxInMemorySize(Integer.MAX_VALUE)).build())
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "Mateusz Labuda")
                //filter requests/responses to log&capture errors globally from here
                .filter(requestLoggingFilter)
                .filter(errorResponseCapturingFilter)
                .build();
    }

    @Bean
    public String apiUrl() {
        return apiUrl;
    }

    @Bean
    public DateTimeFormatter apiDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(apiDateTimeFormat);
    }

    /**
     * This filter will let us handle webclient responses, we can log or re-throw any errors returned by Starling API
     */
    private ExchangeFilterFunction errorResponseCapturingFilter = ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
        if (clientResponse.statusCode().isError()) {
            requestResponseLogger.error("Response status {}", clientResponse.statusCode());
            //lack of consistency between error responses, will have to map Object
            return clientResponse.bodyToMono(Object.class)
                    //sometimes HTTP Client does not emit a response, in that case we return 404
                    .switchIfEmpty(ServerResponse.notFound().build())
                    .flatMap(error -> Mono.error(new ApiException(clientResponse.statusCode(), error.toString())));
        }
        requestResponseLogger.debug("Response status {}", clientResponse.statusCode());
        return Mono.just(clientResponse);
    });

    /**
     * This filter will let us handle webclient requests, we can log everything we send from here
     */
    private ExchangeFilterFunction requestLoggingFilter = ExchangeFilterFunction.ofRequestProcessor(request -> {
        requestResponseLogger.debug("Request: {} {} Headers : {}",
                request.method(),
                request.url(),
                request.headers()
                        .entrySet()
                        .stream()
                        .map(e -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining(";")));
        return Mono.just(request);
    });
}
