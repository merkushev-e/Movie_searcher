package ru.gb.moviesearcher.ui.main.view.history

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.history_fragment.*
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.HistoryFragmentBinding
import ru.gb.moviesearcher.ui.main.utils.hide
import ru.gb.moviesearcher.ui.main.utils.show
import ru.gb.moviesearcher.ui.main.utils.showSnackBar
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.HistoryViewModel


class HistoryFragment : Fragment() {


    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { AppState ->
            renderData(AppState)
        })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessHistory -> {
                with(binding) {
                    historyFragmentRecyclerview.show()
                    includedLoadingLayout.loadingLayout.hide()
                    adapter.setData(appState.movie)
                }
            }
            is AppState.Loading -> {
                with(binding) {
                    historyFragmentRecyclerview.hide()
                    includedLoadingLayout.loadingLayout.show()

                }
            }
            is AppState.Error -> {
                with(binding) {
                    historyFragmentRecyclerview.show()
                    includedLoadingLayout.loadingLayout.hide()
                    historyFragmentRecyclerview.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        {viewModel.getAllHistory()})
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_history){
            viewModel.deleteAllHistory()
        }
        viewModel.getAllHistory()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}