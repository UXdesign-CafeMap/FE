package com.example.cafemap.ui.home

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.R
import com.example.cafemap.api.model.domain.Cafe
import com.example.cafemap.api.model.dto.MarkerCafeResponse
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
    private lateinit var nearCafeViewModel: NearCafeViewModel

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
                    // 클릭된 마커의 위치 정보
                    val clickedLatLng = clickedMarker.position
                    updateNearCafe(clickedLatLng.longitude, clickedLatLng.latitude)
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

    @SuppressLint("ResourceAsColor")
    fun updateNearCafe(longitude: Double, latitude: Double) {
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
                    densColor.setCardBackgroundColor(R.color.DENS_100)
                } else if (dens <= 60) {
                    densText.text = "보통"
                    densColor.setCardBackgroundColor(R.color.DENS_200)
                } else if (dens <= 90) {
                    densText.text = "혼잡"
                    densColor.setCardBackgroundColor(R.color.DENS_300)
                } else {
                    densText.text = "만석"
                    densColor.setCardBackgroundColor(R.color.DENS_400)
                }

                nearCafeOpenHours.text = markerCafeResponse.onpeningHours
                nearCafeSeats.text = remainSeat.toString() + '/' + totalSeat.toString()
                nearCafeSeatsMultiTab.text = remainMultiTap.toString() + '/' + totalMultiTap.toString()
                nearCafeVolume.text = markerCafeResponse.volume
            }

        })

        binding.tvHomeLabel.visibility = View.GONE
        binding.vHomeLine.visibility = View.GONE
        binding.rvHome.visibility = View.GONE

        binding.clHomeNearCafe.visibility = View.VISIBLE
        binding.clHomeNearCafe.setOnClickListener() {
            val i = Intent(requireContext(), CafeDetailActivity::class.java)
            i.putExtra("cafeId", cafeId)
            startActivity(i)
        }

    }
}