package com.example.horoscapp.ui.palmistry

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import com.example.horoscapp.databinding.FragmentPalmistryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PalmistryFragment : Fragment() {

    companion object {
        private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    }

    private var _binding : FragmentPalmistryBinding? = null
    private val binding get() = _binding!!

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if (isGranted){
            //start camera
        }else{
            Toast.makeText(requireContext(), "Acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPalmistryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkCameraPermission()){

        }else{
            requestPermission.launch(CAMERA_PERMISSION)
        }

        initUI()
    }

    private fun checkCameraPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(), CAMERA_PERMISSION
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    private fun initUI() {

    }

}