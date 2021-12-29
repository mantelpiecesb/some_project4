package com.codinginflow.imagesearchapp.ui.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentPhotosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos), PhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<PhotosViewModel>()

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private var isImageShown = false
    private val adapter = PhotoAdapter(this)

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotosBinding.bind(view)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() },
            )
        }

        viewModel.context = requireContext()

        initObservePhotos()

        binding.swipeRefreshLayout?.setOnRefreshListener {
            viewModel.reloadPhotos()
            initObservePhotos()
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipeRefreshLayout!!.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: String) {
        binding.fullscreenImage?.let {
            if(!isImageShown) {
                Glide.with(binding.recyclerView)
                    .load(photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(it)
                it.visibility = View.VISIBLE
                isImageShown = true
            } else {
                it.visibility = View.GONE
                isImageShown = false
            }
        }
    }

    private fun initObservePhotos() {
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }


}
