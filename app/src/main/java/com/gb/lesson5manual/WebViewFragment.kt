package com.gb.lesson5manual

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gb.lesson5manual.databinding.FragmentWebViewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ok.setOnClickListener(clickListener)
    }

    private var clickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            var urlConnection: HttpsURLConnection? = null
            Thread(){
                try {
                    val uri = URL(binding.uri.text.toString())

                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection!!.requestMethod = "GET"
                    urlConnection!!.readTimeout = 1000
                    val reader = BufferedReader(InputStreamReader(urlConnection!!.getInputStream()))
                    val result = getLines(reader)
                    binding.webView.loadData(result, "text/html;charset-utf-8", "utf-8")
                } catch (e: Exception) {
                    Log.e("( ╯°□°)╯ ┻━━┻", "Дебошь", e)
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        }

        private fun getLines(reader: BufferedReader) = reader.lines().collect(Collectors.joining("\n"))

    }

    companion object {
        @JvmStatic
        fun newInstance() = WebViewFragment()
    }
}