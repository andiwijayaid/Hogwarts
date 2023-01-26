package id.andiwijaya.hogwarts.core.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BooleanExtensionTest {

    @Test
    fun `when invoke orTrue with null should return true`() {
        val dummy: Boolean? = null
        val result = dummy.orTrue()
        assertTrue(result)
    }

    @Test
    fun `when invoke orTrue with false should return false`() {
        val dummy = false
        val result = dummy.orTrue()
        assertFalse(result)
    }

    @Test
    fun `when invoke orFalse with null should return false`() {
        val dummy: Boolean? = null
        val result = dummy.orFalse()
        assertFalse(result)
    }

    @Test
    fun `when invoke orFalse with true should return true`() {
        val dummy = true
        val result = dummy.orFalse()
        assertTrue(result)
    }

}