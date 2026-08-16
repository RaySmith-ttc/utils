import org.junit.jupiter.api.Test
import ru.raysmith.utils.eq
import ru.raysmith.utils.isZero
import ru.raysmith.utils.neq
import java.math.BigDecimal
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BigDecimalUtilsTests {

    @Test
    fun isZero() {
        assertTrue(BigDecimal.ZERO.isZero)
        assertTrue(BigDecimal("0").isZero)
        assertTrue(BigDecimal("0.00").isZero)
        assertTrue(BigDecimal("-0.0").isZero)

        assertFalse(BigDecimal("1").isZero)
        assertFalse(BigDecimal("-1").isZero)
        assertFalse(BigDecimal("0.0001").isZero)
        assertFalse(BigDecimal("-0.0001").isZero)
    }

    @Test
    fun eq() {
        val nullBd: BigDecimal? = null
        assertTrue(nullBd.eq(null))
        assertTrue(BigDecimal("1").eq(BigDecimal("1")))
        assertTrue(BigDecimal("1.0").eq(BigDecimal("1.00")))
        assertTrue(BigDecimal("0").eq(BigDecimal.ZERO))
        assertTrue(BigDecimal("123.456").eq(BigDecimal("123.4560")))

        assertFalse(BigDecimal("1").eq(BigDecimal("2")))
        assertFalse(BigDecimal("-1.0").eq(BigDecimal("1.0")))
        assertFalse(BigDecimal("0.1").eq(BigDecimal("0.01")))
        assertFalse(BigDecimal("1").eq(null))
        assertFalse(nullBd.eq(BigDecimal("1")))
    }

    @Test
    fun neq() {
        val nullBd: BigDecimal? = null
        assertTrue(BigDecimal("1").neq(BigDecimal("2")))
        assertTrue(BigDecimal("-1.0").neq(BigDecimal("1.0")))
        assertTrue(BigDecimal("0.1").neq(BigDecimal("0.01")))
        assertTrue(BigDecimal("1").neq(null))
        assertTrue(nullBd.neq(BigDecimal("1")))

        assertFalse(nullBd.neq(null))
        assertFalse(BigDecimal("1").neq(BigDecimal("1")))
        assertFalse(BigDecimal("1.0").neq(BigDecimal("1.00")))
        assertFalse(BigDecimal("0").neq(BigDecimal.ZERO))
        assertFalse(BigDecimal("123.456").neq(BigDecimal("123.4560")))
    }
}
