package io.github.seujorgenochurras;

import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.service.LinkedinService;

public class Main {
   public static void main(String[] args) {
      LinkedinService service = new LinkedinService();
      LinkedinDiscoveryResult discoveryResult = service.fetchConnectablePeople(99);

      service.massSendConnectInvite(discoveryResult);

   }
}
