package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding // Declare binding variable

    private var onImageSelected = 0
    private var onTextSelected = 0
    private var onColorSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()
        binding.btn.setOnClickListener { showCustomToast() }
    }

    private fun initSpinner() {
        setUpSpinner(binding.iconSpinner, ICON_NAMES) { position -> onImageSelected = position }
        setUpSpinner(binding.textSpinner, TEXT_NAMES) { position -> onTextSelected = position }
        setUpSpinner(binding.imageSpinner, TEXT_COLORS.keys.toList()) { position -> onColorSelected = position }
    }


    private fun setUpSpinner(spinner: Spinner, items: List<String>, onItemSelected: (Int) -> Unit) {
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }


    private fun showCustomToast() {
        val layout = LayoutInflater.from(this).inflate(R.layout.toast_layout, null)
        val imageView: ImageView = layout.findViewById(R.id.imageView)
        val textView: TextView = layout.findViewById(R.id.toast_message)

        if (onImageSelected != 0) {
            imageView.setImageResource(ICONS[onImageSelected - 1])
        }

        textView.text = TEXT_NAMES[onTextSelected]

        val colorCode = TEXT_COLORS.values.toList().getOrElse(onColorSelected) { "#000000" }
        textView.setTextColor(Color.parseColor(colorCode))


        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
        }.show()
    }


    companion object {
        private val ICON_NAMES = listOf("None", "3D Rotation", "4G mobile", "30 Image")
        private val ICONS = listOf(
            R.drawable.baseline_3d_rotation_24,
            R.drawable.baseline_4g_mobiledata_24,
            R.drawable.baseline_30fps_24
        )
        private val TEXT_NAMES =
            listOf("None", "Hello, How are you", "4G mobile Dummy", "30 Image Dummy")
        private val TEXT_COLORS = mapOf(
            "None" to "#000000",
            "RED" to "#FF0000",
            "BLUE" to "#0000FF",
            "GREEN" to "#008000"
        )
    }

}