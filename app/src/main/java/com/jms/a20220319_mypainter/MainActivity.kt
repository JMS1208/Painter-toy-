package com.jms.a20220319_mypainter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintSet
import com.jms.a20220319_mypainter.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    val LINE = 1
    val CIRCLE = 2
    var curShape = LINE
    lateinit var binding : ActivityMainBinding

    inner class MyGraphicView(context : Context) : View(context) {
        var startX = -1
        var startY = -1
        var stopX = -1
        var stopY = -1

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            val paint = Paint()
            paint.isAntiAlias = true
            paint.strokeWidth = 5f
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE

            when(curShape){
                LINE -> {
                    canvas?.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(),
                        stopY.toFloat(), paint
                    )

                }
                CIRCLE -> {
                    val radius = sqrt((stopX - startX).toDouble().pow(2.0)+(stopY-startY).toDouble().pow(2.0))
                    canvas?.drawCircle(startX.toFloat(),startY.toFloat(),radius.toFloat(),paint)
                }


            }
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            when(event?.action) {
                MotionEvent.ACTION_DOWN->{
                    startX = event.x.toInt()
                    startY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE,
                MotionEvent.ACTION_UP-> {
                    stopX = event.x.toInt()
                    stopY = event.y.toInt()
                    this.invalidate()

                }


                else -> return super.onTouchEvent(event)
            }

            return true

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(MyGraphicView(this))
        title="간단한 그림판"



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.drawLine_Item-> {
                curShape = LINE
                true
            }
            R.id.drawCircle_Item-> {
                curShape = CIRCLE
                true
            }

            else-> super.onOptionsItemSelected(item)
        }
    }



}