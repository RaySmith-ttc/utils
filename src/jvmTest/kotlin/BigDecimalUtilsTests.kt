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

    @Test
    fun iterableEq() {
        val nullList: List<BigDecimal>? = null
        assertTrue(nullList.eq(null))
        assertTrue(listOf(BigDecimal("1.0"), BigDecimal("2.00")).eq(listOf(BigDecimal("1.000"), BigDecimal("2"))))
        assertTrue(setOf(BigDecimal("0"), BigDecimal("1")).eq(listOf(BigDecimal("0.00"), BigDecimal("1.0"))))

        assertFalse(listOf(BigDecimal("1"), BigDecimal("2")).eq(listOf(BigDecimal("1"), BigDecimal("3"))))
        assertFalse(listOf(BigDecimal("1")).eq(listOf(BigDecimal("1"), BigDecimal("2"))))
        assertFalse(nullList.eq(listOf(BigDecimal("1"))))
        assertFalse(listOf(BigDecimal("1")).eq(null))
    }

    @Test
    fun iterableNeq() {
        val nullList: List<BigDecimal>? = null
        assertTrue(listOf(BigDecimal("1"), BigDecimal("2")).neq(listOf(BigDecimal("1"), BigDecimal("3"))))
        assertTrue(listOf(BigDecimal("1")).neq(listOf(BigDecimal("1"), BigDecimal("2"))))
        assertTrue(nullList.neq(listOf(BigDecimal("1"))))
        assertTrue(listOf(BigDecimal("1")).neq(null))

        assertFalse(nullList.neq(null))
        assertFalse(listOf(BigDecimal("1.0"), BigDecimal("2.00")).neq(listOf(BigDecimal("1.000"), BigDecimal("2"))))
        assertFalse(setOf(BigDecimal("0"), BigDecimal("1")).neq(listOf(BigDecimal("0.00"), BigDecimal("1.0"))))
    }
}
