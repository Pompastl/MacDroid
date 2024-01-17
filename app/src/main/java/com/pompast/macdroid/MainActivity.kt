package com.pompast.macdroid

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pompast.macdroid.connect.ConnectCommands
import com.pompast.macdroid.connect.client.ConnectClientCommunication
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        progressBar = findViewById(R.id.progress_bar)

        starClient()
    }

    fun onClickCopy(view: View) {
        val text = textView!!.text.toString()
        when (view.id) {
            R.id.button_copy -> {
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("label", text)
                clipboardManager.setPrimaryClip(clipData)

                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }

            R.id.button_open -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                startActivity(intent)
            }



        }

    }

    


    private fun starClient() {
        thread {
            try {
                val cc = ConnectClientCommunication()

                progressBar!!.visibility = View.VISIBLE
                while (true) {
                    try {
                        cc.open("192.168.43.216", 8080)
                        progressBar!!.visibility = View.INVISIBLE
                        break
                    } catch (_: Exception) { }

                }


                while (cc.isOpen()) {
                    var text = String(cc.get())

                    if (text.contains(ConnectCommands.CLOSE.name)) {
                        cc.closeMain()
                        text = text.replace(ConnectCommands.CLOSE.name, "")
                    }

                    runOnUiThread {
                        textView!!.text = text
                    }

                }

            } catch (e: Exception) {
                runOnUiThread {
                    textView!!.text = e.message
                }
            }
        }
    }


}