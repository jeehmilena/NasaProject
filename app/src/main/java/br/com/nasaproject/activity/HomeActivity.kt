package br.com.nasaproject.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.nasaproject.R
import br.com.nasaproject.converteData
import br.com.nasaproject.model.ApodResult
import br.com.nasaproject.repository.ApodRepository
import br.com.nasaproject.usecase.ApodUseCase
import br.com.nasaproject.viewmodel.ApodViewModel
import br.com.nasaproject.viewmodel.ApodViewModelFactory
import br.com.nasaproject.viewmodel.states.ApodEvent
import br.com.nasaproject.viewmodel.states.ApodInteractor
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private lateinit var inputDate: TextInputLayout
    private lateinit var buttonSend: AppCompatButton
    private lateinit var apodDetails: ConstraintLayout
    private lateinit var titileDescription: AppCompatTextView
    private lateinit var imageApod: AppCompatImageView
    private lateinit var textDescriptionApod: AppCompatTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var repository: ApodRepository
    private lateinit var useCase: ApodUseCase
    private lateinit var factory: ApodViewModelFactory
    private lateinit var viewModel: ApodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        initViewModel()

        buttonSend.setOnClickListener {
            sendDate()
        }
    }

    private fun initViews() {
        inputDate = findViewById(R.id.til_data)
        buttonSend = findViewById(R.id.bt_apod)
        apodDetails = findViewById(R.id.layout_apod_details)
        titileDescription = findViewById(R.id.tv_title_description)
        imageApod = findViewById(R.id.iv_apod_image)
        textDescriptionApod = findViewById(R.id.tv_apod_description)
        progressBar = findViewById(R.id.pb_loading)
    }

    private fun initViewModel() {
        repository = ApodRepository()
        useCase = ApodUseCase(repository)
        factory = ApodViewModelFactory(useCase)
        viewModel = ViewModelProviders.of(this, factory).get(ApodViewModel::class.java)
        initObservables()
    }

    private fun initObservables() {
        viewModel.viewEvent.observe(this, Observer { event ->
            event?.let {
                when (it) {
                    is ApodEvent.ShowApod -> showApodDate(it.apod)
                    is ApodEvent.Loading -> loading()
                    is ApodEvent.Error -> showError(it.message)
                }
            }
        })
    }

    private fun sendDate() {
        val date = inputDate.editText?.text.toString().converteData()
        when {
            date.isNotEmpty() -> {
                viewModel.interactor(ApodInteractor.ApodDate(date))
            }
            else -> {
                Snackbar.make(inputDate, "Empty field", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showApodDate(apod: ApodResult) {
        progressBar.visibility = View.GONE

        apodDetails.visibility = View.VISIBLE

        Picasso.with(this)
            .load(apod.url)
            .placeholder(R.drawable.nasa)
            .into(imageApod)

        textDescriptionApod.text = apod.explanation
        inputDate.editText?.text?.clear()
        inputDate.clearFocus()
    }

    private fun loading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(message: String){
        progressBar.visibility = View.GONE
        Snackbar.make(textDescriptionApod, message, Snackbar.LENGTH_LONG).show()
    }
}