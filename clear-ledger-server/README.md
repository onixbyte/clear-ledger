# Clear Ledger

## File Structure

```text
clear-ledger-server/src/main/java
├───common
│   └───Formatters.java
├───config
│   ├───CacheConfig.java
│   ├───ConcurrentConfig.java
│   ├───ConcurrentProperty.java
│   ├───CorsConfig.java
│   ├───CorsProperty.java
│   ├───JacksonConfig.java
│   ├───ScheduleConfig.java
│   ├───SecurityConfig.java
│   └───WebConfig.java
├───constant
│   └───IdType.java
├───controller
│   ├───advice
│   │   └───ApplicationExceptionAdvice.java
│   ├───AuthController.java
│   ├───LedgerController.java
│   ├───TransactionController.java
│   └───UserController.java
├───data
│   ├───biz
│   │   ├───BizLedger.java
│   │   └───BizUser.java
│   ├───entity
│   │   ├───table
│   │   │   ├───LedgerTableDef.java
│   │   │   ├───TransactionTableDef.java
│   │   │   ├───UserLedgerTableDef.java
│   │   │   └───UserTableDef.java
│   │   ├───Ledger.java
│   │   ├───Transaction.java
│   │   ├───User.java
│   │   ├───UserLedger.java
│   │   └───ViewTransaction.java
│   ├───request
│   │   ├───CreateLedgerRequest.java
│   │   ├───CreateTransactionRequest.java
│   │   ├───UpdateLedgerRequest.java
│   │   ├───UpdateTransactionRequest.java
│   │   ├───UserLoginRequest.java
│   │   └───UserRegisterRequest.java
│   └───response
│       ├───BizExceptionResponse.java
│       ├───TransactionResponse.java
│       └───UserResponse.java
├───exception
│   ├───BizException.java
│   ├───ServiceUnavailableException.java
│   └───UnauthenticatedException.java
├───filter
├───generator
│   ├───LedgerIdCreator.java
│   ├───TransactionIdCreator.java
│   └───UserIdCreator.java
├───property
│   ├───ConcurrentProperty.java
│   └───CorsProperty.java
├───repository
│   ├───LedgerRepository.java
│   ├───TransactionRepository.java
│   ├───UserLedgerRepository.java
│   └───UserRepository.java
├───security
│   ├───token
│   │   └───UsernamePasswordToken.java
│   ├───UserAuthenticationFilter.java
│   ├───UserHolder.java
│   ├───UserInterceptor.java
│   └───UsernamePasswordAuthenticationProvider.java
├───service
│   ├───AuthService.java
│   ├───LedgerService.java
│   ├───SerialService.java
│   ├───TransactionService.java
│   └───UserService.java
└───ClearLedgerServerApplication.java
```

