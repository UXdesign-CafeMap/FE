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

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.marker_400, null)
        val bitmap = drawable?.toBitmap()
        val marker1Img = bitmap?.let { Bitmap.createScaledBitmap(it, 78, 96, false) }
        marker1.icon(marker1Img?.let { BitmapDescriptorFactory.fromBitmap(it) })

        // map에 marker들 추가
        mMap.addMarker(marker1)

        // 각 marker에 click event 설정
        googleMap.setOnMarkerClickListener { clickedMarker ->
            // 클릭된 마커 처리
            if(clickedMarker == marker1){

            }
            false
        }

        // 위치 초기화 (기준은 cafeId = 1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe1, 14f))
        setCurrentLocation()

    }

    fun setCurrentLocation(){
        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10.0f, locationListener)
    }
    val locationListener = object : android.location.LocationListener {
        override fun onLocationChanged(location: Location) {
            // 위치가 변경되었을 때 호출됩니다.
            val latitude = location.latitude
            val longitude = location.longitude
        }

        override fun onLocationChanged(locations: MutableList<Location>) {
            super.onLocationChanged(locations)
            //위치가 변경되어 위치가 일괄 전달될 때 호출됩니다.
        }
        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            // 사용자가 GPS를 끄는 등의 행동을 해서 위치값에 접근할 수 없을 때 호출됩니다.
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            // 사용자가 GPS를 on하는 등의 행동을 해서 위치값에 접근할 수 있게 되었을 때 호출됩니다.
        }

        override fun onFlushComplete(requestCode: Int) {
            super.onFlushComplete(requestCode)
            //플러시 작업이 완료되고 플러시된 위치가 전달된 후 호출됩니다.
        }
    }
}