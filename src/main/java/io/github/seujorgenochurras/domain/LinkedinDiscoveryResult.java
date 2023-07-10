package io.github.seujorgenochurras.domain;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.seujorgenochurras.adapter.LinkedinDiscoveryResultAdapter;

import java.util.ArrayList;

public class LinkedinDiscoveryResult {
   @SerializedName("*elements")
   @JsonAdapter(LinkedinDiscoveryResultAdapter.class)
   private ArrayList<LinkedinPerson> linkedinPeople = new ArrayList<>();

   public ArrayList<LinkedinPerson> getLinkedinPeople() {
      return linkedinPeople;
   }

   public LinkedinDiscoveryResult setLinkedinPeople(ArrayList<LinkedinPerson> linkedinPeople) {
      this.linkedinPeople = linkedinPeople;
      return this;
   }
}
