package me.kofesst.lovemood.localization.text

object LoveDurationFacts {
    val All: List<LoveDurationFact>
        get() = listOf(
            Heartbeats,
            OxygenLiters,
            StarsInGalaxy,
            HumanBirth,
            Marriage
        )

    object Heartbeats : LoveDurationFact {
        const val HEARTBEAT_PER_MINUTE: Long = 80

        override fun applyFormula(loveDurationMillis: Long): Long {
            val heartbeatsPerMilli = HEARTBEAT_PER_MINUTE.toDouble() / 60 / 1000
            return heartbeatsPerMilli.toLong() * loveDurationMillis
        }
    }

    object OxygenLiters : LoveDurationFact {
        const val LITERS_INHALING_PER_HOUR: Long = 12_000

        override fun applyFormula(loveDurationMillis: Long): Long {
            val litersPerMilli = LITERS_INHALING_PER_HOUR.toDouble() / 60 / 60 / 1000
            return litersPerMilli.toLong() * loveDurationMillis
        }
    }

    object StarsInGalaxy : LoveDurationFact {
        const val STARS_PER_YEAR: Long = 7

        override fun applyFormula(loveDurationMillis: Long): Long {
            val starsPerMilli = STARS_PER_YEAR.toDouble() / 365 / 24 / 60 / 60 / 1000
            return starsPerMilli.toLong() * loveDurationMillis
        }
    }

    object HumanBirth : LoveDurationFact {
        const val BORN_PER_DAY: Long = 450_000

        override fun applyFormula(loveDurationMillis: Long): Long {
            val bornPerMilli = BORN_PER_DAY.toDouble() / 60 / 60 / 1000
            return bornPerMilli.toLong() * loveDurationMillis
        }
    }

    object Marriage : LoveDurationFact {
        const val MARRIAGE_PER_YEAR: Long = 1_000_000

        override fun applyFormula(loveDurationMillis: Long): Long {
            val marriagePerMilli = MARRIAGE_PER_YEAR.toDouble() / 365 / 24 / 60 / 60 / 1000
            return marriagePerMilli.toLong() * loveDurationMillis
        }
    }
}

interface LoveDurationFact {
    fun applyFormula(loveDurationMillis: Long): Long
}

class LoveDurationFactBuilder(
    private val leftPartBuilder: TextBuilder,
    private val fact: LoveDurationFact,
    private val factPartProducer: (value: Long) -> TextBuilder
) : TextBuilder() {
    override var format: TextBuilderFormat
        get() = TextBuilderFormat.ArgumentsAfterText()
        set(_) = Unit

    var loveDurationMillis: Long = 0L

    override fun prepareText(): Pair<String, List<Any>> {
        val leftPart = leftPartBuilder.build()
        val value = fact.applyFormula(loveDurationMillis)
        val factText = factPartProducer(value).build()
        return leftPart to listOf(factText)
    }
}