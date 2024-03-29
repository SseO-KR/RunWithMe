package com.ssafy.runwithme.view.running

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.runwithme.model.dto.AchievementDto
import com.ssafy.runwithme.model.dto.CoordinateDto
import com.ssafy.runwithme.model.dto.RunRecordDto
import com.ssafy.runwithme.model.response.MyCurrentCrewResponse
import com.ssafy.runwithme.repository.CrewManagerRepository
import com.ssafy.runwithme.repository.CrewRepository
import com.ssafy.runwithme.repository.MyActivityRepository
import com.ssafy.runwithme.utils.Result
import com.ssafy.runwithme.utils.SingleLiveEvent
import com.ssafy.runwithme.utils.USER_NAME
import com.ssafy.runwithme.utils.USER_WEIGHT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RunningViewModel @Inject constructor(
    private val crewRepository: CrewRepository,
    private val crewManagerRepository: CrewManagerRepository,
    private val myActivityRepository: MyActivityRepository,
    private val sharedPreferences: SharedPreferences
    ): ViewModel(){

    private val _runRecordSeq = MutableStateFlow(0)
    val runRecordSeq get() = _runRecordSeq.asStateFlow()

    private val _runningCrewList: MutableStateFlow<List<MyCurrentCrewResponse>>
            = MutableStateFlow(listOf())
    val runningCrewList get() = _runningCrewList.asStateFlow()

    private val _achievementsList: MutableStateFlow<List<AchievementDto>>
            = MutableStateFlow(listOf())
    val achievementsList get() = _achievementsList.asStateFlow()

    private val _errorMsgEvent = SingleLiveEvent<String>()
    val errorMsgEvent get() = _errorMsgEvent

    private val _runAbleEvent = SingleLiveEvent<String>()
    val runAbleEvent get() = _runAbleEvent

    private val _coordinateSuccess = SingleLiveEvent<String>()
    val coordinateSuccess get() = _coordinateSuccess

    private val _getCoordinates : MutableStateFlow<List<CoordinateDto>> = MutableStateFlow(listOf())
    val getCoordinates get() = _getCoordinates

    private val _nickName = MutableStateFlow("")
    val nickname get() = _nickName.asStateFlow()

    fun createRunRecord(crewId: Int, imgFile: MultipartBody.Part, runRecordDto: RunRecordDto){
        val json = Gson().toJson(runRecordDto)
        val run = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        viewModelScope.launch(Dispatchers.IO) {
            crewRepository.createRunRecords(crewId, imgFile, run).collectLatest {
                Log.d("test5", "createRunRecord: $it")

                if(it is Result.Success){
                    _runRecordSeq.value = it.data.data.runRecordDto.runRecordSeq
                    _coordinateSuccess.postValue("경로 전송 요청")
                    // 업적 달성 여부
                    if(it.data.data.achievements.isNotEmpty()){
                        _achievementsList.value = it.data.data.achievements
                    }
                }
            }
        }
    }

    fun createCoordinates(recordSeq: Int, coordinates: List<CoordinateDto>){
        viewModelScope.launch(Dispatchers.IO) {
            crewRepository.createCoordinates(recordSeq, coordinates).collectLatest {
                Log.d("test5", "createCoordinates: $it")
                if(it is Result.Success){
                }
            }
        }
    }

    fun runAbleToday(crewSeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            myActivityRepository.runAbleToday(crewSeq).collectLatest {
                Log.d("test5", "runAbleToday: $it")
                if(it is Result.Success){
                    if(it.data.data) {
                        _runAbleEvent.postValue("러닝 가능")
                    }
                    // TODO : TEST (빼야함)
                    else{
                        _errorMsgEvent.postValue(it.data.msg)
                    }
                } else if(it is Result.Fail){
                    _errorMsgEvent.postValue(it.data.msg)
                }
            }
        }
    }

    fun getMyProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            myActivityRepository.getMyProfile().collectLatest {
                if(it is Result.Success){
                    sharedPreferences.edit().putInt(USER_WEIGHT,it.data.data.userDto.weight).apply()
                    sharedPreferences.edit().putString(USER_NAME, it.data.data.userDto.nickName).apply()
                    _nickName.value = it.data.data.userDto.nickName
                } else if(it is Result.Error){

                }
            }
        }
    }


    fun getMyCurrentCrew(){
        viewModelScope.launch(Dispatchers.IO) {
            crewManagerRepository.getMyCurrentCrew().collectLatest{
                if(it is Result.Success){
                    val tmpList = arrayListOf<MyCurrentCrewResponse>()
                    for(i in it.data.data) {
                        val today = Calendar.getInstance()
                        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                        var startDate = sf.parse(i.crewDto.crewDateStart)
                        var endDate = sf.parse(i.crewDto.crewDateEnd)


                        val sfTime = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

                        var startTime = sfTime.parse(i.crewDto.crewTimeStart)
                        var endTime = sfTime.parse(i.crewDto.crewTimeEnd)

                        val now = System.currentTimeMillis()
                        val date = Date(now)
                        val nowString = sfTime.format(date)
                        val nowTime = sfTime.parse(nowString)

                        val startCalendar = Calendar.getInstance()
                        startCalendar.time = startTime

                        val endCalendar = Calendar.getInstance()
                        endCalendar.time = endTime

                        if (today.time.time - startDate.time > 0) {
                            if (today.time.time - endDate.time > 0) {
                                Log.d("test5", "getMyCurrentCrew_end: $i")
                            } else {
                                // 뛸 수 있는 크루
                                if (nowTime.time >= startTime.time && nowTime.time <= endTime.time) {
                                    Log.d("test5", "getMyCurrentCrew_start: $i")
                                    tmpList.add(i)
                                } else {
                                    Log.d("test5", "getMyCurrentCrew_end: $i")
                                }
                            }
                        } else {
                            Log.d("test5", "getMyCurrentCrew_await: $i")
                        }
                    }
                    _runningCrewList.value = tmpList
                }else if(it is Result.Error){
                    _errorMsgEvent.postValue("내 크루 불러오기 중 오류가 발생했습니다.")
                }
            }
        }
    }

}