
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {


  private final RestTemplate restTemplate;

  public TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) throws JsonProcessingException {
      // RestTemplate restTemplate = new RestTemplate();
      String stockURL = prepareUrl(symbol, from, to, getToken());
      // Used string class in tests that's why used here alsp
      String candleArray = restTemplate.getForObject(stockURL, String.class);
      ObjectMapper mapper = new ObjectMapper();
      // Fixed Local Date error
      mapper.registerModule(new JavaTimeModule());
      List<Candle> candleList = Arrays.asList(mapper.readValue(candleArray, TiingoCandle[].class));
      return candleList;
  }

  


  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.
  public static String prepareUrl(String symbol, LocalDate startDate, LocalDate endDate, String token) {
    String url =  "https://api.tiingo.com/tiingo/daily/"+ symbol +
            "/prices?startDate=" + startDate + "&endDate="
            + endDate + "&token=" + token;
    return url;
  }

  public static String getToken() {
    return "f71e7b5829c36a95c94a3940aa4d218d83c43031";
  }
}
