package com.getfly.technologies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.viewmodel.MainScreenViewModel

class MainViewModelFactory(private val repositoryOne: AcademateRepository,private val repositoryTwo: EaseBuzzRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(repositoryOne,repositoryTwo) as T
    }
}