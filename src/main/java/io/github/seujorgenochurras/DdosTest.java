package io.github.seujorgenochurras;

import java.net.http.HttpClient;
import java.util.concurrent.*;

public class DdosTest {
   private static final ExecutorService executorService = Executors.newFixedThreadPool(100);
   private static final HttpClient httpClient = HttpClient.newHttpClient();

   public void stressRandomCepApi(int fromCep, int toCep) throws InterruptedException {
      for(int i = fromCep; i < toCep; i++){
            calculateCep(i).whenComplete((result, e) -> System.out.println(result));
      }
   }
   private CompletableFuture<String> calculateCep(int cep) {
      CompletableFuture<String> future = new CompletableFuture<>();
      executorService.submit(() -> {
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
         future.completeAsync(() -> "My thread is " + Thread.currentThread().getId()
                 + " cep result is " + (cep + 100));
      });
      return future;
   }
   public void exterminateTheFuture()  {
      executorService.shutdown();
   }


   private int fetchCep(int from, int to){

      return 0;
   }

}
