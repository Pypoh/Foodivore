package com.example.foodivore.ui.main.article

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodivore.repository.model.Article

private val TAB_TITLES = arrayOf(
    "Kesehatan",
    "Penyakit",
    "Tips & Trick"
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val data: List<Article.Category?>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        //        when (position) {
//            0 -> {
//                fragment = ArticleFragment.newInstance(data[position]!!.name)
//            }
//            else -> {
//                PlaceholderFragment.newInstance(position + 1)
//            }
//        }
//        if (data[position]!!.name == "System") {
//
//        }
        return ArticleFragment.newInstance(data[position]!!.name)

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return data[position]!!.name
    }

    override fun getCount(): Int {
        return data.size
    }
}