@file:Suppress("UNUSED_PARAMETER")

package com.linecorp.link.developers.txresult.core.event.token

import com.linecorp.link.developers.txresult.core.model.TransactionEvent

open class ServiceTokenEvent(
    open val contractId: String,
) : TransactionEvent
