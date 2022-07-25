package com.ssafy.runwithme.datasource

import com.ssafy.runwithme.api.UserApi
import com.ssafy.runwithme.base.BaseResponse
import com.ssafy.runwithme.model.dto.UserDto
import com.ssafy.runwithme.model.response.JoinResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
){
    fun joinUser(token: String, userDto: UserDto): Flow<BaseResponse<JoinResponse>> = flow {
        emit(userApi.joinUser(token, userDto))
    }
}