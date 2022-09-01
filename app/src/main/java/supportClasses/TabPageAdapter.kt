package supportClasses

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import mx.itesm.bamx.activities.RootTabActivity
import mx.itesm.bamx.activities.fragments.DonationFragment
import mx.itesm.bamx.activities.fragments.MapFragment
import mx.itesm.bamx.activities.fragments.RewardsFragment


class TabPageAdapter(activity: RootTabActivity, private val tabCount : Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RewardsFragment()
            1 -> MapFragment()
            2 -> DonationFragment()
            else -> MapFragment()
        }
    }

}