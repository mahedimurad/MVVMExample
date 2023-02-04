package com.sarwar.mvvmexample.ui.views

import android.content.IntentFilter
import android.media.Image
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.key
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarwar.mvvmexample.R
import com.sarwar.mvvmexample.data.network.api.ApiService
import com.sarwar.mvvmexample.data.network.model.ImageModel
import com.sarwar.mvvmexample.data.network.model.UnsplashApiResponse
import com.sarwar.mvvmexample.databinding.FragmentGalleryBinding
import com.sarwar.mvvmexample.ui.adapter.PhotoAdapter
import com.sarwar.mvvmexample.utils.NetworkBroadcastReceiver
import com.sarwar.mvvmexample.utils.NetworkManager
import com.sarwar.mvvmexample.utils.Status
import com.sarwar.mvvmexample.viewmodel.GalleryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@AndroidEntryPoint
class GalleryFragment : Fragment() {

    lateinit var binding: FragmentGalleryBinding
    private var photos = ArrayList<ImageModel>()
    private var searchKey: String = ""
    private lateinit var networkBroadcastReceiver: NetworkBroadcastReceiver

    private val viewModel: GalleryFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_gallery, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        setObservers()

        searchImage("cat")

        binding.btnSearch.setOnClickListener {
            searchImage(binding.etSearch.text.toString())
        }

        networkBroadcastReceiver = NetworkBroadcastReceiver(requireContext()){
            if(it){
                searchImage(binding.etSearch.text.toString())
            }else{
               Toast.makeText(requireContext(),"Internet Unavailable",Toast.LENGTH_SHORT).show()
            }
        }

        requireContext().registerReceiver(networkBroadcastReceiver,  IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun searchImage(keyword: String) {
        viewModel.searchImage(keyword)
    }

    private fun setObservers() {

        viewModel.imagesLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {

                Status.SUCCESS -> {
                    resource.data?.let {
                        photos.clear()
                        photos.addAll(it.results)
                        binding.rvGallery.adapter!!.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                    }

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }


        }

        viewModel.searchKeyLiveData.observe(viewLifecycleOwner) {
            viewModel.searchImage(it);
        }


    }

    private fun prepareRecyclerView() {
        binding.rvGallery.setHasFixedSize(true)
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGallery.adapter = PhotoAdapter(requireContext(), photos) {
            findNavController().navigate(
                GalleryFragmentDirections.actionGalleryFragmentToPhotoViewerFragment(
                    it.urls.small
                )
            )
        }

        /*binding.rvGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    // viewModel.searchImage(searchKey);
                }
            }
        })*/
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkBroadcastReceiver)
    }


}