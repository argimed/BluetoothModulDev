package com.example.bluetoothmoduldev

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bluetoothmoduldev.databinding.FragmentMainBinding
import com.example.bt_def.BluetoothConstants
import com.example.bt_def.bluetooth.BluetoothController

class MainFragment : Fragment(), BluetoothController.Listener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var bluetoothController: BluetoothController
    private lateinit var btAdapter: BluetoothAdapter
    private var speed_sens = 0 // режим работы датчиков
    private var display_mode = 0 // режим вывода на дисплей
    private var disp_bright = 0 // яркость дисплея
    private var longitude = 0
    private var latitude = 0// долгота
    private var altitude = 0 // высота
    private var satellites_active = 0// количество активных спутников
    private var satellites_visible = 0 // количество видимых спутников
    private var light_on = false // принудительное включение фар
    private var wheel_length = 0f //длина окружности колеса в метрах
    private var battery = 0f // состояние аккумулятора
    private var DIST = 0f // пройденное расстояние
    private var SPEED = 0f // скорость


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        val pref = activity?.getSharedPreferences(
            BluetoothConstants.PREFERENCES, Context.MODE_PRIVATE
        )
        val mac = pref?.getString(BluetoothConstants.MAC, "")
        bluetoothController = BluetoothController(btAdapter)

        binding.bList.setOnClickListener {// кнопка списка устройств
            findNavController().navigate(R.id.action_mainFragment_to_deviceListFragment)
        }
        binding.bConnect.setOnClickListener { //кнопка подключения
            if (bluetoothController.connectThread == null) { // если нет подключения
                bluetoothController.connect(mac ?: "", this) // подключиться
            } else { // если подключение есть
                bluetoothController.closeConnection() // отключиться
            }
        }

        binding.bMap.setOnClickListener {// кнопка местоположения
            bluetoothController.sendMessage("3,1,1,0,0,0,0,0,0,2.05,0.00,1.20,0.00")
        }

    }

    private fun initBtAdapter() {
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
    }

    override fun onReceive(message: String) {
        activity?.runOnUiThread {
            when (message) {
                BluetoothController.BLUETOOTH_CONNECTED -> {
                    binding.bConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), com.example.bt_def.R.color.red)
                    binding.bConnect.text = "Отключиться"
                }
                BluetoothController.BLUETOOTH_NO_CONNECTED -> {
                    binding.bConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), com.example.bt_def.R.color.green)
                    binding.bConnect.text = "Подключиться"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothController.closeConnection()
    }
}