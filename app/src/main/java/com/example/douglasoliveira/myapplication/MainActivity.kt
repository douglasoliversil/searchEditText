package com.example.douglasoliveira.myapplication

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import com.example.douglasoliveira.myapplication.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.Normalizer
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private var list = mutableListOf("Teste 1", "Teste 2", "Brasil", "Argentina", "B.3", "Açaí", "ç", "é")
    private var listCache = mutableListOf("Teste 1", "Teste 2", "Brasil", "Argentina", "B.3", "Açaí", "ç", "é")
    var closeIsVisible: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerItems.adapter = MyAdapter(this, list)
        recyclerItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        initActions()
    }

    private fun initActions() {
        search.addTextChangedListener(
                object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {
                        return
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        resetList()
                        return
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        var iterator = list.iterator()
                        while (iterator.hasNext()) {

                            val item = iterator.next()
                            val normalizedItem = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                                    .matcher(Normalizer.normalize(item, Normalizer.Form.NFD))
                                    .replaceAll("")

                            if (!item.toLowerCase().contains(p0!!.toString().toLowerCase())
                                    && !normalizedItem.toLowerCase().contains(p0!!.toString().toLowerCase()))
                                iterator.remove()
                        }
                        recyclerItems.adapter?.notifyDataSetChanged()
                        changeIcon(p0?.isEmpty())
                    }

                }
        )

        search.setOnTouchListener { view, motionEvent ->
            doSomething(motionEvent)
        }

    }

    fun resetList() {
        list.clear()
        list.addAll(listCache)
    }

    private fun doSomething(motionEvent: MotionEvent?): Boolean {
        if (closeIsVisible && motionEvent!!.rawX >= (search.right - search.compoundDrawables[2].bounds.width())) {
            resetList()
            search.text.clear()
            recyclerItems.adapter!!.notifyDataSetChanged()
        }
        return true
    }

    private fun changeIcon(empty: Boolean?) {
        when (empty) {
            true -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                search.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_loupe, 0)
                closeIsVisible = false
            }
            false -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                search.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
                closeIsVisible = true
            }
        }
    }


}
