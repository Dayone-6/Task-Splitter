package ru.dayone.tasksplitter.common.utils

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


@OptIn(ExperimentalCoroutinesApi::class)
open class BaseStateMachine<E : Any, S : Any, A : Any>(initialState: S) :
    FlowReduxStateMachine<S, A>(initialState) {
    private val _effect: MutableSharedFlow<E> = MutableSharedFlow()
    private val effect: SharedFlow<E> = _effect.asSharedFlow()

    protected suspend fun updateEffect(newEffect: E){
        _effect.emit(newEffect)
    }
}