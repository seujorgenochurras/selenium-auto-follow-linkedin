package io.github.seujorgenochurras.service;

import com.google.gson.Gson;
import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.domain.LinkedinPerson;
import io.github.seujorgenochurras.service.util.LinkedinHttpUtil;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class LinkedinService {

   private static final Logger logger = Logger.getLogger(LinkedinService.class.getName());

   public LinkedinDiscoveryResult fetchConnectablePeople(int personAmount) {
      HttpResponse<String> linkedinResponse = LinkedinHttpUtil.fetchPeople(personAmount);

      Gson gson = new Gson();

      return gson.fromJson(linkedinResponse.body(), LinkedinDiscoveryResult.class);
   }

   public CompletableFuture<HttpResponse<String>> sendConnectRequestTo(LinkedinPerson linkedinPerson) {
      LinkedinHttpUtil.AsyncHttpUtil asyncHttpUtil = LinkedinHttpUtil.sendAsync();

      return asyncHttpUtil.sendInviteToLinkedinPerson(linkedinPerson).whenComplete((response, e) -> asyncHttpUtil.killExecutor());
   }
   public void massSendConnectInvite(LinkedinDiscoveryResult linkedinDiscoveryResult){
      LinkedinHttpUtil.AsyncHttpUtil asyncHttpUtil = LinkedinHttpUtil.sendAsync();

      linkedinDiscoveryResult.getLinkedinPeople().forEach(linkedinPerson ->
              asyncHttpUtil.sendInviteToLinkedinPerson(linkedinPerson)
              .whenComplete(
                      (response, e) ->
                              System.out.println("Successfully sent invite to " + linkedinPerson.getUuid())));
   }

}
