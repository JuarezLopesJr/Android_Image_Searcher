package com.example.getimages

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

interface OnRecyclerClickListener {
    fun onItemClick(view: View, position: Int)
    fun onItemLongClick(view: View, position: Int)
}

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val listener: OnRecyclerClickListener
) : RecyclerView.SimpleOnItemTouchListener() {


    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
//            return super.onSingleTapUp(e)
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            listener.onItemClick(childView!!, recyclerView.getChildAdapterPosition(childView))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            listener.onItemLongClick(childView!!, recyclerView.getChildAdapterPosition(childView))

        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val result = gestureDetector.onTouchEvent(e)
        return result
//        return super.onInterceptTouchEvent(rv, e)
    }
}