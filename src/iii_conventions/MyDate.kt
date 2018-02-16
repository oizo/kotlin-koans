package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val first: MyDate, val last: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override val start: MyDate
        get() = first
    override val endInclusive: MyDate
        get() = last

    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current = start
        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): MyDate {
            val tmp = current
            current = tmp.nextDay()
            return tmp
        }

    }
}
