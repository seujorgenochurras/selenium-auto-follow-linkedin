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
      if(!json.isJsonObject()) throw new JsonParseException("Json is not an object");
      ArrayList<LinkedinPerson> linkedinPeople = new ArrayList<>();

      JsonElement elements = json.getAsJsonObject().get("*elements");
      elements.getAsJsonArray().forEach(jsonElement ->
              linkedinPeople.add(new LinkedinPerson(getOnlyPersonUuid(jsonElement.getAsString()))));
      return linkedinPeople;
   }
   private static String getOnlyPersonUuid(String str){
      return str
              .replace("urn:li:fsd_discoveryEntityViewModel:(", "")
              .replace(",PYMK)", "");
   }

}
