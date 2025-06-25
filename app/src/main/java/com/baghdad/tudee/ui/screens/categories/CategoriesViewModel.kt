package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CategoriesViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : CategoriesInteractionListener,
    BaseViewModel<CategoriesUiState, CategoriesUiEffect>(initialState = CategoriesUiState()) {

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
            taskCountProvider = ::getTaskCount
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

    private suspend fun getTaskCount(id: Long): Int {
        return taskService.getTasksByCategory(id)
            .map { it.count() }
            .first()
    }

    override fun onAddCategory() = tryToExecute(
        function = ::addCategory,
        onSuccess = { onAddNewCategorySuccess() },
        onError = ::onAddNewCategoryError
    )

    private suspend fun addCategory() {
        categoryService.createCategory(
            Category(
                id = 0L,
                title = currentState.addCategorySheetState.categoryTitle,
                image = Category.Image.ByteArray(
                    currentState.addCategorySheetState.categoryImageByteArray ?: byteArrayOf()
                )
            )
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

    override fun onUpdateCategoryImage(byteArray: ByteArray) {
        updateState {
            it.copy(
                addCategorySheetState = it.addCategorySheetState.copy(
                    categoryImageByteArray = byteArray
                )
            )
        }
    }

    override fun onToggleAddCategorySheetVisibility() {
        updateState { state ->
            state.copy(
                addCategorySheetState = state.addCategorySheetState.copy(
                    isVisible = !state.addCategorySheetState.isVisible,
                    categoryTitle = "",
                    categoryImageByteArray = null
                )
            )
        }
    }

    override fun onCategoryClick(categoryId: Long) {
        emitNewEffect(
            CategoriesUiEffect.NavigateToCategoryTasks(categoryId)
        )
    }

    private fun onAddNewCategorySuccess() {
        showSnackbar(
            messageRes = R.string.added_category_successfully,
            isSuccess = true
        )
        onToggleAddCategorySheetVisibility()
    }

    private fun onAddNewCategoryError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_adding_category,
            isSuccess = false
        )
        onToggleAddCategorySheetVisibility()
    }
}