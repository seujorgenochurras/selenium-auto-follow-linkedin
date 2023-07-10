package io.github.seujorgenochurras.utils;

import io.github.seujorgenochurras.domain.LinkedinDiscoveryResult;
import io.github.seujorgenochurras.domain.LinkedinPerson;

public class LinkedinDomainUtil {
   private LinkedinDomainUtil(){}
   public static boolean testPerson(LinkedinPerson linkedinPerson){
      if(linkedinPerson == null) return false;
      return linkedinPerson.getUuid().isBlank();
   }
   public static boolean testDiscoveryResult(LinkedinDiscoveryResult linkedinDiscoveryResult){
      if(linkedinDiscoveryResult == null) return false;
      if(linkedinDiscoveryResult.getLinkedinPeople() == null) return false;
      for (LinkedinPerson linkedinPerson : linkedinDiscoveryResult.getLinkedinPeople()) {
         if (!testPerson(linkedinPerson)) return false;
      }
      return true;
   }
}
