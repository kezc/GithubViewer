package com.example.allegro.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegro.R
import com.example.allegro.databinding.DetailsFragmentBinding
import com.example.allegro.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModel.AssistedFactory
    val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.provideFactory(detailsViewModelFactory, args.repository.name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailsFragmentBinding.bind(view)

        val repository = args.repository

        val createdAt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            .format(repository.createdAt)
        val updatedAt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            .format(repository.updatedAt)

        val adapter = GithubContributorsAdapter()

        binding.apply {
            recyclerViewContributors.layoutManager = LinearLayoutManager(context)
            recyclerViewContributors.adapter = adapter

            textViewName.text = repository.name
            textViewDescription.text = repository.description
            textViewStarsCount.text = repository.stargazersCount.toString()
            textViewForksCount.text = repository.forksCount.toString()
            textViewWatchersCount.text = repository.watchersCount.toString()
            textViewCreatedAt.text = getString(R.string.created_at, createdAt)
            textViewUpdatedAt.text = getString(R.string.updated_at, updatedAt)


            viewModel.contributors.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("DetailsFragment", resource.data.toString())
                        adapter.submitList(resource.data)
                        progressBar.visibility = View.INVISIBLE
                        textViewError.visibility = View.INVISIBLE
                        textViewMostContributors.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> {
                        Log.d("DetailsFragment", "Loading")
                        progressBar.visibility = View.VISIBLE
                        textViewError.visibility = View.INVISIBLE
                        textViewMostContributors.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        Log.d("DetailsFragment", resource.message.toString())
                        progressBar.visibility = View.INVISIBLE
                        textViewMostContributors.visibility = View.INVISIBLE
                        textViewError.visibility = View.VISIBLE
                        textViewError.text = getString(
                            R.string.contributors_could_not_be_loaded
                        )
                    }
                }
            }
        }
    }
}