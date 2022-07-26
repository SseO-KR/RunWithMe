package com.ssafy.runwithme.view.running.result

import android.util.Log
import com.ssafy.runwithme.R
import com.ssafy.runwithme.base.BaseActivity
import com.ssafy.runwithme.databinding.ActivityRunningResultBinding
import com.ssafy.runwithme.utils.TAG
import com.ssafy.runwithme.view.create_recommend.CreateRecommendDialog
import com.ssafy.runwithme.view.running.RunningActivity
import com.ssafy.runwithme.view.running.result.achievement.AchievementDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunningResultActivity : BaseActivity<ActivityRunningResultBinding>(R.layout.activity_running_result) {

    override fun init() {
        initClickListener()

        initResult()

        // TEST
        AchievementDialog(this).show()
    }

    private fun initResult(){
        binding.apply {
            imgResult.setImageBitmap(RunningActivity.image)
            Log.d(TAG, "initResult: ${RunningActivity.runRecordEndTime}")
        }
    }

    private fun initClickListener(){
        binding.apply {
            btnOk.setOnClickListener {
                finish()
            }
            btnRecommend.setOnClickListener {
                CreateRecommendDialog(this@RunningResultActivity).show()
            }
        }
    }
}