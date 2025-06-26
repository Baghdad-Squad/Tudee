package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.base.BaseViewModel
import com.baghdad.tudee.ui.model.toUiStates
import com.baghdad.tudee.ui.shared.components.category.toEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CategoriesViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : CategoriesInteractionListener,
    BaseViewModel<CategoriesScreenState, CategoriesScreenEffect>(initialState = CategoriesScreenState()) {

    init {
        getCategories()
    }

    fun getCategories() = tryToCollect(
        function = categoryService::getCategories,
        onNewValue = ::onNewCategoriesValue,
        onError = ::onGetCategoriesError
    )

    private suspend fun onNewCategoriesValue(newCategories: List<Category>) {
        val categories = newCategories.toUiStates(
            taskCountProvider = ::getTaskCountForCategory
        )
        updateState { state ->
            state.copy(
                categories = categories
            )
        }
    }

    private fun onGetCategoriesError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_fetching_categories,
            isSuccess = false
        )
    }

    private suspend fun getTaskCountForCategory(categoryId: Long): Int {
        return taskService.getTasksByCategory(categoryId)
            .map { it.count() }
            .first()
    }

    override fun onAddCategory() {
        tryToExecute<Unit>(
            function = { categoryService.createCategory(currentState.addCategorySheetState.toEntity()) },
            onSuccess = { onAddNewCategorySuccess() },
            onError = ::onAddNewCategoryError
        )
    }

    private fun onAddNewCategorySuccess() {
        onDismissAddCategorySheet()
        showSnackbar(
            messageRes = R.string.added_category_successfully,
            isSuccess = true
        )
    }

    private fun onAddNewCategoryError(error: Throwable) {
        onDismissAddCategorySheet()
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_adding_category,
            isSuccess = false
        )
    }

    override fun onUpdateCategoryTitle(newTitle: String) {
        updateState {
            it.copy(
                addCategorySheetState = it.addCategorySheetState.copy(
                    categoryTitle = newTitle
                )
            )
        }
    }

    override fun onUpdateCategoryImageByteArray(categoryImageByteArray: ByteArray) {
        updateState {
            it.copy(
                addCategorySheetState = it.addCategorySheetState.copy(
                    categoryImageByteArray = categoryImageByteArray
                )
            )
        }
    }

    override fun onAddCategoryClicked() {
        updateState { state ->
            state.copy(
                addCategorySheetState = state.addCategorySheetState.copy(
                    isVisible = true,
                    categoryTitle = "",
                    categoryImageByteArray = null
                )
            )
        }
    }

    override fun onDismissAddCategorySheet() {
        updateState { state ->
            state.copy(
                addCategorySheetState = state.addCategorySheetState.copy(
                    isVisible = false
                )
            )
        }
    }

    override fun onCategoryClicked(categoryId: Long?) {
        categoryId?.let {
            emitNewEffect(
                CategoriesScreenEffect.NavigateToCategoryTasks(categoryId)
            )
        }
    }
}