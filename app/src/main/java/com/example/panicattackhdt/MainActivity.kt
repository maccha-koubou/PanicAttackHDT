package com.example.panicattackhdt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.panicattackhdt.model.RecordData
import com.example.panicattackhdt.storage.DataStorage
import com.example.panicattackhdt.storage.copyAssetsToInternal
import com.example.panicattackhdt.storage.loadRecords
import com.example.panicattackhdt.view.MainPage
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyAssetsToInternal(this, "HistoryData.json")
        DataStorage.init(this)
        enableEdgeToEdge()
        setContent {
            MainPage()
        }
    }
}
