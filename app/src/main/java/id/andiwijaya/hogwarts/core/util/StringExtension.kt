package id.andiwijaya.hogwarts.core.util

import id.andiwijaya.hogwarts.core.Constants.HYPHEN

fun String?.orHyphen() = this.takeIf { it.isNullOrBlank().not() } ?: HYPHEN

fun String.wrap(wrapper: String) = "$wrapper$this$wrapper"