package me.kofesst.lovemood.presentation.forms

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

/**
 * Базовая вью модель для формы.
 *
 * [Model] - модель, создаваемая в форме.
 *
 * [Form] - состояние формы.
 *
 * [Action] - событие в форме.
 */
abstract class BaseFormViewModel<Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>>(
    /**
     * Начальное состояние формы
     */
    initialFormState: Form,

    /**
     * Валидатор формы.
     */
    private val validator: FormValidator<Model, Form>,

    /**
     * Событие отправки формы.
     */
    private val submitActionClass: Class<Action>
) : ViewModel() {
    private val _preparedState = mutableStateOf(false)

    /**
     * Состояние подготовки формы.
     */
    val preparedState: State<Boolean> = _preparedState

    private val _formMethod = mutableStateOf<FormMethod>(FormMethod.CreatingNewModel)

    /**
     * Метод формы.
     */
    val formMethod: State<FormMethod> = _formMethod

    private val _formState = MutableStateFlow(initialFormState)

    /**
     * Состояние формы.
     */
    val formState: StateFlow<Form> = _formState.asStateFlow()

    private val _resultsChannel = Channel<FormResult<Model>>()

    /**
     * Поток уведомлений о результатах формы.
     */
    val resultsFlow = _resultsChannel.receiveAsFlow()

    /**
     * Обрабатывает событие в форме.
     *
     * [action] - событие в форме.
     */
    fun handleFormAction(action: Action) {
        when {
            action::class.java == submitActionClass -> onSubmit()
            else -> {
                _formState.update { currentForm ->
                    action.applyToForm(currentForm)
                }
            }
        }
    }

    /**
     * Устанавливает готовность формы к использованию.
     */
    fun prepareForm() {
        _preparedState.value = true
    }

    /**
     * Изменяет метод формы на [method].
     */
    protected fun changeFormMethod(method: FormMethod) {
        _formMethod.value = method
    }

    /**
     * Вручную изменяет состояние формы.
     *
     * [block] - функция изменения формы.
     */
    protected fun manuallyEditForm(block: (Form) -> Form) {
        _formState.update(block)
    }

    /**
     * Работает с полученной из формы моделью [model].
     *
     * [method] - метод формы.
     *
     * Возвращает обработанную модель.
     */
    protected abstract suspend fun workWithModel(model: Model, method: FormMethod): Model

    /**
     * Обрабатывает отправку формы.
     *
     * По итогу отправляет в поток результатов [resultsFlow]
     * результат отправки формы [FormResult].
     */
    private fun onSubmit() {
        viewModelScope.launch {
            try {
                val validatedForm = _formState.updateAndGet(validator::validate)
                if (!validatedForm.isValid) throw InvalidFormValuesException()

                val model = workWithModel(validatedForm.asModel(), formMethod.value)
                Log.d("LoveMood", "Worked with model: $model")
                Log.d("LoveMood", "Sending success result to channel")
                _resultsChannel.send(
                    FormResult.Success(model)
                )
            } catch (throwable: Throwable) {
                _resultsChannel.send(
                    FormResult.Failed(throwable)
                )
            }
        }
    }
}