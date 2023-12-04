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
import com.bumptech.glide.Glide
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

        val icon_400 = ResourcesCompat.getDrawable(resources, R.drawable.marker_400, null)?.toBitmap()
        val icon_400Img = icon_400?.let { Bitmap.createScaledBitmap(it, 70, 86, false) }
        val icon_300 = ResourcesCompat.getDrawable(resources, R.drawable.marker_300, null)?.toBitmap()
        val icon_300Img = icon_300?.let { Bitmap.createScaledBitmap(it, 70, 86, false) }
        val icon_200 = ResourcesCompat.getDrawable(resources, R.drawable.marker_200, null)?.toBitmap()
        val icon_200Img = icon_200?.let { Bitmap.createScaledBitmap(it, 70, 86, false) }
        val icon_100 = ResourcesCompat.getDrawable(resources, R.drawable.marker_100, null)?.toBitmap()
        val icon_100Img = icon_100?.let { Bitmap.createScaledBitmap(it, 70, 86, false) }
        val icon_000 = ResourcesCompat.getDrawable(resources, R.drawable.marker_000, null)?.toBitmap()
        val icon_000Img = icon_000?.let { Bitmap.createScaledBitmap(it, 70, 86, false) }

        val cafe1 = LatLng(37.542, 127.070899)
        val marker1 = MarkerOptions()
        marker1.position(cafe1)
        marker1.title("마우스래빗")
        marker1.icon(icon_100Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe2 = LatLng(37.542398, 127.071304)
        val marker2 = MarkerOptions()
        marker2.position(cafe2)
        marker2.title("투썸플레이스 건대입구점")
        marker2.icon(icon_200Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe3 = LatLng(37.541743, 127.07091)
        val marker3 = MarkerOptions()
        marker3.position(cafe3)
        marker3.title("카페 아르무아")
        marker3.icon(icon_200Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe4 = LatLng(37.545254, 127.073473)
        val marker4 = MarkerOptions()
        marker4.position(cafe4)
        marker4.title("환원당")
        marker4.icon(icon_300Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe5 = LatLng(37.54502, 127.07102)
        val marker5 = MarkerOptions()
        marker5.position(cafe5)
        marker5.title("흐릇")
        marker5.icon(icon_000Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe6 = LatLng( 37.545187, 127.075247)
        val marker6 = MarkerOptions()
        marker6.position(cafe6)
        marker6.title("윤이커피")
        marker6.icon(icon_000Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        val cafe7 = LatLng( 37.53867, 127.077405)
        val marker7 = MarkerOptions()
        marker7.position(cafe7)
        marker7.title("카페 704호")
        marker7.icon(icon_000Img?.let { BitmapDescriptorFactory.fromBitmap(it) })


        // map에 marker들 추가
        val addedMarker1 = mMap.addMarker(marker1)
        addedMarker1?.tag = 1
        val addedMarker2 = mMap.addMarker(marker2)
        addedMarker2?.tag = 2
        val addedMarker3 = mMap.addMarker(marker3)
        addedMarker3?.tag = 3
        val addedMarker4 = mMap.addMarker(marker4)
        addedMarker4?.tag = 4
        val addedMarker5 = mMap.addMarker(marker5)
        addedMarker5?.tag = 5
        val addedMarker6 = mMap.addMarker(marker6)
        addedMarker6?.tag = 6
        val addedMarker7 = mMap.addMarker(marker7)
        addedMarker7?.tag = 7


        // 각 marker에 click event 설정
        googleMap.setOnMarkerClickListener { clickedMarker ->
            // tag에 따라 클릭된 마커 처리
            when (clickedMarker.tag) {
                1 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                2 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                2 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                3 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                4 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                5 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                6 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }
                7 -> {
                    selectedMarkerPosition = clickedMarker.position
                    updateNearCafe(selectedMarkerPosition!!.latitude, selectedMarkerPosition!!.longitude)
                }

            }
            true
        }
        // 다른 곳 클릭하면 레이아웃 원래대로 복구.. 어케 함?
        googleMap.setOnMapClickListener { clickedLatLng ->
            selectedMarkerPosition = null
            restoreOriginalView()
            true
        }

        // 위치 초기화 (기준은 cafeId = 1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe1, 15f))
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
                context?.let {
                    Glide.with(it)
                        .load(markerCafeResponse.cafeImage)
                        .into(ivNearCafeImg)
                }

                val dens = ((totalSeat - remainSeat).toDouble() / totalSeat.toDouble()) * 100
                val densText = nearCafeDenseLabel
                val densColor = nearCafeDense
                if (markerCafeResponse.isOpen == "휴무") {
                    densText.text = "휴무"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_FFF
                    ))
                } else if (markerCafeResponse.isOpen == "영업전") {
                    densText.text = "영업전"
                    densColor.setCardBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.DENS_FFF
                    ))
                } else if (dens <= 30) {
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