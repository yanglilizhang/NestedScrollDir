package com.moj.nested

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author : moj
 * @date : 2019/8/9
 * @description :布局用behavior
 */
class ViewOffsetActivity : AppCompatActivity() {
   companion object{
       fun startToMe(context: Context){
           val intent = Intent(context, ViewOffsetActivity::class.java)
           context.startActivity(intent)
       }
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_offset)
    }
}