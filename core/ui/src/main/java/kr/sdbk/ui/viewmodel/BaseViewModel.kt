package kr.sdbk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: State, I: Intent, E: Effect>(
    initialState: S
) : ViewModel() {
    private var isInitialized: Boolean = false

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true
                initializeData()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = initialState
        )

    private val _effect: Channel<E> = Channel(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    protected open fun initializeData() {}
    abstract fun handleIntent(intent: I)

    protected fun updateState(update: (S) -> S) = _state.update(update)
    protected fun sendEffect(effect: E) = viewModelScope.launch { _effect.send(effect) }
}