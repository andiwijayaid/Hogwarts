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
        val expectedResult = faker.app.name()
        val result = expectedResult.orHyphen()
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `when invoke wrap it should return string with wrapper`() {
        val dummy = faker.app.name()
        val wrapper = PERCENT
        val expectedResult = "$wrapper$dummy$wrapper"
        val result = dummy.wrap(wrapper)

        assertThat(result).isNotEqualTo(dummy)
        assertThat(result).isEqualTo(expectedResult)
        assertThat(result[ZERO].toString()).isEqualTo(wrapper)
        assertThat(result[result.length - ONE].toString()).isEqualTo(wrapper)
    }

}