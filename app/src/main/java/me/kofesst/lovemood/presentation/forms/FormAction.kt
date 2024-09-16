package me.kofesst.lovemood.presentation.forms

interface FormAction<Model : Any, Form : FormState<Model>> {
    fun applyToForm(currentForm: Form): Form {
        return currentForm
    }
}