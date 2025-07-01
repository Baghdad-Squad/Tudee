package com.baghdad.tudee.ui.screens.Onboarding

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.service.AppConfigurationService
import com.baghdad.tudee.ui.base.BaseViewModel

class OnboardingViewModel(
    private val appConfigurationService: AppConfigurationService
) : OnboardingInteractionListener,
    BaseViewModel<OnboardingState, OnboardingScreenEffect>(OnboardingState()) {

    private fun completeOnboarding() {
        tryToExecute(
            function = { 
                appConfigurationService.setTheme(false)
                appConfigurationService.setOnboardingCompleted()
            },
            onSuccess = { emitNewEffect(OnboardingScreenEffect.NavigateToHome) },
            onError = ::onboardingError
        )
    }

    override fun onNextButtonClick() {
        val currentPage = currentState.currentPage
        if (currentPage < 2) {
            updateState { it.copy(currentPage = currentPage + 1) }
        } else {
            completeOnboarding()
        }
    }

    override fun onSkipButtonClick() {
        completeOnboarding()
    }

    private fun onboardingError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_saving_onboarding,
            isSuccess = false
        )
    }
}