package controller;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import org.example.config.Routes;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Log4j
@Component
public class ApiControllers {
    private final Routes routes;

    public ApiControllers(Routes routes) {
        this.routes = routes;
    }

    public Response getResponseFromActivityURL() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        log.debug("ApiController: GET requested");
        return given().contentType(ContentType.JSON).when().get(routes.getActivityURL());
    }

}
