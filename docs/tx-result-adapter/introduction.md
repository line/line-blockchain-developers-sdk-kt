# Introduction

The LINE Blockchain Developers tx-result SDK for Java makes it easy to handle result of a transaction from LINE Blockchain.

# Modules
## tx-result-adapter
This module helps parse result of transaction into a certain structured object, which is [`TxResult`](../../libs/developers-tx-result-adapter/src/main/kotlin/com/linecorp/link/developers/txresult/core/model/models.kt#L7).
TxResult has all required information such as block height, txHash, result status, what you sent(messages) and what happened(events). For details, please refer to following section.

### Install

#### Gradle Kotlin DSL
```
implementation("com.linecorp.lbd:tx-result-adapter:${latest-version}")
```

#### Gradle Groovy DSL
```
implementation 'com.linecorp.lbd:tx-result-adapter:${latest-version}'
```

#### Test
You can run all the unit tests by following scripts.

```
./gradlew test
```

## Package structure
### chain
* account: has classes for address and public key.
* crypto: classes to help get address.
### txresult
* adapter: has only one interface, which is TxAdapter. The interface is to convert tx-result from old chains(Cashew/Daphne) or new chains(Ebony/Finschia) to TxResult.
* core: has core model and events.
    * events: represents what happened and contains all information for a client.
    * model: the models that a client finally being use.
* v1.raw: has raw transaction from old chains(Cashew/Daphne) or new chains(Ebony/Finschia) and implementations of TxAdapter.
    * adapter: has implementations of TxAdapter.
    * model: has model classes for raw transaction.
* util: utility classes being used over core and raw.
* exception: has exceptions.

## structure of TxResult
TxResult is consist of properties such as summary, txMessages, txEvents and details of each one is as followings.

### summary
Literally, it has summarized properties such as height, txIndex, txHash, timestamp, signers and result. Details and type of each one are as followings.
#### properties
* height: Long
* txIndex: Int
* txHash: String
* timestamp: Long (*)
* signer: Array<TxSigner>
  * address: String
* result: TxStatusResult
  * code
  * codeSpace
  * status: String (SUCCEEDED | FAILED)

> Note
> 
> `timestamp` is Long type because it is milliseconds of UNIX Epoch

### txMessages
`txMessages` means what a client sends in a transaction. All available messages are found at [LINE Blockchain Docs- messages](https://docs-blockchain.line.biz/api-guide/Callback-Response#message)
#### properties
* msgIndex: Int
* requestType: String
* details: Object|Any (*)

> Note
> 
> "details" in each message is object type, since it is hard to provide concrete type of data when we support smart-contract. 
>   With smart-contract, developer or owner of the smart-contract can define own messages with custom properties, which we are not able to figure out its structure or properties.

### txEvents
Event means generally what happened, so `txEvents` means what happened on chain caused by the messages, and there are state changes.
Also, an event has required data in its own properties.

Please find all possible events at [events](https://docs-blockchain.line.biz/api-guide/Callback-Response#event)
