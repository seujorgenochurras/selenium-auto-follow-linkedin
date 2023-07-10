package io.github.seujorgenochurras.test.service;

import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.service.LinkedinService;
import org.junit.jupiter.api.Test;

import static io.github.seujorgenochurras.utils.LinkedinDomainUtil.testDiscoveryResult;
import static org.junit.jupiter.api.Assertions.*;

class LinkedinServiceTest {
   private final LinkedinService linkedinService = new LinkedinService();
  @Test
  void isFetchingConnectablePeople(){

    LinkedinDiscoveryResult linkedinDiscoveryResult = linkedinService.fetchConnectablePeople(10);
    assertTrue(testDiscoveryResult(linkedinDiscoveryResult));
  }




}
