package com.baghdad.tudee.viewModel.homescreenViewModel

import com.baghdad.tudee.domain.entity.Task


interface HomeScreenInteraction {

    fun onClickSwitchTheme()
    fun showTaskDetailsDialog()
    fun showAddTaskDialog()
    fun onClickAddNewTask(task: Task)
    fun togileEditTaskDialog()
    fun onClickEditTask(task: Task)
    fun moveTaskToDone(taskId: Long)
    fun moveTaskToTodo(taskId: Long)
    fun moveTaskToInProgress(taskId: Long)
    fun showSnarkMessage(message: String, isVisible: Boolean, isError: Boolean)
    fun getTaskDetailsById(id: Long)

}

