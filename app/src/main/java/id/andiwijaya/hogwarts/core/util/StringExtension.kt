package id.andiwijaya.hogwarts.core.util

import id.andiwijaya.hogwarts.core.Constants.HYPEN

fun String?.orHypen() = this.takeIf { it.isNullOrBlank().not() } ?: HYPEN