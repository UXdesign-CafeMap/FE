package com.example.cafemap.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.R
import com.example.cafemap.api.service.ListService
import com.example.cafemap.databinding.FragmentHomeBinding
import com.example.cafemap.ui.cafe.CafeDetailActivity
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
    private lateinit var nearCafeViewModel: NearCafeViewModel

    private var shortAnimationDuration: Int = 0

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
            Log.d("whereareyou", it.toString())
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // 클릭된 marker의 위치 정보
        var selectedMarkerPosition: LatLng? = null

        val icon_400 = R.drawable.marker_400
        val icon_300 = R.drawable.marker_300
        val icon_200 = R.drawable.marker_200
        val icon_100 = R.drawable.marker_100

//        val marker1 = MarkerOptions()
//        marker1.position(cafe1)
//        marker1.title("마우스래빗")
//
//        val drawable1 = ResourcesCompat.getDrawable(resources, R.drawable.marker_400, null)
//        val bitmap1 = drawable1?.toBitmap()
//        val marker1Img = bitmap1?.let { Bitmap.createScaledBitmap(it, 78, 96, false) }
//        marker1.icon(marker1Img?.let { BitmapDescriptorFactory.fromBitmap(it) })
//
//        // map에 marker들 추가
//        val addedMarker1 = mMap.addMarker(marker1)
//        addedMarker1?.tag = 1
        val cafe1 = LatLng(37.542, 127.070899)
        addMarkerToMap(cafe1, "마우스래빗", icon_300, 1)
        val cafe2 = LatLng(37.542398, 127.071304)
        addMarkerToMap(cafe2, "투썸플레이스 건대입구점", icon_200, 2)
        val cafe3 = LatLng(37.541743, 127.07091)
        addMarkerToMap(cafe3, "카페 아르무아", icon_200, 3)


        // 각 marker에 click event 설정
        googleMap.setOnMarkerClickListener { clickedMarker ->
//            // tag에 따라 클릭된 마커 처리
//            when (clickedMarker.tag) {
//                1 -> {
//                    // 클릭된 마커의 위치 정보
//                    selectedMarkerPosition = clickedMarker.position
//                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
//                }
//                2 -> {
//
//                }
//            }
            selectedMarkerPosition = clickedMarker.position
            updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)

            true
        }
        // 다른 곳 클릭하면 레이아웃 원래대로 복구
        googleMap.setOnMapClickListener { clickedLatLng ->
            selectedMarkerPosition = null
            restoreOriginalView()
            true
        }

        // 위치 초기화 (기준은 cafeId = 1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe1, 15f))
    }

    private fun addMarkerToMap(cafeLocation: LatLng, cafeName: String, icon: Int, tag: Int) {
        val marker = MarkerOptions()
        marker.position(cafeLocation)
        marker.title(cafeName)

        val drawable = ResourcesCompat.getDrawable(resources, icon, null)
        val bitmap = drawable?.toBitmap()
        val markerImg = bitmap?.let { Bitmap.createScaledBitmap(it, 78, 96, false) }
        marker.icon(markerImg?.let { BitmapDescriptorFactory.fromBitmap(it) })

        // map에 marker 추가
        val addedMarker = mMap.addMarker(marker)
        addedMarker?.tag = tag
    }


    fun updateNearCafe(latitude: Double, longitude: Double) {
        var cafeId = 0
        // 마커 카페 정보 가져오기
        userService.getCafeMarker(longitude, latitude)

        nearCafeViewModel = userService.getNearCafeViewModel()

        nearCafeViewModel.markerCafe.observe(this, Observer {markerCafeResponse ->
            cafeId = markerCafeResponse.cafeId

            val totalSeat = markerCafeResponse.totalSeat
            val remainSeat = markerCafeResponse.remainSeat
            val totalMultiTap = markerCafeResponse.totalMultitap
            val remainMultiTap = markerCafeResponse.remainMultitap

            binding.apply {
                nearCafeName.text = markerCafeResponse.name
                nearCafeDistance.text = markerCafeResponse.distance
                nearCafeLocation.text = markerCafeResponse.address

                val dens = (remainSeat.toDouble() / totalSeat.toDouble()) * 100
                val densText = nearCafeDenseLabel
                val densColor = nearCafeDense
                if (dens <= 30) {
                    densText.text = "여유"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_100
                    ))
                } else if (dens <= 60) {
                    densText.text = "보통"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_200
                    ))
                } else if (dens <= 90) {
                    densText.text = "혼잡"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_300
                    ))
                } else {
                    densText.text = "만석"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_400
                    ))
                }

                nearCafeOpenHours.text = markerCafeResponse.onpeningHours
                nearCafeSeats.text = remainSeat.toString() + '/' + totalSeat.toString()
                nearCafeSeatsMultiTab.text = remainMultiTap.toString() + '/' + totalMultiTap.toString()
                nearCafeVolume.text = markerCafeResponse.volume
            }

        })

//        binding.clHomeList.visibility = View.GONE
//        binding.clHomeNearCafe.visibility = View.VISIBLE
        crossFade(binding.clHomeNearCafe, binding.clHomeList)

        binding.clHomeNearCafe.setOnClickListener() {
            val i = Intent(requireContext(), CafeDetailActivity::class.java)
            i.putExtra("cafeId", cafeId)
            startActivity(i)
        }
        crossFade(binding.clHomeNearCafe, binding.clHomeList)
    }

    fun restoreOriginalView() {
//        binding.clHomeList.visibility = View.VISIBLE
//        binding.clHomeNearCafe.visibility = View.GONE
        crossFade(binding.clHomeList, binding.clHomeNearCafe)
    }

    fun crossFade(fadeInLayout: ConstraintLayout, fadeOutLayout: ConstraintLayout) {
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        fadeInLayout.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        fadeOutLayout.animate().alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    fadeOutLayout.visibility = View.GONE
                }
            })
    }
}