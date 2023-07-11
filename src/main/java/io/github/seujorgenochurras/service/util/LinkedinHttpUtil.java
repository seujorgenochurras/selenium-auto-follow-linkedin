package io.github.seujorgenochurras.service.util;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.seujorgenochurras.domain.LinkedinPerson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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


   public static AsyncHttpUtil sendAsync(){
      return new AsyncHttpUtil();
   }
   public static HttpResponse<String> fetchPeople(int peopleAmount) {

      String finalUrl = DISCOVERY_PEOPLE_URL + "?decorationId=com.linkedin.voyager.dash.deco.relationships.DiscoveryEntityCollection-29" +
              "&count=" + peopleAmount +
              "&paginationToken=5659868640" +
              "&q=cohort" +
              "&reasons=List((sourceType:PYMK_ENTITY,reasonContext:PYMK_ENTITY))" +
              "&start=52";
      HttpRequest.Builder request = HttpRequest.newBuilder()
              .GET()
              .uri(tryCreateUriFor(finalUrl));

      addLinkedinHeadersToRequest(request);

      return trySendRequest(request.build(), HttpResponse.BodyHandlers.ofString());
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
         e.printStackTrace();
         throw new IllegalStateException(e.getMessage(), e.getCause());
      }
   }
   private static HttpRequest.Builder addLinkedinHeadersToRequest(HttpRequest.Builder httpRequestBuilder){
      Dotenv dotenv = Dotenv.load();
      //TODO user input, fix this shit
      String cookies = dotenv.get("COOKIES");
      String csrf = dotenv.get("CSRF");
      String accept = dotenv.get("ACCEPT");

      return httpRequestBuilder.headers("cookie", cookies, "csrf-token", csrf, "accept", accept);
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

   public static final class AsyncHttpUtil{
      private final ExecutorService httpExecutorService = Executors.newFixedThreadPool(10);

      public CompletableFuture<HttpResponse<String>> sendInviteToLinkedinPerson(LinkedinPerson linkedinPerson){
         HttpRequest connectionRequest = createConnectionInviteRequest(linkedinPerson);
         CompletableFuture<HttpResponse<String>> future = new CompletableFuture<>();
         httpExecutorService.submit(() ->
           future.completeAsync(() -> trySendRequest(connectionRequest, HttpResponse.BodyHandlers.ofString()))
         );
         return future;
      }
      private HttpRequest createConnectionInviteRequest(LinkedinPerson linkedinPerson){
         String jsonBody = "{\"invitee\":{\"inviteeUnion\":{\"memberProfile\":\"" + linkedinPerson.getUuid() + "\"}}}";

         String urlWithParams = PERSON_INTERACTION_URL + "?action=verifyQuotaAndCreateV2&" +
                 "decorationId=com.linkedin.voyager.dash.deco.relationships.InvitationCreationResultWithInvitee-2";

         HttpRequest.BodyPublisher requestBody = HttpRequest.BodyPublishers.ofString(jsonBody);
         HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                 .POST(requestBody)
                 .headers("content-type", "application/json; charset=UTF-8")
                 .uri(tryCreateUriFor(urlWithParams));

         return addLinkedinHeadersToRequest(requestBuilder).build();
      }
      public void killExecutor(){
         httpExecutorService.shutdown();
      }

   }

}
