@file:Suppress("UNUSED_PARAMETER")

package com.linecorp.link.developers.txresult.core.event.item

open class ItemNonFungibleTokenEvent(
    contractId: String,
    open val tokenTypes: Collection<String>,
    open val tokenIndices: Collection<String> = emptyList(),
) : ItemTokenEvent(contractId)
