package com.igdtuw.travelbuddy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TripViewModel : ViewModel() {
    private val _destinationName = MutableStateFlow("")
    val destinationName: StateFlow<String> = _destinationName

    private val _startDate = MutableStateFlow("")
    val startDate: StateFlow<String> = _startDate

    private val _numberOfTravelers = MutableStateFlow("")
    val numberOfTravelers: StateFlow<String> = _numberOfTravelers

    private val _numberOfDays = MutableStateFlow("")
    val numberOfDays: StateFlow<String> = _numberOfDays

    private val _ageGroup = MutableStateFlow("")
    val ageGroup: StateFlow<String> = _ageGroup

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    // In TripViewModel.kt
    private val _energyLevel = MutableStateFlow("")
    val energyLevel: StateFlow<String> = _energyLevel
    fun setEnergyLevel(level: String) {
        _energyLevel.value = level
    }

    private val _mood = MutableStateFlow("")
    val mood: StateFlow<String> = _mood
    fun setMood(currentMood: String) {
        _mood.value = currentMood
    }

    private val _ageGroupList = MutableStateFlow<List<String>>(emptyList())
    val ageGroupList: StateFlow<List<String>> = _ageGroupList
    fun setAgeGroups(groups: List<String>) {
        _ageGroupList.value = groups
    }
    private val _budget = MutableStateFlow("")
    val budget: StateFlow<String> = _budget

    // Function to update destination name
    fun setDestinationName(name: String) {
        _destinationName.value = name
    }

    // Function to update start date
    fun setStartDate(date: String) {
        _startDate.value = date
    }

    // Function to update number of travelers
    fun setNumberOfTravelers(count: String) {
        _numberOfTravelers.value = count
    }

    // Function to update number of days
    fun setNumberOfDays(days: String) {
        _numberOfDays.value = days
    }

    // Function to update age group
    fun setAgeGroup(group: String) {
        _ageGroup.value = group
    }

    // Function to update category
    fun setCategory(cat: String) {
        _category.value = cat
    }

    // Function to update budget
    fun setBudget(amount: String) {
        _budget.value = amount
    }

    // You can add more StateFlows and update functions for other trip details
    // as your application grows.

    // Example of a function to reset all trip details
    fun resetTripDetails() {
        _destinationName.value = ""
        _startDate.value = ""
        _numberOfTravelers.value = ""
        _numberOfDays.value = ""
        _ageGroup.value = ""
        _category.value = ""
        _budget.value = ""
    }
}