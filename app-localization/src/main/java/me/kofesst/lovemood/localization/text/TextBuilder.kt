package me.kofesst.lovemood.localization.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/**
 * Тип форматирования текста.
 *
 * [ArgumentsBeforeText] - аргументы вставляются в начале текста.
 *
 * [ArgumentsAfterText] - аргументы вставляются в конце текста.
 *
 * [OnlyText] - при форматировании возвращается текст без аргументов.
 */
sealed interface TextBuilderFormat {
    /**
     * Форматирует текст [text] и аргументы [arguments].
     */
    fun buildString(
        text: String,
        arguments: List<Any>
    ): String

    fun buildAnnotatedString(
        text: String,
        arguments: List<Any>,
        textStyle: SpanStyle,
        argumentsStyle: SpanStyle
    ): AnnotatedString

    /**
     * Форматирование текста, при котором аргументы вставляются в начале текста.
     *
     * [argumentsSeparator] - разделитель между аргументами.
     */
    class ArgumentsBeforeText(
        val argumentsSeparator: String = " "
    ) : TextBuilderFormat {
        override fun buildString(text: String, arguments: List<Any>): String {
            return buildString {
                append(arguments.joinToString(argumentsSeparator))
                if (arguments.isNotEmpty()) append(argumentsSeparator)
                append(text)
            }
        }

        override fun buildAnnotatedString(
            text: String,
            arguments: List<Any>,
            textStyle: SpanStyle,
            argumentsStyle: SpanStyle
        ): AnnotatedString {
            return buildAnnotatedString {
                withStyle(argumentsStyle) {
                    append(arguments.joinToString(argumentsSeparator))
                    if (arguments.isNotEmpty()) append(argumentsSeparator)
                }
                withStyle(textStyle) {
                    append(text)
                }
            }
        }
    }

    /**
     * Форматирование текста, при котором аргументы вставляются в конце текста.
     *
     * [argumentsSeparator] - разделитель между аргументами.
     */
    class ArgumentsAfterText(
        val argumentsSeparator: String = " "
    ) : TextBuilderFormat {
        override fun buildString(text: String, arguments: List<Any>): String {
            return buildString {
                append(text)
                if (arguments.isNotEmpty()) append(argumentsSeparator)
                append(arguments.joinToString(argumentsSeparator))
            }
        }

        override fun buildAnnotatedString(
            text: String,
            arguments: List<Any>,
            textStyle: SpanStyle,
            argumentsStyle: SpanStyle
        ): AnnotatedString {
            return buildAnnotatedString {
                withStyle(textStyle) {
                    append(text)
                }
                withStyle(argumentsStyle) {
                    if (arguments.isNotEmpty()) append(argumentsSeparator)
                    append(arguments.joinToString(argumentsSeparator))
                }
            }
        }
    }

    /**
     * Форматирование текста, при котором показывается текст без аргументов.
     */
    data object OnlyText : TextBuilderFormat {
        override fun buildString(text: String, arguments: List<Any>): String {
            return text
        }

        override fun buildAnnotatedString(
            text: String,
            arguments: List<Any>,
            textStyle: SpanStyle,
            argumentsStyle: SpanStyle
        ): AnnotatedString {
            return buildAnnotatedString {
                withStyle(textStyle) {
                    append(text)
                }
            }
        }
    }
}

/**
 * Построитель текста.
 */
abstract class TextBuilder {
    /**
     * Плейсхолдеры текста.
     *
     * Представляет собой словарь плейсхолдера к его значению.
     *
     * Например:
     *
     * ```
     * {
     *     "$profile_name$" to "John Doe",
     *     "$age$" to 18
     * }
     * ```
     */
    var placeholders: MutableMap<String, Any> = mutableMapOf()

    /**
     * Тип форматирования текста.
     */
    open var format: TextBuilderFormat = TextBuilderFormat.OnlyText

    /**
     * Добавляет плейсхолдер.
     *
     * [name] - имя плейсхолдера.
     *
     * [value] - значение плейсхолдера.
     */
    fun addPlaceholder(name: String, value: Any) {
        addPlaceholder(name to value)
    }

    /**
     * Добавляет плейсхолдер.
     *
     * [placeholder] - пара имени и значения плейсхолдера.
     */
    fun addPlaceholder(placeholder: Pair<String, Any>) {
        placeholders += placeholder
    }

    /**
     * Подготавливает текст и аргументы для форматирования.
     *
     * Возвращает пару - текст и список аргументов.
     */
    protected abstract fun prepareText(): Pair<String, List<Any>>

    protected fun replacePlaceholders(string: String): String {
        return placeholders.entries.fold(initial = string) { folding, (name, value) ->
            folding.replace(name, value.toString())
        }
    }

    /**
     * Возвращает готовый текст со стилями.
     */
    open fun buildAnnotated(
        textStyle: SpanStyle = SpanStyle(),
        argumentsStyle: SpanStyle = SpanStyle()
    ): AnnotatedString {
        var (text, arguments) = prepareText()
        text = replacePlaceholders(text)
        return format.buildAnnotatedString(text, arguments, textStyle, argumentsStyle)
    }

    /**
     * Возвращает готовый текст.
     */
    open fun build(): String {
        var (text, arguments) = prepareText()
        text = replacePlaceholders(text)
        return format.buildString(text, arguments)
    }

    companion object {
        /**
         * Построитель текста, всегда возвращающий пустую строку.
         */
        val Empty = object : TextBuilder() {
            override fun prepareText(): Pair<String, List<Any>> {
                return "" to emptyList()
            }
        }

        /**
         * Возвращает построитель текста, всегда возвращающий константное значение [string].
         */
        fun fromString(string: String): TextBuilder {
            return object : TextBuilder() {
                override fun prepareText(): Pair<String, List<Any>> {
                    return string to emptyList()
                }
            }
        }
    }
}