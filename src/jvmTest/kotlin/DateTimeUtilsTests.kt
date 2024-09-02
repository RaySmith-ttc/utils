import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.raysmith.utils.*
import java.time.*
import java.util.*
import kotlin.test.assertTrue

class DateTimeUtilsTests {
    @Test
    fun now() {
        val expected = LocalDateTime.now()
        val actual = ru.raysmith.utils.now()
        assertTrue { Duration.between(expected, actual).seconds < 1 }
    }

    @Test
    fun nowTime() {
        val expected = LocalTime.now()
        val actual = ru.raysmith.utils.nowTime()
        assertTrue { Duration.between(expected, actual).seconds < 1 }
    }

    @Test
    fun today() {
        val expected = LocalDate.now()
        val actual = ru.raysmith.utils.today()
        assertEquals(expected, actual)
    }

    @Test
    fun yesterday() {
        val expected = LocalDate.now().minusDays(1)
        val actual = ru.raysmith.utils.yesterday()
        assertEquals(expected, actual)
    }

    @Test
    fun tomorrow() {
        val expected = LocalDate.now().plusDays(1)
        val actual = ru.raysmith.utils.tomorrow()
        assertEquals(expected, actual)
    }

    private fun ZoneId.plusTwoHours(): ZoneId {
        val offset = rules.getOffset(LocalDateTime.now())
        val totalSeconds = offset.totalSeconds + (2 * 3600)
        val cappedTotalSeconds = totalSeconds.coerceIn(ZoneOffset.MIN.totalSeconds, ZoneOffset.MAX.totalSeconds)
        return if (cappedTotalSeconds == totalSeconds) {
            ZoneOffset.ofTotalSeconds(cappedTotalSeconds)
        } else {
            // Добавляем оставшееся время к GMT-12:00
            val remainingHours = (totalSeconds - cappedTotalSeconds) / 3600
            val remainingMinutes = ((totalSeconds - cappedTotalSeconds) % 3600) / 60
            ZoneOffset.ofHoursMinutes(-12 + remainingHours, remainingMinutes)
        }
    }
    @Test
    fun nowZoned() {
        val expected = ZonedDateTime.now().withZoneSameInstant(zoneId.plusTwoHours())
        val actual = nowZoned(zoneId.plusTwoHours())
        assertTrue { Duration.between(expected, actual).seconds < 1 }
    }

    @Test
    fun atZone() {
        val date = LocalDateTime.of(2020, 1, 1, 12, 0) // +03:00

        val expected = LocalDateTime.of(2020, 1, 1, 14, 0) // +05:00
        val actual = date.atZone(ZoneId.of("+03:00"), ZoneId.of("+05:00"))

        assertEquals(expected, actual)
    }

    @Test
    fun firstDayOfYear() {
        val expected = LocalDate.of(LocalDate.now().year, Month.JANUARY, 1)
        val actual = LocalDate.now().firstDayOfYear()
        assertEquals(expected, actual)
    }

    @Test
    fun firstDayOfMonth() {
        val expected = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1)
        val actual = LocalDate.now().firstDayOfMonth()
        assertEquals(expected, actual)
    }

    @Test
    fun lastDayOfYear() {
        val expected = LocalDate.of(LocalDate.now().year, Month.DECEMBER, 31)
        val actual = LocalDate.now().lastDayOfYear()
        assertEquals(expected, actual)
    }

    @Test
    fun lastDayOfMonth() {
        val expected = LocalDate.now().withDayOfMonth(LocalDate.now().month.length(LocalDate.now().isLeapYear))
        val actual = LocalDate.now().lastDayOfMonth()
        assertEquals(expected, actual)
    }

    @Test
    fun startOfDay() {
        val expected = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val actual = LocalDateTime.now().startOfDay()
        assertEquals(expected, actual)
    }

    @Test
    fun endOfDay() {
        val expected = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        val actual = LocalDateTime.now().endOfDay()
        assertEquals(expected, actual)
    }

    @Test
    fun atEndOfDay() {
        val expected = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        val actual = LocalDate.now().atEndOfDay()
        assertEquals(expected, actual)
    }

    private val zoneId = ZoneId.systemDefault()

    @Test
    fun toLocalDateToDate() {
        val date = Date()
        val expected = date.toInstant().atZone(zoneId).toLocalDate()
        val actual = date.toLocalDate()
        assertEquals(expected, actual)
    }

    @Test
    fun toLocalDateTimeToDate() {
        val dateTime = LocalDateTime.now()
        val expected = Date.from(dateTime.atZone(zoneId).toInstant())
        val actual = dateTime.toDate()
        assertEquals(expected, actual)
    }

    @Test
    fun inPeriodMorningAndEvening() {
        val start = LocalTime.of(10, 0)
        val end = LocalTime.of(18, 0)

        val time1 = LocalTime.of(12, 0)
        val time2 = LocalTime.of(20, 0)
        val time3 = LocalTime.of(8, 0)

        assertEquals(true, time1.inPeriod(start, end))
        assertEquals(false, time2.inPeriod(start, end))
        assertEquals(false, time3.inPeriod(start, end))
    }

    @Test
    fun inPeriodEveningAndMorning() {
        val start = LocalTime.of(18, 0)
        val end = LocalTime.of(10, 0)

        val time1 = LocalTime.of(12, 0)
        val time2 = LocalTime.of(20, 0)
        val time3 = LocalTime.of(8, 0)

        assertEquals(false, time1.inPeriod(start, end))
        assertEquals(true, time2.inPeriod(start, end))
        assertEquals(true, time3.inPeriod(start, end))
    }
}