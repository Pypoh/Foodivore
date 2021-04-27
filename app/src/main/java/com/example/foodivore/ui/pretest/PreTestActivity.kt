package com.example.foodivore.ui.pretest

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityPreTestBinding
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.auth.login.LoginRepoImpl
import com.example.foodivore.repository.datasource.remote.pretest.PreTestRepoImpl
import com.example.foodivore.repository.model.Post
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.AuthActivity
import com.example.foodivore.ui.auth.login.LoginVMFactory
import com.example.foodivore.ui.auth.login.LoginViewModel
import com.example.foodivore.ui.auth.login.domain.LoginImpl
import com.example.foodivore.ui.pretest.adapter.ArticleAdapter
import com.example.foodivore.ui.pretest.domain.PreTestImpl
import com.example.foodivore.utils.toast
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.lang.Exception

class PreTestActivity : AppCompatActivity() {

    private lateinit var preTestBinding: ActivityPreTestBinding

    private val preTestViewModel: PreTestViewModel by lazy {
        ViewModelProvider(
            this,
            PreTestVMFactory(PreTestImpl(PreTestRepoImpl()))
        ).get(PreTestViewModel::class.java)
    }

    private lateinit var sessionManager: SessionManager

    private var pageState: Int = 0
    private var sexChoice: Int = 0

    private lateinit var titleToolbar: MaterialTextView
    private lateinit var backArrowToolbar: ImageView

    // Layout
    private lateinit var nameLayout: RelativeLayout
    private lateinit var heightLayout: RelativeLayout
    private lateinit var weightLayout: RelativeLayout
    private lateinit var sexLayout: RelativeLayout
    private lateinit var ageLayout: RelativeLayout
    private lateinit var activeLayout: RelativeLayout
    private lateinit var purposeLayout: RelativeLayout

    // Input
    private lateinit var nameInput: TextInputEditText
    private lateinit var heightInput: TextInputEditText
    private lateinit var weightInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText

    private lateinit var maleImageInput: ImageView
    private lateinit var femaleImageInput: ImageView

    // Button
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

        sessionManager = SessionManager(this)

        setupViews(preTestBinding.root)

        initPreTest(pageState)

        setupRecycler(preTestBinding.root)
    }

    private fun setupRecycler(view: View) {
        dummyActiveData.add(
            Post.Article(
                "Rendah",
                "Sedikit atau tidak ada olahraga. Habiskan sebagian besar hari dengan duduk (mis. teller bank)"
            )
        )
        dummyActiveData.add(
            Post.Article(
                "Biasa",
                "Habiskan sebagian besar hari Anda dengan berdiri sendiri (mis. Guru, pedagang)"
            )
        )
        dummyActiveData.add(
            Post.Article(
                "Aktif",
                "Latihan intensif melakukan beberapa aktivitas fisik (mis. tukang pos, pelayan)"
            )
        )
        dummyActiveData.add(
            Post.Article(
                "Sangat Aktif",
                "Latihan yang sangat intens dengan melakukan aktivitas fisik yang berat (mis. fitness, berenang)"
            )
        )

        dummyPurposeData.add(
            Post.Article(
                "Menurunkan berat badan",
                "Mengurangi porsi asupan makanan"
            )
        )
        dummyPurposeData.add(Post.Article("Menjadi lebih bugar", "Penuh energi & sehat"))
        dummyPurposeData.add(Post.Article("Menaikkan berat badan", "Menambah porsi asupan makanan"))

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

    private fun nextPreTest() {
        Log.d("PreTestActivity", "PageState: $pageState")


        if (pageState == 6) {
            try {
                Log.d("PreTestActivity", "User : ${sessionManager.fetchAuthToken()!!} Preparing to upload data...")
                preTestViewModel.postPreTestData(
                    User.PreTestData(
                        name = nameInput.text.toString(),
                        height = heightInput.text.toString(),
                        weight = weightInput.text.toString(),
                        age = ageInput.text.toString(),
                        sex = when (sexChoice) {
                            1 -> {
                                "Laki-Laki"
                            }
                            2 -> {
                                "Perempuan"
                            }
                            else -> {
                                "Unknown"
                            }
                        },
                        activity = adapterActive.getLastCheckedItem()!!.title,
                        target = adapterPurpose.getLastCheckedItem()!!.title
                    ),
                    sessionManager.fetchAuthToken()!!
                ).observe(this, { task ->
                    when (task) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            Log.d("PreTestActivity", task.data!!.message)
                        }

                        is Resource.Failure -> {
                            Log.d("PreTestActivity", task.throwable.message.toString())
                        }

                        else -> {

                        }
                    }
                })
            } catch (e: Exception) {

            }
        }

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

    private fun checkPrecondition(): Boolean {
        when (pageState) {
            0 -> {
                if (nameInput.text!!.isEmpty()) {
                    return false
                }
            }
            1 -> {
                if (heightInput.text!!.isEmpty()) {
                    return false
                }
            }
            2 -> {
                if (weightInput.text!!.isEmpty()) {
                    return false
                }
            }
            3 -> {
                if (ageInput.text!!.isEmpty()) {
                    return false
                }
            }
            4 -> {
                if (sexChoice == 0) {
                    return false
                }
            }
            5 -> {

            }
            6 -> {

            }
        }
        return true
    }

    private fun setupViews(view: View) {
        val clickListener = View.OnClickListener {
            if (checkPrecondition()) {
                nextPreTest()
            }
        }

        nameLayout = view.findViewById(R.id.layout_name_pre_test)
        heightLayout = view.findViewById(R.id.layout_height_pre_test)
        weightLayout = view.findViewById(R.id.layout_weight_pre_test)
        sexLayout = view.findViewById(R.id.layout_sex_pre_test)
        ageLayout = view.findViewById(R.id.layout_age_pre_test)
        activeLayout = view.findViewById(R.id.layout_active_pre_test)
        purposeLayout = view.findViewById(R.id.layout_purpose_pre_test)

        nameInput = view.findViewById(R.id.input_name_pre_test)
        heightInput = view.findViewById(R.id.input_height_pre_test)
        weightInput = view.findViewById(R.id.input_weight_pre_test)
        ageInput = view.findViewById(R.id.input_age_pre_test)

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

        maleImageInput = view.findViewById(R.id.male_choice_pre_test)
        maleImageInput.setOnClickListener {
            if (sexChoice == 2) {
                femaleImageInput.scaleX = 1.0f
                femaleImageInput.scaleY = 1.0f
            }
            maleImageInput.scaleX = 1.5f
            maleImageInput.scaleY = 1.5f
            sexChoice = 1
        }
        femaleImageInput = view.findViewById(R.id.female_choice_pre_test)
        femaleImageInput.setOnClickListener {
            if (sexChoice == 1) {
                maleImageInput.scaleX = 1.0f
                maleImageInput.scaleY = 1.0f
            }
            femaleImageInput.scaleX = 1.5f
            femaleImageInput.scaleY = 1.5f
            sexChoice = 2
        }

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