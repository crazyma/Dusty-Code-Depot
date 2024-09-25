package batu.is.awesome

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * A [CoordinatorLayout.Behavior] implementation that makes a bottom bar aware of vertical 
 * scrolling.This behavior allows the bottom bar to hide when scrolling down and reappear when 
 * scrolling up.
 */
class ScrollAwareBottomBarBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var enable: Boolean = true
    private var totalDyConsumed: Int = 0
    private var child: View? = null

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
        return enable && axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {
        if (enable) {
            totalDyConsumed = (totalDyConsumed + dyConsumed).coerceIn(0, child.height)
            child.translationY = totalDyConsumed.toFloat()
            this.child = child
        }
    }

    fun awareOfScrolling(enable: Boolean) {
        this.enable = enable
        if (!enable) reset()
    }

    private fun reset() {
        totalDyConsumed = 0
        child?.translationY = 0f
    }
}