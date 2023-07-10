package io.github.seujorgenochurras.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.seujorgenochurras.domain.LinkedinPerson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LinkedinDiscoveryResultAdapter implements JsonDeserializer<ArrayList<LinkedinPerson>> {
   @Override
   public ArrayList<LinkedinPerson> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      if(!json.isJsonArray()) throw new JsonParseException("Json is not an array");
      ArrayList<LinkedinPerson> linkedinPeople = new ArrayList<>();
      json.getAsJsonArray().forEach(jsonElement -> linkedinPeople.add(new LinkedinPerson(jsonElement.getAsString())));
      return linkedinPeople;
   }
}
