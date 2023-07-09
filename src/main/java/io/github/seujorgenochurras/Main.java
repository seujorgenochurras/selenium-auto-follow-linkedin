package io.github.seujorgenochurras;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Main {
   public static void main(String[] args) {
      Future<String> future = new CompletableFuture<>();

      System.out.println("Hello world!");
   }
}