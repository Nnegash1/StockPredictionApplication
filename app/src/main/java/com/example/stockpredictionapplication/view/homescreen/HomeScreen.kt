package com.example.stockpredictionapplication.view.homescreen

import android.content.res.AssetFileDescriptor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.stockpredictionapplication.databinding.FragmentHomeScreenBinding
import com.example.stockpredictionapplication.ml.StockPredictionModelApp
import com.example.stockpredictionapplication.view.UIState
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    val modelName = "stockPredictionModelApp.tflite"
    private val viewModel: HomeScreenViewModel by viewModels()
    private lateinit var tfLite: Interpreter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeScreenBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.balance
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.homeScreenState.collect {
                if (it is UIState.Success) {
                    binding.balance.text = it.price.price
                }
            }
        }

        try {
            tfLite = Interpreter(LoadModelFile())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.predict.setOnClickListener {

            viewModel.homeScreenState.value.let {
                if (it is UIState.Success) {
                    val daily = it.price.latestTradingDay.split("-")

                    val year = daily[0].toFloat()
                    val month = daily[1].toFloat()
                    val day = daily[2].toFloat()


                    inference(
                        day,
                        month,
                        year,
                        it.price.high.toFloat(),
                        it.price._open.toFloat(),
                        it.price.low.toFloat()
                    )


                    Log.d(
                        "onViewCreated",
                        " month : $month day : $day year : $year  ${it.price.high.toFloat()}: Price high"
                    )


                }
            }

        }
        viewModel.getGlobalUpdate()
    }

    private fun inference(
        month: Float,
        day: Float,
        year: Float,
        high: Float,
        _open: Float,
        low: Float
    ) {

        val model = StockPredictionModelApp.newInstance(requireContext())

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 6), DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(4 * 6)


        inputFeature0.loadBuffer(byteBuffer)

        byteBuffer.putFloat(day)
        byteBuffer.putFloat(month)
        byteBuffer.putFloat(year)
        byteBuffer.putFloat(high)
        byteBuffer.putFloat(_open)
        byteBuffer.putFloat(low)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
        Log.d("TAG", "inference: ${outputFeature0[0]}")

        binding.balance.text = outputFeature0[0].toString()

// Releases model resources if no longer used.
        model.close()
    }


    @Throws(IOException::class)
    private fun LoadModelFile(): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor =
            requireContext().assets.openFd("stockPredictionModelApp.tflite")
        val fileinputStream: FileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = fileinputStream.channel
        val startOffSets = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffSets, declaredLength)
    }
}