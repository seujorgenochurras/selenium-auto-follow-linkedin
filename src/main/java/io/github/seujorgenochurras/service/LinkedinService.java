package io.github.seujorgenochurras.service;

import com.google.gson.Gson;
import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.service.util.LinkedinHttpUtil;

import java.net.http.HttpResponse;

public class LinkedinService {


   public LinkedinDiscoveryResult   fetchConnectablePeople(int personAmount) {
      HttpResponse<String> linkedinResponse = LinkedinHttpUtil.fetchPeople(personAmount);

      Gson gson = new Gson();

      return gson.fromJson(linkedinResponse.body(), LinkedinDiscoveryResult.class);
   }

}
