package com.nitin.codetime.internal

import java.io.IOException
import java.security.InvalidKeyException
import java.security.InvalidParameterException

class NoConnectivityException : IOException()
class NoIdPassedForDetailException : InvalidParameterException()
class InvalidContestTypeCodeException : InvalidKeyException()