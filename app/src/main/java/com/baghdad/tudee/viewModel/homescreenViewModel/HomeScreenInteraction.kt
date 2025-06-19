package com.baghdad.tudee.viewModel.homescreenViewModel


interface HomeScreenInteraction {

    fun onClickAddNewTask()
    fun onClickTask(taskId: Long)
    fun onClickSwitchTheme()
    fun onClickEditTask(taskId: Long)
    fun showTaskDetails(taskId: Long)
    fun moveTaskToDone(taskId: Long)
    fun moveTaskToTodo(taskId: Long)
    fun moveTaskToInProgress(taskId: Long)
    fun showSnarkMessage(message: String, isVisible: Boolean, isError: Boolean)

}

