package com.example.cafemap.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.R
import com.example.cafemap.api.service.ListService
import com.example.cafemap.databinding.FragmentHomeBinding
import com.example.cafemap.ui.cafe.CafeDetailActivity
import com.example.cafemap.ui.cafe.SearchCafeAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userService: ListService
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val mapView = MapView(context)
//        binding.mapHome.addView(mapView)

        init()
        return root
    }

    fun init() {
        userService = ListService
        userService.getCafes()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_home) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HomeListAdapter()
        adapter.itemClickListener = object: HomeListAdapter.OnItemClickListener {
            override fun onItemClicked(cafeId: Int) {
                val i = Intent(requireContext(), CafeDetailActivity::class.java)
                i.putExtra("cafeId", cafeId)
                startActivity(i)
            }
        }

        binding.rvHome.adapter = adapter

        val searchCafeViewModel = userService.getSearchCafeViewModel()

        searchCafeViewModel.itemList.observe(viewLifecycleOwner, Observer {
            (binding.rvHome.adapter as HomeListAdapter).setData(it)
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // cafeId = 1
        val cafe1 = LatLng(37.542, 127.070899)

        val marker1 = MarkerOptions()
        marker1.position(cafe1)
        marker1.title("마우스래빗")

        val drawable1 = ResourcesCompat.getDrawable(resources, R.drawable.marker_400, null)
        val bitmap1 = drawable1?.toBitmap()
        val marker1Img = bitmap1?.let { Bitmap.createScaledBitmap(it, 78, 96, false) }
        marker1.icon(marker1Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        // map에 marker들 추가
        val addedMarker1 = mMap.addMarker(marker1)
        addedMarker1?.tag = 1

        // 각 marker에 click event 설정
        googleMap.setOnMarkerClickListener { clickedMarker ->
            // tag에 따라 클릭된 마커 처리
            when (clickedMarker.tag) {
                1 -> {
                    Log.d("seohyunMarker",clickedMarker.tag.toString())
                    updateNearCafe(1)
                }
                2 -> {

                }
            }
            true
        }
        // 다른 곳 클릭하면 레이아웃 원래대로 복구.. 어케 함?

        // 위치 초기화 (기준은 cafeId = 1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe1, 14f))
    }

    fun updateNearCafe(tag: Int) {
        binding.tvHomeLabel.visibility = View.GONE
        binding.vHomeLine.visibility = View.GONE
        binding.rvHome.visibility = View.GONE

        binding.clHomeNearCafe.visibility = View.VISIBLE
//        id가 tag인 cafe 불러와서 text랑 기타 등등.. 세팅
//        binding.nearCafeName.text =
    }
}