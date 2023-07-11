package io.github.seujorgenochurras.test.service;

import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.service.LinkedinService;
import org.junit.jupiter.api.Test;

import static io.github.seujorgenochurras.utils.LinkedinDomainUtil.testDiscoveryResult;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedinServiceTest {
   private static final LinkedinService linkedinService = new LinkedinService();

   public static LinkedinDiscoveryResult fetchPeople(int peopleAmount) {
      return linkedinService.fetchConnectablePeople(peopleAmount);
   }

   @Test
   void isFetchingConnectablePeople() {
      LinkedinDiscoveryResult linkedinDiscoveryResult = linkedinService.fetchConnectablePeople(10);
      assertTrue(testDiscoveryResult(linkedinDiscoveryResult));
   }

   @Test
   void isMassiveInvitingRandomPeople() {
      LinkedinDiscoveryResult discoveryResult = fetchPeople(4);

      linkedinService.massSendConnectInvite(discoveryResult);
   }

}
