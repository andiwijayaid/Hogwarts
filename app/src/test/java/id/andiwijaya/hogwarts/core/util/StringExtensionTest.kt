package id.andiwijaya.hogwarts.core.util

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.hogwarts.core.Constants.EMPTY_STRING
import id.andiwijaya.hogwarts.core.Constants.HYPHEN
import id.andiwijaya.hogwarts.core.Constants.ONE
import id.andiwijaya.hogwarts.core.Constants.PERCENT
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.util.DataDummy.faker
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `when invoke orHyphen with null should return -`() {
        val dummy: String? = null
        val result = dummy.orHyphen()
        assertThat(result).isEqualTo(HYPHEN)
    }

    @Test
    fun `when invoke orHyphen with empty string should return -`() {
        val dummy = EMPTY_STRING
        val result = dummy.orHyphen()
        assertThat(result).isEqualTo(HYPHEN)
    }

    @Test
    fun `when invoke orHyphen with non blank string should return its value`() {
        val dummy = faker.app.name()
        val result = dummy.orHyphen()
        assertThat(result).isEqualTo(dummy)
    }

    @Test
    fun `when invoke wrap it should return string with wrapper`() {
        val dummy = faker.app.name()
        val wrapper = PERCENT
        val result = dummy.wrap(wrapper)
        assertThat(result).isNotEqualTo(dummy)
        assertThat(result).isEqualTo("$wrapper$dummy$wrapper")
        assertThat(result[ZERO].toString()).isEqualTo(wrapper)
        assertThat(result[result.length - ONE].toString()).isEqualTo(wrapper)
    }

}