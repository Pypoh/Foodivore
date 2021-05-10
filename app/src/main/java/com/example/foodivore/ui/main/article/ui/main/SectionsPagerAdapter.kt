package com.example.foodivore.ui.main.article.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val TAB_TITLES = arrayOf(
    "Kesehatan",
    "Penyakit",
    "Tips & Trick"
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = PlaceholderFragment.newInstance(position + 1)
        when (position) {
            0 -> {
                fragment = ArticleFragment()
            }
            else -> {
                PlaceholderFragment.newInstance(position + 1)
            }
        }
        return fragment!!

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}