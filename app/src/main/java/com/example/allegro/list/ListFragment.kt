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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentListBinding.bind(view)

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
            recyclerView.setHasFixedSize(true)
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
            binding.recyclerView.scrollToPosition(0)
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    private fun createDialog(): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val currentlySelectedIndex = when (viewModel.currentQuery.value) {
                GithubService.SortOptions.FULL_NAME -> 0
                GithubService.SortOptions.CREATED -> 1
                GithubService.SortOptions.UPDATED -> 2
                GithubService.SortOptions.PUSHED -> 3
                else -> 0
            }

            builder.setTitle(R.string.choose_order)
                .setSingleChoiceItems(R.array.sort_orders, currentlySelectedIndex, null)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition

                    val selectedOption = when (selectedPosition) {
                        0 -> GithubService.SortOptions.FULL_NAME
                        1 -> GithubService.SortOptions.CREATED
                        2 -> GithubService.SortOptions.UPDATED
                        3 -> GithubService.SortOptions.PUSHED
                        else -> GithubService.SortOptions.FULL_NAME
                    }
                    viewModel.changeSortOrder(selectedOption)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
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