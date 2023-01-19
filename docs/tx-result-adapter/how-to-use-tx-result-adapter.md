### Compatibility with current tx-result response

We
have [`RawTransactionResult`](../../libs/developers-tx-result-adapter/src/main/kotlin/com/linecorp/link/developers/txresult/v1/raw/model/raw-tx-result-models.kt#L27)
and it is compatible with `responseData` of each response from below APIs.

* [Retrieve transaction information](https://docs-blockchain.line.biz/api-guide/category-transactions#v1-transactions-txHash-get)
* [Retrieve service wallet transaction history](https://docs-blockchain.line.biz/api-guide/category-service-wallets/retrieve#v1-wallets-walletAddress-transactions-get)
* [Retrieve user wallet transaction history](https://docs-blockchain.line.biz/api-guide/category-users/retrieve#v1-users-userId-transactions-get)

#### Sample code

Even though you're new
to [`WebClient` of spring-framework](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/), we believe you
can figure out the below codes.

```kotlin
val webClient = WebClient.create("http://localhost:29999")
val txResultResponse = webClient.get()
    .uri("/v1/transactions/{txHash}", "61AB8A054D47CA05E4ABE591B929282CBCD7DACD5A4C8259020C566F0EC186BE")
    .accept(MediaType.APPLICATION_JSON)
    .retrieve()
    .bodyToMono<GenericResponse<RawTransactionResult>>().block()
```

You can see the all the codes
at [OpenApiRawTransactionParserTest](../../libs/developers-tx-result-adapter/src/main/kotlin/com/linecorp/link/developers/txresult/v1/raw/adapter/OpenApiRawTransactionParserTest.kt)

### Adapting to new transaction result

We need a way to adapt(convert) old transaction result in sort of raw format, which is very dependent on the chain's to
new structured transaction result.

#### Basic adapting flow

Adapting flow is simple as below.

 ```
 Old(current) transaction result ---> raw-transaction result ---> new structured transaction result.
 ```

#### How to adapt old one to new one.

##### Adapting(converting) to raw-transaction result

```kotlin
 // using default adapters for summary, messages and events
val lbdTxResultAdapter: TxResultAdapter<RawTransactionResult, TxResult> = LbdTxEventsAdapterV1 (HrpPrefix.TEST_NET)
/*
We can use custom adapters for summary, messages and events
val customTxResultSummaryAdapter: TxResultAdapter<RawTransactionResult, TxResultSummary> = CustomTxSummaryAdapterV1();
val customTxMessagesAdapter: TxResultAdapter<RawTransactionResult, Set<TxMessage>> = CustomTxMessageAdapterV1();
val customTxEventsAdapter: TxResultAdapter<RawTransactionResult, Set<TransactionEvent>> = CustomTxMessageAdapterV1();
val lbdTxResultAdapter: TxResultAdapter<RawTransactionResult, TxResult> = LbdTxEventsAdapterV1(
    HrpPrefix.TEST_NET,
    customTxResultSummaryAdapter,
    customTxMessagesAdapter,
    customTxEventsAdapter
)
*/
val txResultResponse = getTxResult(txHash)
val newTransactionResult = lbdTxResultAdapter.adapt(txResultResponse.responseData)
println(newTransactionResult.summary)
println(newTransactionResult.txMessages)
println(newTransactionResult.txEvents)
```

