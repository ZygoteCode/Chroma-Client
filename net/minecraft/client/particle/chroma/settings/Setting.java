package net.minecraft.client.particle.chroma.settings;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.module.Module;

public class Setting
{
	private String name;
	private Module parent;
	private String mode;
	private String description;
	
	private String sval;
	private ArrayList<String> options;
	
	private boolean bval;
	
	private double dval;
	private double min;
	private double max;
	private boolean onlyint = false;
	
	private int id;
	private boolean visible;
	
	public Setting(int id, String name, String description, Module parent, String sval, ArrayList<String> options)
	{
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
		this.mode = "Combo";
		this.id = id;
		this.visible = true;
	}
	
	public Setting(int id, String name, String description, Module parent, boolean bval)
	{
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.bval = bval;
		this.mode = "Check";
		this.id = id;
		this.visible = true;
	}
	
	public Setting(int id, String name, String description, Module parent, double dval, double min, double max, boolean onlyint)
	{
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.mode = "Slider";
		this.id = id;
		this.visible = true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Module getParentMod()
	{
		return parent;
	}
	
	public String getValString()
	{
		return this.sval;
	}
	
	public void setValString(String in)
	{
		this.sval = in;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public ArrayList<String> getOptions()
	{
		return this.options;
	}
	
	public void setOptions(ArrayList<String> options)
	{
		this.options = options;
	}
	
	public boolean getValBoolean()
	{
		return this.bval;
	}
	
	public void setValBoolean(boolean in)
	{
		this.bval = in;
	}
	
	public double getValueD()
	{
		if (this.onlyint)
		{
			this.dval = (int) dval;
		}
		
		return this.dval;
	}

	public void setValueD(double in)
	{
		this.dval = in;
	}
	
	public int getValueI()
	{
		return (int) this.dval;
	}

	public void setValueI(int in)
	{
		this.dval = in;
	}
	
	public double getMinD()
	{
		return this.min;
	}
	
	public double getMaxD()
	{
		return this.max;
	}
	
	public int getMinI()
	{
		return (int) this.min;
	}
	
	public int getMaxI()
	{
		return (int) this.max;
	}
	
	public boolean isCombo()
	{
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}
	
	public boolean isCheck()
	{
		return this.mode.equalsIgnoreCase("Check") ? true : false;
	}
	
	public boolean isSlider()
	{
		return this.mode.equalsIgnoreCase("Slider") ? true : false;
	}
	
	public boolean onlyInt()
	{
		return this.onlyint;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public double getMin()
	{
		return min;
	}

	public void setMin(double min)
	{
		this.min = min;
	}

	public double getMax()
	{
		return max;
	}

	public void setMax(double max)
	{
		this.max = max;
	}
}