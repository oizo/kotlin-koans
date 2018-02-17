package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(time: TimeInterval): MyDate = when (time) {
    TimeInterval.DAY -> MyDate(this.year, this.month, this.dayOfMonth + 1)
    TimeInterval.WEEK -> MyDate(this.year, this.month, this.dayOfMonth + 7)
    TimeInterval.YEAR -> MyDate(this.year + 1, this.month, this.dayOfMonth)
}

operator fun MyDate.plus(time: RepeatedTimeInterval): MyDate = addTimeIntervals(time.ti, time.n)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(mult: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, mult)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

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
