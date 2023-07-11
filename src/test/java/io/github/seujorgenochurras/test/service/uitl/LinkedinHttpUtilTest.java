package io.github.seujorgenochurras.test.service.uitl;

import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.domain.LinkedinPerson;
import io.github.seujorgenochurras.service.util.LinkedinHttpUtil;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static io.github.seujorgenochurras.test.service.LinkedinServiceTest.fetchPeople;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedinHttpUtilTest {
   private static final Logger logger = Logger.getLogger(LinkedinHttpUtilTest.class.getName());
   @Test
   void isFetchingPeople() {
      HttpResponse<String> response = LinkedinHttpUtil.fetchPeople(10);
      assertTrue(response.statusCode() > 200 || response.statusCode() < 299);
      assertFalse(response.body().isBlank());
   }

   @Test
   void isConnectingToSomeone() {
      LinkedinDiscoveryResult discoveryResult = fetchPeople(3);
      LinkedinPerson randomPerson = discoveryResult.getLinkedinPeople().get(0);

      CompletableFuture<HttpResponse<String>> asyncResponse = LinkedinHttpUtil.sendAsync()
              .sendInviteToLinkedinPerson(randomPerson);

      asyncResponse.whenComplete( (response, e) -> {
         assertTrue(response.statusCode() > 200 || response.statusCode() < 299);
         assertFalse(response.body().isBlank());
         logger.info("Successfully followed person " + randomPerson.getUuid());
      });
   }


}
