package net.minecraft.client.particle.chroma.module;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventManager;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.settings.SettingsManager;
import net.minecraft.client.particle.chroma.utils.Timer;

public class Module
{
	private String name;
	private String description;
	private int key;
	private Category category;
	private boolean toggled;
	private int id;
	private Class inheritance;
	private double translate;
	private boolean animation;
	private boolean set;
	private Timer animationTimer;
	private Color color;
	private String suffix;
	private boolean toggable;
    private long currentMS;
    protected long lastMS;
	
	public static final Minecraft mc = Minecraft.getMinecraft();
	
	public Module(String n, String d, int k, Category c, boolean t, int i, Class h)
	{
		color = Color.WHITE;
		animationTimer = new Timer();
		translate = 0.0D;
		animation = false;
		set = false;
		name = n;
		description = d;
		key = k;
		category = c;
		toggled = t;
		id = i;
		inheritance = h;
		toggable = true;
		suffix = getModeColored();
		currentMS = 0L;
		lastMS = -1L;
		setup();
	}
	
	public Module(String n, String d, int k, Category c, boolean t, int i)
	{
		color = color.WHITE;
		animationTimer = new Timer();
		translate = 0.0D;
		animation = false;
		set = false;
		name = n;
		description = d;
		key = k;
		category = c;
		toggled = t;
		id = i;
		toggable = true;
		suffix = getModeColored();
		currentMS = 0L;
		lastMS = -1L;
		setup();
	}
	
	public Module(String n, int i, Category c)
	{
		color = color.WHITE;
		animationTimer = new Timer();
		translate = 0.0D;
		animation = false;
		set = false;
		name = n;
		description = "";
		key = Keyboard.KEY_NONE;
		category = c;
		toggled = false;
		id = i;
		toggable = true;
		suffix = getModeColored();
		currentMS = 0L;
		lastMS = -1L;
		setup();
	}
	
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	   public void portMove(float yaw, float multiplyer, float up)
	   {
		      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
		      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
		      double moveY = (double)up;
		      this.mc.thePlayer.setPosition(moveX + this.mc.thePlayer.posX, moveY + this.mc.thePlayer.posY, moveZ + this.mc.thePlayer.posZ);
	   }

		   public void move(float yaw, float multiplyer, float up) {
		      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
		      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
		      this.mc.thePlayer.motionX = moveX;
		      this.mc.thePlayer.motionY = (double)up;
		      this.mc.thePlayer.motionZ = moveZ;
		   }

		   public void move(float yaw, float multiplyer) {
		      double moveX = -Math.sin(Math.toRadians((double)yaw)) * (double)multiplyer;
		      double moveZ = Math.cos(Math.toRadians((double)yaw)) * (double)multiplyer;
		      this.mc.thePlayer.motionX = moveX;
		      this.mc.thePlayer.motionZ = moveZ;
		   }
	
	public void setRandomColor()
	{
	    /*String[] mColors = {
	            "#39add1",
	            "#3079ab",
	            "#c25975",
	            "#e15258",
	            "#f9845b",
	            "#838cc7",
	            "#7d669e",
	            "#53bbb4",
	            "#51b46d",
	            "#e0ab18",
	            "#637a91",
	            "#f092b0",
	            "#b7c0c7"
	    };
	    
        String color = "";
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);
        color = mColors[randomNumber];
        this.color = Color.decode(color);*/
        
		Random random = new Random();
		int R = (int) (random.nextDouble() * 256);
		int G = (int) (random.nextDouble() * 256);
		int B = (int) (random.nextDouble() * 256);
		Color color = new Color(R, G, B);
		final float hue = random.nextFloat();
		final float saturation = 0.9f;
		final float luminance = 1.0f;
		color = Color.getHSBColor(hue, saturation, luminance);
		this.color = color;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Chroma getChroma()
	{
		return mc.getChroma();
	}
	
	public SettingsManager getSetManager()
	{
		return getChroma().getSetManager();
	}
	
	public ModuleManager getModuleManager()
	{
		return getChroma().getModuleManager();
	}
	
	public String getMode()
	{
		try
		{
			return Minecraft.getMinecraft().getChroma().getSetManager().getSettingByName("Mode", this).getValString();
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	public String getModeColored()
	{
		try
		{
			return " " + "§7" + (mc.getChroma().getSetManager().getSettingById(13).getValBoolean() ? "§l" : "") + Minecraft.getMinecraft().getChroma().getSetManager().getSettingByName("Mode", this).getValString();
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	public void addSetting(Setting set)
	{
		mc.getChroma().getSetManager().rSetting(set);
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public int getKey()
	{
		return key;
	}
	
	public void setKey(int key)
	{
		this.key = key;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}
	
	public boolean isToggled()
	{
		return toggled;
	}
	
	public void setToggled(boolean toggled)
	{
		boolean canProceed = true;
		
		if (!(this.getId() == 0))
		{
			if (Minecraft.getMinecraft().getChroma().isGhostMode())
			{
				canProceed = false;
			}
		}
		
		if (!toggable)
		{
			canProceed = false;
		}
		
		if (canProceed)
		{
			this.toggled = toggled;
			onToggle();
			
			if (toggled)
			{
				onEnable();
			}
			else
			{
				onDisable();
			}
			
			try
			{
				Minecraft.getMinecraft().getChroma().endClient();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Class getInheritance()
	{
		return inheritance;
	}
	
	public void setInheritance(Class inheritance)
	{
		this.inheritance = inheritance;
	}
	
	public void onToggle()
	{
		setRandomColor();
	}
	
	public void onEnable()
	{
		EventManager.register(this);
		this.animation = true;
		this.animationTimer.reset();
	}
	
	public void onDisable()
	{
		EventManager.unregister(this);
	}
	
	public void setup()
	{
		
	}
	
	public void toggle()
	{
		boolean canProceed = true;
		
		try
		{
			if (!(this.getId() == 0))
			{
				if (Minecraft.getMinecraft().getChroma().isGhostMode())
				{
					canProceed = false;
				}
			}
		}
		catch (Exception e)
		{
			
		}
		
		if (!toggable)
		{
			canProceed = false;
		}
		
		if (canProceed)
		{
			toggled = !toggled;
			onToggle();
			
			if (toggled)
			{
				onEnable();
			}
			else
			{
				onDisable();
			}
			
			try
			{
				Minecraft.getMinecraft().getChroma().endClient();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public double getTranslate()
	{
		return translate;
	}

	public void setTranslate(double translate)
	{
		this.translate = translate;
	}

	public boolean isAnimation()
	{
		return animation;
	}

	public void setAnimation(boolean animation)
	{
		this.animation = animation;
	}

	public boolean isSet()
	{
		return set;
	}

	public void setSet(boolean set)
	{
		this.set = set;
	}

	public Timer getAnimationTimer()
	{
		return animationTimer;
	}

	public void setAnimationTimer(Timer animationTimer)
	{
		this.animationTimer = animationTimer;
	}

	public static Minecraft getMc()
	{
		return mc;
	}
	
    public final void updateMS()
    {
        this.currentMS = System.currentTimeMillis();
    }

    public final void updateLastMS()
    {
        this.lastMS = System.currentTimeMillis();
    }

    public final boolean hasTimePassedM(long MS)
    {
        return this.currentMS >= this.lastMS + MS;
    }

    public final boolean hasTimePassedS(float speed)
    {
        return this.currentMS >= this.lastMS + (long)(1000.0f / speed);
    }
}