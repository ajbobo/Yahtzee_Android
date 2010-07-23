package ajbobo.yahtzee;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class DiceAdapter extends BaseAdapter
{
	private MainGame _context;
	private Integer[] _unselectedimages = {
			R.drawable.die_1_unselected,
			R.drawable.die_2_unselected,
			R.drawable.die_3_unselected,
			R.drawable.die_4_unselected,
			R.drawable.die_5_unselected,
			R.drawable.die_6_unselected,
	};
	private Integer[] _selectedimages = {
			R.drawable.die_1_selected,
			R.drawable.die_2_selected,
			R.drawable.die_3_selected,
			R.drawable.die_4_selected,
			R.drawable.die_5_selected,
			R.drawable.die_6_selected,
	};
	
	public DiceAdapter(Context c) 
	{
      _context = (MainGame)c;
  }

	public int getCount()
	{
		return 5;
	}

	public Object getItem(int arg0)
	{
		return null;
	}

	public long getItemId(int arg0)
	{
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView iv = null;
		if (convertView == null) // Not recycling - initialize stuff
		{
			iv = new ImageView(_context);
			iv.setLayoutParams(new GridView.LayoutParams(35,35));
			iv.setPadding(0, 0, 0, 0);
		}
		else
		{
			iv = (ImageView)convertView;
		}
		
		Integer[] array;
		Die die = _context.GetDie(position);
		if (die.isChecked())
			array = _selectedimages;
		else
			array = _unselectedimages;
		int value = die.getValue();
		Integer image = array[value];
		iv.setImageResource(image);
		
		return iv;
	}

}
