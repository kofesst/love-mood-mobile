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
     * Событие отправки формы.
     */
    private val submitAction: Action
) : ViewModel() {
    private val _preparedState = mutableStateOf(false)

    /**
     * Состояние подготовки формы.
     */
    val preparedState: State<Boolean> = _preparedState

    /**
     * Метод формы.
     */
    var formMethod = FormMethod.CreatingNewModel
        private set

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
        when (action) {
            submitAction -> onSubmit()
            else -> {
                _formState.update { currentForm ->
                    action.applyToForm(currentForm)
                }
            }
        }
    }

    /**
     * Загружает изменяемую модель по её ID.
     *
     * [modelId] - ID модели.
     *
     * Возвращает модель, найденную по ID (может быть равно null).
     */
    protected abstract suspend fun loadEditingModel(modelId: Int): Model?

    /**
     * Подготавливает форму, устанавливая метод
     * формы на [FormMethod.EditingOldModel], если ID модели
     * [editingModelId] не равен null, а также модель с данным ID
     * существует. Иначе метод формы будет равен [FormMethod.CreatingNewModel].
     *
     * [callback] - callback, вызываемый при загрузке редактируемой модели.
     */
    fun prepareForm(
        editingModelId: Int?,
        callback: (Model?) -> Unit = {}
    ) {
        viewModelScope.launch {
            val editingModel = editingModelId?.let { loadEditingModel(it) }
            prepareForm(editingModel)
            callback(editingModel)
        }
    }

    /**
     * Подготавливает форму, устанавливая метод
     * формы на [FormMethod.EditingOldModel], если модель [editingModel]
     * существует. Иначе метод формы будет равен [FormMethod.CreatingNewModel].
     */
    @Suppress("UNCHECKED_CAST")
    fun prepareForm(editingModel: Model?) {
        editingModel?.let {
            _formState.update { currentForm ->
                currentForm.fromModel(it) as Form
            }
            formMethod = FormMethod.EditingOldModel
        }
        _preparedState.value = true
    }

    /**
     * Проводит валидацию формы.
     *
     * [form] - текущее состояние формы.
     *
     * Возвращает новую форму с возможными ошибками валидации.
     */
    protected abstract fun validateForm(form: Form): Form

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
                val validatedForm = validateForm(formState.value)
                _formState.value = validateForm(validatedForm)

                if (!validatedForm.isValid) throw InvalidFormValuesException()

                val model = workWithModel(validatedForm.asModel(), formMethod)
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