package net.dg.newsscrapingapi.performance;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class NewsControllerPerformanceTesting extends Simulation {

  HttpProtocolBuilder httpProtocol =
      http.baseUrl("http://localhost:5000")
          .acceptLanguageHeader("en-US,en;q=0.5")
          .acceptEncodingHeader("gzip, deflate")
          .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

  ScenarioBuilder scenarioGetAllNews =
      CoreDsl.scenario("Get all news simulation")
          .repeat(1000)
          .on(
              exec(
                  http("getAllNews method}")
                      .get("/api/v1/news?page=1&order=ASC")
                      .check(status().is(200))));

  {
    setUp(scenarioGetAllNews.injectOpen(atOnceUsers(100))).protocols(httpProtocol);
  }
}
