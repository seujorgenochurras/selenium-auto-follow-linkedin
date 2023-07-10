package io.github.seujorgenochurras.domain;

public class LinkedinPerson {

   private String uuid;

   public String getUuid() {
      return uuid;
   }

   public LinkedinPerson setUuid(String uuid) {
      this.uuid = uuid;
      return this;
   }

   public LinkedinPerson(String uuid) {
      this.uuid = uuid;
   }

   public LinkedinPerson() {
   }
}
