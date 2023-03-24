# LINE Blockchain Developers SDK for Kotlin Spring support

## ChainProperties

Depending on which chain(test-net, main-net) is being used for your dApp application, some configuration is required
like below.

```yaml
line:
  lbd:
    config:
      chain:
        is-main-net: false
        bech32-hrp: tlink
```

Above configuration is for test-net, and if the dApp needs main-net, then below one is working.

```yaml
line:
  lbd:
    config:
      chain:
        is-main-net: true
        bech32-hrp: link
```

The configured properties are required to handle transaction result response. 
For example `DomainTxSummaryAdapterV1`will be used to adapt [legacy transaction result](https://docs-blockchain.line.biz/api-guide/category-transactions#v1-transactions-txHash-get) into [new transaction result](https://docs-blockchain.line.biz/api-guide/category-transactions#v2-transactions-txHash-get).

## Auto-Configurations
There are automatically generated beans such as `lbdWebClient`, `jacksonObjectMapperBuilder` and transaction result adapters.
* lbdWebClient: This is `WebClient`, so can be used to call [APIs](https://docs-blockchain.line.biz/api-guide/API-Reference), if you will use spring-framework and `WebClient`.
* jacksonObjectMapperBuilder: This is required to handle transaction result in a response. More specifically, you can see there is `events` and its type is `TransactionEvent`, which is super type of each concrete event. Because of that, custom deserializer is required and the `jacksonObjectMapperBuilder` is dealing with it.
* transaction result adapters: if you see `TxResultAutoConfiguration`, then you can figure out there are default beans for adapters.

