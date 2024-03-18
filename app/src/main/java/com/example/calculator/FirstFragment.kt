package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calculator.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var textView: TextView
    private var _binding: FragmentFirstBinding? = null
    private var oldValue: Int? = null
    private var operator: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numbers = mapOf(
            R.id.button_one to "1",
            R.id.button_two to "2",
            R.id.button_three to "3",
            R.id.button_four to "4",
            R.id.button_five to "5",
            R.id.button_six to "6",
            R.id.button_seven to "7",
            R.id.button_eight to "8",
            R.id.button_nine to "9",
            R.id.button_zero to "0"
        )

        for ((buttonId, value) in numbers) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                appendValue(value)
            }
        }

        val operators = mapOf(
            R.id.button_plus to "+",
            R.id.button_minus to "-",
            R.id.button_multiply to "*",
            R.id.button_divide to "/"
        )

        for ((buttonId, value) in operators) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                setOperator(value)
            }
        }

        view.findViewById<Button>(R.id.button_equal).setOnClickListener {
            calculateResult()
        }

        view.findViewById<Button>(R.id.button_c).setOnClickListener {
            clear()
        }

        textView = view.findViewById(R.id.textView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun appendValue(value: String) {
        var currentValue = textView.text.toString()

        if (currentValue == "0")
            currentValue = ""

        textView.text = buildString {
            append(currentValue)
            append(value)
        }
    }

    private fun setOperator(op: String) {
        operator = op
        oldValue = textView.text.toString().toInt()
        textView.text = "0"
    }

    private fun calculateResult() {
        val newValue = textView.text.toString().toInt()
        val result = when (operator) {
            "+" -> oldValue?.plus(newValue)
            "-" -> oldValue?.minus(newValue)
            "*" -> oldValue?.times(newValue)
            "/" -> oldValue?.div(newValue)
            else -> throw IllegalArgumentException("Invalid operator")
        }
        textView.text = result.toString()
    }

    private fun clear() {
        operator = null
        oldValue = null
        textView.text = "0"
    }
}