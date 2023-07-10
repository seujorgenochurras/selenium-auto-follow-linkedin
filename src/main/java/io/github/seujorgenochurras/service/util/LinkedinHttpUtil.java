package io.github.seujorgenochurras.service.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class LinkedinHttpUtil {
   private static final Logger logger = Logger.getLogger(LinkedinHttpUtil.class.getName());
   private static final String PRIVATE_API_URL = "https://www.linkedin.com/voyager/api";
   private static final String DISCOVERY_PEOPLE_URL = PRIVATE_API_URL + "/voyagerRelationshipsDashDiscovery";
   //only POST
   private static final String PERSON_INTERACTION_URL = PRIVATE_API_URL + "/voyagerRelationshipsDashMemberRelationships";
   private static final HttpClient httpClient = HttpClient.newHttpClient();

   private LinkedinHttpUtil() {
   }

   public static HttpResponse<String> fetchPeople(int peopleAmount) {
      Dotenv dotenv = Dotenv.load();

      //TODO user input
      String cookies = dotenv.get("COOKIES");
      String csrf = dotenv.get("CSRF");
      String accept = dotenv.get("ACCEPT");

      String finalUrl = DISCOVERY_PEOPLE_URL + "?decorationId=com.linkedin.voyager.dash.deco.relationships.DiscoveryEntityCollection-29" +
              "&count=" + peopleAmount +
              "&paginationToken=9170735010" +
              "&q=cohort" +
              "&reasons=List((sourceType:PYMK_ENTITY,reasonContext:PYMK_ENTITY))" +
              "&start=52";
      HttpRequest request = HttpRequest.newBuilder()
              .GET()
              .uri(tryCreateUriFor(finalUrl))
              .header("cookie", cookies)
              .header("csrf-token", csrf)
              .header("accept", accept)
              .build();
      return trySendRequest(request, HttpResponse.BodyHandlers.ofString());
   }

   private static <T> HttpResponse<T> trySendRequest(HttpRequest request, java.net.http.HttpResponse.BodyHandler<T> bodyHandler) {
      try {

         HttpResponse<T> response = httpClient.send(request, bodyHandler);
         int httpStatusCode = response.statusCode();
         if (httpStatusCode < 200 || httpStatusCode > 299)
            throw new IOException("Request returned code " + httpStatusCode);
         return response;

      } catch (IOException | InterruptedException e) {
         logger.severe("Something went terrible wrong when sending the request");
         throw new IllegalStateException(e.getMessage(), e.getCause());
      }
   }

   private static URI tryCreateUriFor(String url) {
      try {
         return new URI(url);
      } catch (URISyntaxException e) {
         logger.severe(e.getMessage());
         e.printStackTrace();
         return null;
      }
   }


}
