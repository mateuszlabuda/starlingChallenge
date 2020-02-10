package com.labuda.roundup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * This class is responsible for routing
 */

@Configuration
public class RoundupRouterConfiguration {

    @Autowired
    private RoundupHandler roundupHandler;

    @Bean
    RouterFunction<ServerResponse> roundupRoutes() {
        return route()
                .GET("/accounts", accept(MediaType.APPLICATION_JSON), roundupHandler::accountsGet)
                .GET("/account/{accountUid}/savings-goals", accept(MediaType.APPLICATION_JSON), roundupHandler::accountGetSavingsGoals)
                .PUT("/account/{accountUid}/savings-goals", accept(MediaType.APPLICATION_JSON), roundupHandler::accountPutSavingGoal)
                .DELETE("/account/{accountUid}/savings-goals/{savingsGoalUid}", accept(MediaType.APPLICATION_JSON), roundupHandler::deleteSavingGoal)
                .PUT("/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}", accept(MediaType.APPLICATION_JSON), roundupHandler::accountPutSavingsGoalAddMoney)
                .GET("/account/{accountUid}/category/{categoryUid}", accept(MediaType.APPLICATION_JSON), roundupHandler::accountGetTransactionsSince)
                .GET("/account/{accountUid}/category/{categoryUid}/transactions-between", accept(MediaType.APPLICATION_JSON), roundupHandler::accountGetTransactionsBetween)
                .GET("/roundup", accept(MediaType.APPLICATION_JSON), roundupHandler::roundupAll)
                .build();
    }
}
