package com.example.foodivore.ui.pretest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityPreTestBinding
import com.example.foodivore.repository.model.Post
import com.example.foodivore.ui.pretest.adapter.ArticleAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class PreTestActivity : AppCompatActivity() {

    private lateinit var preTestBinding: ActivityPreTestBinding

    private var pageState: Int = 0

    private lateinit var titleToolbar: MaterialTextView
    private lateinit var backArrowToolbar: ImageView

    private lateinit var nameLayout: RelativeLayout
    private lateinit var heightLayout: RelativeLayout
    private lateinit var weightLayout: RelativeLayout
    private lateinit var sexLayout: RelativeLayout
    private lateinit var ageLayout: RelativeLayout
    private lateinit var activeLayout: RelativeLayout
    private lateinit var purposeLayout: RelativeLayout

    private lateinit var nameButton: MaterialButton
    private lateinit var heightButton: MaterialButton
    private lateinit var weightButton: MaterialButton
    private lateinit var sexButton: MaterialButton
    private lateinit var ageButton: MaterialButton
    private lateinit var activeButton: MaterialButton
    private lateinit var purposeButton: MaterialButton

    private lateinit var layoutArray: ArrayList<RelativeLayout>

    private lateinit var recyclerActive: RecyclerView
    private lateinit var adapterActive: ArticleAdapter
    private lateinit var recyclerPurpose: RecyclerView
    private lateinit var adapterPurpose: ArticleAdapter

    private var dummyActiveData: ArrayList<Post.Article> = arrayListOf()
    private var dummyPurposeData: ArrayList<Post.Article> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_test)

        preTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_pre_test)

        setupViews(preTestBinding.root)

        initPreTest(pageState)

        setupRecycler(preTestBinding.root)


    }

    private fun setupRecycler(view: View) {
        dummyActiveData.add(Post.Article("test title 1", "test subbitle 1"))
        dummyActiveData.add(Post.Article("test title 2", "test subbitle 2"))
        dummyActiveData.add(Post.Article("test title 3", "test subbitle 3"))

        dummyPurposeData.add(Post.Article("test title 1", "test subbitle 1"))
        dummyPurposeData.add(Post.Article("test title 2", "test subbitle 2"))
        dummyPurposeData.add(Post.Article("test title 3", "test subbitle 3"))

        recyclerActive = view.findViewById(R.id.recycler_active_pre_test)
        recyclerActive.layoutManager = LinearLayoutManager(this)
        adapterActive = ArticleAdapter(this, dummyActiveData)
        recyclerActive.adapter = adapterActive

        recyclerPurpose = view.findViewById(R.id.recycler_purpose_pre_test)
        recyclerPurpose.layoutManager = LinearLayoutManager(this)
        adapterPurpose = ArticleAdapter(this, dummyPurposeData)
        recyclerPurpose.adapter = adapterPurpose
    }

    @SuppressLint("SetTextI18n")
    private fun initPreTest(state: Int) {
        layoutArray[state].visibility = View.VISIBLE
        titleToolbar.text = "Langkah ${pageState + 1} dari 7"
        Log.d("PreTest", "State: $pageState")

    }

    private fun nextPreTest(state: Int) {
       if (pageState < 6) {
           layoutArray[pageState++].visibility = View.GONE
           layoutArray[pageState].visibility = View.VISIBLE
           titleToolbar.text = "Langkah ${pageState + 1} dari 7"
           Log.d("PreTest", "State: $pageState")
       }
    }

    private fun rollbackPreTest() {
        if (pageState > 0) {
            layoutArray[pageState--].visibility = View.GONE
            layoutArray[pageState].visibility = View.VISIBLE
            titleToolbar.text = "Langkah ${pageState + 1} dari 7"
        }

    }

    private fun setupViews(view: View) {
        val clickListener = View.OnClickListener { view ->
            nextPreTest(pageState)
        }

        nameLayout = view.findViewById(R.id.layout_name_pre_test)
        heightLayout = view.findViewById(R.id.layout_height_pre_test)
        weightLayout = view.findViewById(R.id.layout_weight_pre_test)
        sexLayout = view.findViewById(R.id.layout_sex_pre_test)
        ageLayout = view.findViewById(R.id.layout_age_pre_test)
        activeLayout = view.findViewById(R.id.layout_active_pre_test)
        purposeLayout = view.findViewById(R.id.layout_purpose_pre_test)

        nameButton = view.findViewById(R.id.button_name_pre_test)
        nameButton.setOnClickListener(clickListener)
        heightButton = view.findViewById(R.id.button_height_pre_test)
        heightButton.setOnClickListener(clickListener)
        weightButton = view.findViewById(R.id.button_weight_pre_test)
        weightButton.setOnClickListener(clickListener)
        sexButton = view.findViewById(R.id.button_sex_pre_test)
        sexButton.setOnClickListener(clickListener)
        ageButton = view.findViewById(R.id.button_age_pre_test)
        ageButton.setOnClickListener(clickListener)
        activeButton = view.findViewById(R.id.button_active_pre_test)
        activeButton.setOnClickListener(clickListener)
        purposeButton = view.findViewById(R.id.button_purpose_pre_test)
        purposeButton.setOnClickListener(clickListener)

        titleToolbar = view.findViewById(R.id.title_pre_test)
        backArrowToolbar = view.findViewById(R.id.back_arrow_pre_test)

        backArrowToolbar.setOnClickListener {
            rollbackPreTest()
        }

        layoutArray = arrayListOf(
            nameLayout,
            heightLayout,
            weightLayout,
            ageLayout,
            sexLayout,
            activeLayout,
            purposeLayout
        )
    }
}