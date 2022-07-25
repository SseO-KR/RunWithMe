package com.ssafy.runwithme.repository

import android.util.Log
import com.ssafy.runwithme.base.BaseResponse
import com.ssafy.runwithme.datasource.UserRemoteDataSource
import com.ssafy.runwithme.model.dto.UserDto
import com.ssafy.runwithme.model.response.JoinResponse
import com.ssafy.runwithme.utils.Result
import com.ssafy.runwithme.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
){

    fun joinUser(token: String, userDto: UserDto): Flow<Result<BaseResponse<JoinResponse>>> = flow {
        emit(Result.Loading)
        userRemoteDataSource.joinUser(token, userDto).collect {
            Log.d(TAG, "joinUser: $it")
            if(it.isSuccess){
                emit(Result.Success(it))
            }else{
                emit(Result.Empty)
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}