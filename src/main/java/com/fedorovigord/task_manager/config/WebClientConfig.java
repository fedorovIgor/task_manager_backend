package com.fedorovigord.task_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientSecurityCustomizer(
            ReactiveClientRegistrationRepository clientRegistrations,
            ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClientService) {

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        clientRegistrations, oAuth2AuthorizedClientService);

    /*AnonymousReactiveOAuth2AuthorizedClientManager manager =
            new AnonymousReactiveOAuth2AuthorizedClientManager(clientRegistrations,
                    new UnAuthenticatedServerOAuth2AuthorizedClientRepository());*/

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials(clientCredentialsGrantBuilder ->
                                clientCredentialsGrantBuilder.accessTokenResponseClient(new IdamReactiveOAuth2AccessTokenResponseClient()))
                        .password()
                        .build();

        manager.setAuthorizedClientProvider(authorizedClientProvider);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);

        oauth2.setDefaultClientRegistrationId("keycloak");

        return WebClient.builder()
                .filter(oauth2)
                .build();
    }


}
