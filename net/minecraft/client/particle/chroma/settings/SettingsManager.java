package net.minecraft.client.particle.chroma.settings;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.module.Module;

public class SettingsManager
{
	private ArrayList<Setting> settings;
	
	public SettingsManager()
	{
		settings = new ArrayList<Setting>();
		settings.clear();
	}
	
	public void rSetting(Setting in)
	{
		settings.add(in);
	}
	
	public ArrayList<Setting> getSettings()
	{
		return settings;
	}
	
	public ArrayList<Setting> getSettingsByMod(Module m)
	{
		ArrayList<Setting> out = new ArrayList<Setting>();
		
		for (Setting s : getSettings())
		{
			if (s.getParentMod().equals(m))
			{
				out.add(s);
			}
		}
		
		if (out.isEmpty())
		{
			return null;
		}
		
		return out;
	}
	
	public Setting getSettingByName(String name)
	{
		for (Setting set : getSettings())
		{
			if (set.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
			{
				return set;
			}
		}
		
		return null;
	}
	
	public Setting getSettingByName(String name, String mName)
	{
		for (Setting set : getSettings())
		{
			if (set.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()) && set.getParentMod().getName().toLowerCase() == mName.toLowerCase())
			{
				return set;
			}
		}
		
		return null;
	}
	
	public Setting getSettingByName(String name, int id)
	{
		for (Setting set : getSettings())
		{
			if (set.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()) && set.getParentMod().getId() == id)
			{
				return set;
			}
		}
		
		return null;
	}
	
	public Setting getSettingById(int id)
	{
		for (Setting set : getSettings())
		{
			if (set.getId() == id)
			{
				return set;
			}
		}
		
		return null;
	}
	
	public Setting getSettingByName(String name, Module inheritance)
	{
		for (Setting set : getSettings())
		{
			if (set.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()) && set.getParentMod() == inheritance)
			{
				return set;
			}
		}
		
		return null;
	}
}
