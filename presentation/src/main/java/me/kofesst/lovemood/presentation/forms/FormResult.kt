package me.kofesst.lovemood.presentation.forms

class InvalidFormValuesException : Throwable()

sealed interface FormResult<Model : Any> {
    class Success<Model : Any>(val model: Model) : FormResult<Model>
    class Failed<Model : Any>(val cause: Throwable) : FormResult<Model>
}