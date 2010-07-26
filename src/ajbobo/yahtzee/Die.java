package ajbobo.yahtzee;

public class Die
{
	private int _value;
	private boolean _checked;
	
	public Die()
	{
		setChecked(false);
		setValue(0);
	}

	public void setValue(int _value)
	{
		this._value = _value;
	}

	public int getValue()
	{
		return _value;
	}

	public void setChecked(boolean _checked)
	{
		this._checked = _checked;
	}

	public boolean isChecked()
	{
		return _checked;
	}
}
