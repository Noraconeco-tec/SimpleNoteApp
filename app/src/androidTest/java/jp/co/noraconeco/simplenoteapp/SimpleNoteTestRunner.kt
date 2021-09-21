package jp.co.noraconeco.simplenoteapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class SimpleNoteTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, SimpleNoteApplication::class.java.name, context)
    }
}