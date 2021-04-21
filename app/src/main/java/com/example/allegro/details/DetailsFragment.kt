package com.example.allegro.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.allegro.R
import com.example.allegro.data.GithubRepository
import com.example.allegro.databinding.DetailsFragmentBinding
import com.example.allegro.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModel.AssistedFactory
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.provideFactory(detailsViewModelFactory, args.repository.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = DetailsFragmentBinding.bind(view)

        val repository = args.repository

        ViewCompat.setTransitionName(binding.textViewName, "name_${repository.name}")
        bindRepository(repository)

        val adapter = GithubContributorsAdapter()
        binding.apply {
            recyclerViewContributors.layoutManager = LinearLayoutManager(context)
            recyclerViewContributors.adapter = adapter

            viewModel.contributors.observe(viewLifecycleOwner) { resource ->
                progressBar.isVisible = resource is Resource.Loading
                textViewError.isVisible = resource is Error
                textViewMostContributors.isVisible = resource is Resource.Success

                if (resource is Resource.Success) {
                    adapter.submitList(resource.data)
                }
                if (resource is Resource.Error) {
                    textViewError.text = getString(R.string.contributors_could_not_be_loaded)
                }
            }

            setHasOptionsMenu(true)
        }
    }

    private fun bindRepository(repository: GithubRepository) {
        val createdAt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            .format(repository.createdAt)
        val updatedAt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            .format(repository.updatedAt)

        binding.apply {
            textViewName.text = repository.name
            textViewDescription.text = repository.description
            textViewStarsCount.text = repository.stargazersCount.toString()
            textViewForksCount.text = repository.forksCount.toString()
            textViewWatchersCount.text = repository.watchersCount.toString()
            textViewCreatedAt.text = getString(R.string.created_at, createdAt)
            textViewUpdatedAt.text = getString(R.string.updated_at, updatedAt)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.open_browser) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(args.repository.htmlUrl)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}