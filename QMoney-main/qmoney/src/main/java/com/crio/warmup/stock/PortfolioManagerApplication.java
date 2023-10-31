
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManagerImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]

            /*
                Reading json 
             */
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {

    ObjectMapper objmp = getObjectMapper();
    File file = resolveFileFromResources(args[0]);

    // Converting into bytes for faster working.
    byte[] byteTrade = Files.readAllBytes(file.toPath());
    String allTradesinBytes = new String(byteTrade, StandardCharsets.UTF_8);
    
    PortfolioTrade[] allTrades = objmp.readValue(allTradesinBytes, PortfolioTrade[].class);

    // String result = new String(Files.readAllBytes(Paths.get(a)));

    List<String> allSymbols = new ArrayList<>();

    for (PortfolioTrade allTrade : allTrades) {
      allSymbols.add(allTrade.getSymbol());
    }
    return allSymbols;

  }


  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.


  // Tiingo API Integration

  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename)).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/atharvapatil19202-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@3149";
     String functionNameFromTestFileInStackTrace = "mainReadFile";
     String lineNumberFromTestFileInStackTrace = "26";


    return Arrays.asList(valueOfArgument0, resultOfResolveFilePathArgs0,
            toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
            lineNumberFromTestFileInStackTrace);
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {

    File file = resolveFileFromResources(args[0]);
    System.out.println(file);
    LocalDate endDate = LocalDate.parse(args[1]);

    byte[] byteArray = Files.readAllBytes(file.toPath());
    String content = new String(byteArray, StandardCharsets.UTF_8);

    ObjectMapper mapper = getObjectMapper();
    PortfolioTrade[] portfolioTrades = mapper.readValue(content, PortfolioTrade[].class);

    List<TotalReturnsDto> totalReturnsDtoList = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();

    List<String> symbols = new ArrayList<>();

    for(PortfolioTrade portfolioTrade: portfolioTrades){
      String stockURL = prepareUrl(portfolioTrade,endDate, getToken());
      TiingoCandle[] candleArray = restTemplate.getForObject(stockURL, TiingoCandle[].class);
      int candleArrayLength = candleArray.length;
      totalReturnsDtoList.add(new TotalReturnsDto(portfolioTrade.getSymbol(), candleArray[candleArrayLength - 1].getClose()));
    }

    Collections.sort(totalReturnsDtoList, new Comparator<TotalReturnsDto>() {
      @Override
      public int compare(TotalReturnsDto o1, TotalReturnsDto o2) {
        if(o1.getClosingPrice() < o2.getClosingPrice())
        return -1;
        if(o1.getClosingPrice() > o2.getClosingPrice())
          return 1;
        return 0;
      }
    });

    for(TotalReturnsDto totalReturnsDto: totalReturnsDtoList){
      symbols.add(totalReturnsDto.getSymbol());
    }

    return symbols;
  }

  

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    List<PortfolioTrade> portfolioTrades = getObjectMapper().readValue(
            resolveFileFromResources(filename), new TypeReference<List<PortfolioTrade>>() {});
    return portfolioTrades;
  }


  public static String getToken() {
    return "f71e7b5829c36a95c94a3940aa4d218d83c43031";
  }

  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String url =  "https://api.tiingo.com/tiingo/daily/"+ trade.getSymbol() +
                  "/prices?startDate=" + trade.getPurchaseDate() + "&endDate="
                  + endDate + "&token=" + token;
    return url;
  }




  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
    return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
     return candles.get(candles.size()-1).getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    RestTemplate restTemplate = new RestTemplate();
    String stockURL = prepareUrl(trade, endDate, getToken());
    TiingoCandle[] candleArray = restTemplate.getForObject(stockURL, TiingoCandle[].class);
    List<Candle> candleList = Arrays.asList(candleArray);
    return candleList;
  }



//  TODO : Find the closing price of a stock on the given end date.
//   If the closing price is not available for the given end date,take the last date on which the market was open.
//   For e.g, if on 12-10-2020 the market was closed , you have to take endDate as 11-10-2020.
//   If the market is closed on this day as well, take 10-10-2020 and so on.
//   Implement your solution by following the TODOs for mainCalculateSingleReturn(String[] args) in PortfolioManagerApplication.java
//   Verify your solution by running the tests
  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
     LocalDate currDate = LocalDate.parse(args[1]);
     List<AnnualizedReturn> annualizedReturns = new ArrayList<>();
     List<PortfolioTrade> portFolioTrades = readTradesFromJson(args[0]);
     for(PortfolioTrade trade: portFolioTrades){
       List<Candle> candleList = fetchCandles(trade, currDate, getToken());
       annualizedReturns.add(calculateAnnualizedReturns(currDate, trade, getOpeningPriceOnStartDate(candleList), getClosingPriceOnEndDate(candleList)));
     }

     return annualizedReturns.stream()
             .sorted((a1, a2) -> Double.compare(a2.getAnnualizedReturn(), a1.getAnnualizedReturn()))
             .collect(Collectors.toList());
  }


  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
      double totalReturn = (sellPrice - buyPrice) / buyPrice;
      LocalDate currentDate =trade.getPurchaseDate();
      double totalYears = ChronoUnit.DAYS.between(currentDate, endDate) / 365.2422;
      double annualized_returns = Math.pow((1.0 + totalReturn),  (1.0 / totalYears)) - 1;
      return new AnnualizedReturn(trade.getSymbol(), annualized_returns, totalReturn);
  }


  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  private static String readFileAsString(String file) throws URISyntaxException, IOException {
    byte[] byteArray = Files.readAllBytes(resolveFileFromResources(file).toPath());
    String content = new String(byteArray, StandardCharsets.UTF_8);
    return content;
  }

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(file);
       ObjectMapper objectMapper = getObjectMapper();
    PortfolioManager portfolioManager =
            PortfolioManagerFactory.getPortfolioManager(new RestTemplate());
    List<PortfolioTrade> portfolioTrades =
            objectMapper.readValue(contents, new TypeReference<>() {
            });
    return portfolioManager.calculateAnnualizedReturn(portfolioTrades , endDate);
  }


  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    // printJsonObject(mainCalculateSingleReturn(args));
    printJsonObject(mainCalculateReturnsAfterRefactor(args));

  }
}
