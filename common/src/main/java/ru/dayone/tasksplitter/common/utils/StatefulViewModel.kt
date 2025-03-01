package ru.dayone.tasksplitter.common.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class StatefulViewModel<S : Any, E : Any, A : Any>(
    initialState: S,
    private val stateMachine: BaseStateMachine<E, S, A>
) : ViewModel() {
    private val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            stateMachine.effect.collect{
                _effect.emit(it)
            }
        }

        viewModelScope.launch {
            stateMachine.state.collect{
                _state.value = it
            }
        }
    }

    open fun handleAction(action: A) {
        viewModelScope.launch {
            stateMachine.dispatch(action)
        }
    }

    fun changeState(state: S){
        _state.value = state
    }

    fun changeEffect(effect: E){
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}