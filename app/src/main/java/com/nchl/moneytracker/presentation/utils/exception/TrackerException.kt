package com.nchl.moneytracker.presentation.utils.exception

class TrackerException( val trackerError: TrackerError) :
    RuntimeException(trackerError.errorMessage)