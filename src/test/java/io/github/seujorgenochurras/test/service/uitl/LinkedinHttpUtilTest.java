package io.github.seujorgenochurras.test.service.uitl;

import io.github.seujorgenochurras.service.util.LinkedinHttpUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpResponse;

class LinkedinHttpUtilTest {

   @Test
   void isFetchingPeople() {
      HttpResponse<String> response = LinkedinHttpUtil.fetchPeople(10);
      assertTrue(response.statusCode() > 200 || response.statusCode() < 299);
      assertFalse(response.body().isBlank());

   }

}
// https://www.linkedin.com/voyager/api/voyagerRelationshipsDashDiscovery?decorationId=com.linkedin.voyager.dash.deco.relationships.DiscoveryEntityCollection-29&count=10&paginationToken=9170735069&q=cohort&reasons=List((sourceType:PYMK_ENTITY,reasonContext:PYMK_ENTITY))&start=52
// https://www.linkedin.com/voyager/api/voyagerRelationshipsDashDiscovery?decorationId=com.linkedin.voyager.dash.deco.relationships.DiscoveryEntityCollection-29&count=10&paginationToken=9170735069&q=cohort&reasons=List((sourceType:PYMK_ENTITY,reasonContext:PYMK_ENTITY))
