package jp.co.noraconeco.simplenoteapp.ui

import androidx.annotation.LayoutRes

interface CellViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
}