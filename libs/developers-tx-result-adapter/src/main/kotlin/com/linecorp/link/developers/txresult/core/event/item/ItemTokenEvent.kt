@file:Suppress("UNUSED_PARAMETER")

package com.linecorp.link.developers.txresult.core.event.item

import com.linecorp.link.developers.txresult.core.model.TransactionEvent

open class ItemTokenEvent(
    open val contractId: String,
) : TransactionEvent
