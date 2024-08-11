package me.kofesst.lovemood.core.text

/**
 * Интерфейс хранилища текста, который может изменяться в зависимости
 * от каких-либо аргументов.
 */
interface AppTextHolder {
    companion object {
        /**
         * Пустое хранилище текста.
         */
        val empty: AppTextHolder
            get() = raw("")

        /**
         * Возвращает хранилище текста из обычной строки [text].
         */
        fun raw(text: String) = object : AppTextHolder {
            override fun string(vararg args: Any): String = text
        }
    }

    /**
     * Возвращает строку для использования в приложении.
     *
     * [args] - список аргументов (например, плейсхолдеров).
     */
    fun string(vararg args: Any): String
}