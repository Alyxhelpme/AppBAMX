package mx.itesm.bamx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import mx.itesm.bamx.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var progressBar1 : ProgressBar
    lateinit var progressBar2 : ProgressBar
    lateinit var progressBar3: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar1 = view.findViewById(R.id.progressBar1)
        progressBar1.max = 100
        progressBar1.min = 0
        val currentProgress1 = 50
        progressBar1.progress = currentProgress1

        progressBar2 = view.findViewById(R.id.progressBar2)
        progressBar2.max = 200
        progressBar2.min = 0
        val currentProgress2 = 50
        progressBar2.progress = currentProgress2

        progressBar3 = view.findViewById(R.id.progressBar3)
        progressBar3.max = 50
        progressBar3.min = 0
        val currentProgress3 = 50
        progressBar3.progress = currentProgress3


        // Inflate the layout for this fragment
        return view



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}