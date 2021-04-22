package com.example.allegro.list

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegro.R
import com.example.allegro.api.GithubService
import com.example.allegro.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        val adapter = GithubRepositoriesAdapter { repository, itemBinding ->
            val extras = FragmentNavigatorExtras(
                itemBinding.textViewName to "name_${repository.name}",
            )
            findNavController().navigate(
                ListFragmentDirections.toDetailsFragment(repository), extras
            )
        }

        postponeEnterTransition()
        view.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        binding.apply {
            recyclerView.setHasFixedSize(false)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                GithubRepositoriesLoadStateAdapter { adapter.retry() },
                GithubRepositoriesLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                }
            }
        }

        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    private fun createDialog(): Dialog =
        activity?.let {
            val builder = AlertDialog.Builder(it)

            val currentlySelectedIndex = getCurrentlySelectedSortingOptionIndex()

            builder.setTitle(R.string.choose_order)
                .setSingleChoiceItems(R.array.sort_orders, currentlySelectedIndex, null)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                    val selectedOption = getSortingOptionByIndex(selectedPosition)

                    viewModel.changeSortOrder(selectedOption)
                    binding.recyclerView.scrollToPosition(0)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    private fun getSortingOptionByIndex(selectedPosition: Int): GithubService.SortingOptions =
        when (selectedPosition) {
            0 -> GithubService.SortingOptions.FULL_NAME
            1 -> GithubService.SortingOptions.CREATED
            2 -> GithubService.SortingOptions.UPDATED
            3 -> GithubService.SortingOptions.PUSHED
            else -> GithubService.SortingOptions.FULL_NAME
        }

    private fun getCurrentlySelectedSortingOptionIndex(): Int =
        when (viewModel.currentSortingOption.value) {
            GithubService.SortingOptions.FULL_NAME -> 0
            GithubService.SortingOptions.CREATED -> 1
            GithubService.SortingOptions.UPDATED -> 2
            GithubService.SortingOptions.PUSHED -> 3
            else -> 0
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sorting_order) {
            createDialog().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}