package net.minecraft.client.particle.chroma.module;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.module.combat.*;
import net.minecraft.client.particle.chroma.module.exploit.*;
import net.minecraft.client.particle.chroma.module.gui.*;
import net.minecraft.client.particle.chroma.module.minigames.BedFucker;
import net.minecraft.client.particle.chroma.module.world.*;
import net.minecraft.client.particle.chroma.utils.CustomFontRenderer;
import net.minecraft.client.particle.chroma.module.movement.*;
import net.minecraft.client.particle.chroma.module.player.*;
import net.minecraft.client.particle.chroma.module.render.*;
import net.minecraft.client.particle.chroma.module.other.*;
import net.minecraft.client.particle.chroma.module.fun.*;
import net.minecraft.client.particle.chroma.module.minigames.*;

public class ModuleManager
{
	private ArrayList<Module> modules;
	private ArrayList<Integer> noLoginModules;
	
	public ModuleManager() throws ClassNotFoundException, IOException
	{
		modules = new ArrayList<Module>();
		noLoginModules = new ArrayList<Integer>();
		
		addModule(new ClickGui());
		addModule(new Ghost());
		addModule(new Panic());
		addModule(new ArmorHUD());
		addModule(new PotionHUD());
		addModule(new Sprint());
		addModule(new KeepSprint());
		addModule(new Parkour());
		addModule(new HitAnimation());
		addModule(new WallHack());
		addModule(new NoHurtcam());
		addModule(new NoFall());
		addModule(new Fullbright());
		addModule(new InventoryMove());
		addModule(new NoRotationSet());
		addModule(new AutoRespawn());
		addModule(new PingSpoof());
		addModule(new AutoArmor());
		addModule(new NoScoreboard());
		addModule(new FastLadder());
		addModule(new Timer());
		addModule(new ItemPhysics());
		addModule(new Killaura());
		addModule(new Hitbox());
		addModule(new AntiBot());
		addModule(new Velocity());
		addModule(new Criticals());
		addModule(new NoSlowdown());
		addModule(new Tower());
		addModule(new NoWeb());
		addModule(new InventoryCleaner());
		addModule(new ChestStealer());
		addModule(new NoInvisibles());
		addModule(new AntiCactus());
		addModule(new Teleport());
		addModule(new HighJump());
		addModule(new Step());
		addModule(new NoPitchLimit());
		addModule(new NoBob());
		addModule(new AntiLag());
		addModule(new Scaffold());
		addModule(new AutoJump());
		addModule(new AutoWalk());
		addModule(new Disabler());
		addModule(new Eagle());
		addModule(new Speed());
		addModule(new PlayerFlight());
		addModule(new NoHitDelay());
		addModule(new StorageESP());
		addModule(new PlayerESP());
		addModule(new ItemESP());
		addModule(new Overlay());
		addModule(new ProphuntESP());
		addModule(new BedFucker());
		addModule(new ChestAura());
		addModule(new NoEffects());
		addModule(new Trajectories());
		addModule(new FastPlace());
		addModule(new AutoPot());
		addModule(new AutoSoup());
		addModule(new Jesus());
		addModule(new BowAimbot());
		addModule(new FastConsume());
		addModule(new NoFriends());
		addModule(new FastUse());
		addModule(new AutoSpawn());
		addModule(new AutoLeave());
		addModule(new FastBreak());
		addModule(new Anticheat());
		addModule(new AutoUse());
		addModule(new MidClick());
		addModule(new AirJump());
		addModule(new FastStairs());
		addModule(new FastBow());
		addModule(new SuperKnockback());
		addModule(new IceSpeed());
		addModule(new AutoClicker());
		addModule(new AimAssist());
		addModule(new Reach());
		addModule(new Regen());
		addModule(new Tracers());
		addModule(new AutoEat());
		addModule(new AutoGapple());
		addModule(new SafeWalk());
		addModule(new QuakeAura());
		addModule(new AntiAFK());
		addModule(new AntiFire());
		addModule(new ForcePush());
		addModule(new AutoSwitch());
		addModule(new SkinDerp());
		addModule(new Tired());
		addModule(new NoWeather());
		addModule(new Dolphin());
		addModule(new Fish());
		addModule(new AutoFish());
		addModule(new AutoSign());
		addModule(new RideFly());
		addModule(new AntiSpam());
		addModule(new FancyChat());
		addModule(new BaseFinder());
		addModule(new CaveFinder());
		addModule(new XRay());
		addModule(new CameraNoClip());
		addModule(new Derp());
		addModule(new Headless());
		addModule(new HeadRoll());
		addModule(new Freecam());
		addModule(new Astronaut());
		addModule(new HealthTags());
		addModule(new NameTags());
		addModule(new Jetpack());
		addModule(new Kick());
		addModule(new DeadGhost());
		addModule(new Damage());
		addModule(new Teams());
		addModule(new VClip());
		addModule(new Crasher());
		addModule(new SlimeJump());
		addModule(new NoJumpDelay());
		addModule(new NoHunger());
		addModule(new KeepContainer());
		addModule(new PluginFinder());
		addModule(new Liquids());
		addModule(new OtherSpeed());
		addModule(new ResPackSpoof());
		addModule(new TeleportAccept());
		addModule(new CivBreak());
		addModule(new MoreCarry());
		addModule(new Paralyze());
		addModule(new ConsoleSpammer());
		addModule(new TNTBlock());
		addModule(new AntiSpy());
		
		// Current max module/setting id: 134/350
	}
	
	public void addModule(Module m)
	{
		modules.add(m);
	}
	
	public void removeModule(Module m)
	{
		modules.remove(m);
	}
	
	public void clearModules()
	{
		modules.clear();
	}
	
	public ArrayList<Module> getModules()
	{
		return modules;
	}
	
	public void addNoLoginModule(int nlm)
	{
		noLoginModules.add(nlm);
	}
	
	public void removeNoLoginModule(int nlm)
	{
		noLoginModules.remove(nlm);
	}
	
	public void clearNoLoginModules()
	{
		noLoginModules.clear();
	}
	
	public ArrayList<Integer> getNoLoginModules()
	{
		return noLoginModules;
	}
	
	public boolean isNoLoginModule(int nlm)
	{
		return noLoginModules.contains(nlm);
	}
	
	public Module getModuleByName(String name)
	{
		for (Module m: getModules())
		{
			if (m.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()) || name.toLowerCase().equalsIgnoreCase(m.getName().toLowerCase()))
			{
				return m;
			}
		}
		
		return null;
	}
	
	public Module getModuleByKey(int key)
	{
		for (Module m: getModules())
		{
			if (m.getKey() == key)
			{
				return m;
			}
		}
		
		return null;
	}
	
	public Module getModuleByDescription(String description)
	{
		for (Module m: getModules())
		{
			if (m.getDescription().toLowerCase() == description.toLowerCase())
			{
				return m;
			}
		}
		
		return null;
	}
	
	public Module getModuleByID(int id)
	{
		for (Module m: getModules())
		{
			if (m.getId() == id)
			{
				return m;
			}
		}
		
		return null;
	}
	
	public Module getModuleByInheritance(Class inheritance)
	{
		for (Module m: getModules())
		{
			if (m.getInheritance() == inheritance)
			{
				return m;
			}
		}
		
		return null;
	}
	
	public ArrayList<Module> getModulesForCategory(Category category)
	{
		ArrayList<Module> categoryModules = new ArrayList<Module>();
		
		for (Module m: getModules())
		{
			if (m.getCategory() == category)
			{
				categoryModules.add(m);
			}
		}
		
		return categoryModules;
	}
	
	public boolean isCategoryEmpty(Category category)
	{
		if (getModulesForCategory(category).isEmpty() || getModulesForCategory(category) == null)
		{
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Module> getToggledModules()
	{
		ArrayList<Module> toggledModules = new ArrayList<Module>();
		
		for (Module m: getModules())
		{
			if (m.isToggled())
			{
				toggledModules.add(m);
			}
		}
		
		return toggledModules;
	}
	
	public ArrayList<Module> getUntoggledModules()
	{
		ArrayList<Module> untoggledModules = new ArrayList<Module>();
		
		for (Module m: getModules())
		{
			if (!m.isToggled())
			{
				untoggledModules.add(m);
			}
		}
		
		return untoggledModules;
	}
	
	public void onModuleKey(int key)
	{
		Module m = getModuleByKey(key);
		
		if (!(m == null))
		{
			m.toggle();
		}
	}
	
	public ArrayList<Module> sortModules(ArrayList<Module> modulesToSort, boolean mode, boolean coloredMode)
	{
		ArrayList<Module> sortedModules = new ArrayList<Module>();
		
		int length = 500;
		
		for (int j = 0; j < 500; j++)
		{
			length--;
			
			for (Module m: modulesToSort)
			{
				if (coloredMode || (mode && coloredMode))
				{
					if ((m.getName() + m.getSuffix()).length() == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
				else if (mode)
				{	
					if ((m.getName() + " " + m.getMode()).length() == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
				else
				{
					if (m.getName().length() == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
			}
		}
		
		return sortedModules;
	}
	
	public ArrayList<Module> sortModules(ArrayList<Module> modulesToSort, boolean mode, boolean coloredMode, CustomFontRenderer fontRenderer)
	{
		ArrayList<Module> sortedModules = new ArrayList<Module>();
		
		int length = 130;
		
		for (int j = 0; j < 130; j++)
		{
			length--;
			
			for (Module m: modulesToSort)
			{
				if (coloredMode || (mode && coloredMode))
				{
					if (fontRenderer.getStringWidth(m.getName() + m.getSuffix()) == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
				else if (mode)
				{	
					if (fontRenderer.getStringWidth(m.getName() + " " + m.getMode()) == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
				else
				{
					if (fontRenderer.getStringWidth(m.getName()) == length && !(m.getCategory() == Category.NONE))
					{
						sortedModules.add(m);
					}
				}
			}
		}
		
		return sortedModules;
	}
}