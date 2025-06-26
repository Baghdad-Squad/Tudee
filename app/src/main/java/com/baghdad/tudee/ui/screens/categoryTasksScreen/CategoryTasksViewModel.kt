package com.baghdad.tudee.ui.screens.categoryTasksScreen

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.base.BaseViewModel

class CategoryTasksViewModel(
    private val categoryId: Long,
    val taskService: TaskService,
    val categoryService: CategoryService,
) : BaseViewModel<CategoryTasksScreenUiState, CategoryTasksScreenEffect>(
    CategoryTasksScreenUiState()
), CategoriesTasksInteractionListener {

    init {
        getCategoryById()
        getTasksByCategoryId()
    }

    override fun onTabSelected(tab: Task.State) {
        updateState {
            it.copy(selectedTab = tab)
        }
    }

    private fun getCategoryById() {
        tryToExecute(
            function = { categoryService.getCategoryById(categoryId) },
            onSuccess = { category ->
                category?.let {
                    onGetCategoryByIdSuccess(it)
                }
            },
            onError = { onGetCategoryByIdError() }
        )
    }

    private fun onGetCategoryByIdSuccess(category: Category) {
        updateState {
            it.copy(
                category = category,
                addEditCategorySheetState = it.addEditCategorySheetState.copy(
                    categoryTitle = category.title,
                    categoryImageByteArray = (category.image as? Category.Image.ByteArray)?.data
                )
            )
        }
    }

    private fun onGetCategoryByIdError() {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_fetching_the_category,
            isSuccess = false
        )
    }

    private fun getTasksByCategoryId() {
        tryToCollect(
            function = { taskService.getTasksByCategory(categoryId) },
            onNewValue = { tasks -> onGetTasksByCategoryIdSuccess(tasks) },
            onError = { onGetTasksByCategoryIdError() }
        )
    }

    private fun onGetTasksByCategoryIdSuccess(tasks: List<Task>) {
        updateState {
            it.copy(
                todoTasks = tasks.filter { task -> task.state == Task.State.TODO },
                inProgressTasks = tasks.filter { task -> task.state == Task.State.IN_PROGRESS },
                doneTasks = tasks.filter { task -> task.state == Task.State.DONE }
            )
        }
    }

    private fun onGetTasksByCategoryIdError() {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_fetching_the_tasks,
            isSuccess = false,
        )

    }


    override fun onCategoryTitleChanged(newTitle: String) {
        updateState {
            it.copy(
                addEditCategorySheetState = it.addEditCategorySheetState.copy(
                    categoryTitle = newTitle
                )
            )
        }
    }


    override fun onDeleteCategory() {
        tryToExecute(
            function = { categoryService.deleteCategory(categoryId) },
            onSuccess = { onDeleteCategorySuccess() },
            onError = { onDeleteCategoryError() }
        )
    }

    private fun onDeleteCategorySuccess() {
        showSnackbar(
            messageRes = R.string.category_deleted_successfully,
            isSuccess = true,
        )
        emitNewEffect(CategoryTasksScreenEffect.NavigateToCategoriesScreen)
    }

    private fun onDeleteCategoryError() {
        showSnackbar(
            messageRes = R.string.an_error_occured_while_deleting_the_category,
            isSuccess = false,
        )
    }


    override fun onSaveCategoryChanges() {
        tryToExecute(
            function = { updateCategoryFromSheetState() },
            onSuccess = { onSaveCategoryChangesSuccess() },
            onError = { onSaveCategoryChangesError() }
        )
    }

    private suspend fun updateCategoryFromSheetState(): Unit = categoryService.editCategory(
        Category(
            id = categoryId,
            title = currentState.addEditCategorySheetState.categoryTitle,
            image = Category.Image.ByteArray(
                currentState.addEditCategorySheetState.categoryImageByteArray ?: byteArrayOf()
            ),
        )
    )

    private fun onSaveCategoryChangesSuccess() {
        showSnackbar(
            messageRes = R.string.category_updated_successfully,
            isSuccess = true
        )
        getCategoryById()
        onToggleEditCategorySheetVisibility()
        emitNewEffect(CategoryTasksScreenEffect.NavigateToCategoriesScreen)
    }

    private fun onSaveCategoryChangesError() {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_updating_the_category,
            isSuccess = false
        )
    }



    override fun onChangeImage(newImage: Category.Image) {
        updateState {
            it.copy(
                addEditCategorySheetState = it.addEditCategorySheetState.copy(
                    categoryImageByteArray = (newImage as? Category.Image.ByteArray)?.data
                )
            )
        }
    }

    override fun onToggleEditCategorySheetVisibility() {
        updateState {
            it.copy(
                addEditCategorySheetState = it.addEditCategorySheetState.copy(
                    isVisible = !it.addEditCategorySheetState.isVisible
                )
            )
        }
    }

    override fun toggleDeleteCategorySheet() {
        updateState {
            it.copy(
                showDeleteCategorySheet = !it.showDeleteCategorySheet
            )
        }
    }

}