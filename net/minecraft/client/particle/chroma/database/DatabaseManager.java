package net.minecraft.client.particle.chroma.database;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.database.databases.ChecksDatabase;
import net.minecraft.client.particle.chroma.database.databases.CombosDatabase;
import net.minecraft.client.particle.chroma.database.databases.FriendsDatabase;
import net.minecraft.client.particle.chroma.database.databases.KeysDatabase;
import net.minecraft.client.particle.chroma.database.databases.SlidersDatabase;
import net.minecraft.client.particle.chroma.database.databases.ToggledDatabase;

public class DatabaseManager
{
	private ArrayList<Database> databases;
	
	public DatabaseManager()
	{
		databases = new ArrayList<Database>();
		
		addDatabase(new ToggledDatabase());
		addDatabase(new KeysDatabase());
		addDatabase(new CombosDatabase());
		addDatabase(new ChecksDatabase());
		addDatabase(new SlidersDatabase());
		addDatabase(new FriendsDatabase());
	}
	
	public void addDatabase(Database database)
	{
		databases.add(database);
	}
	
	public void removeDatabase(Database database)
	{
		databases.remove(database);
	}
	
	public ArrayList<Database> getDatabases()
	{
		return databases;
	}
	
	public Database getDatabaseByName(String name)
	{
		for (Database database: databases)
		{
			if (name.toLowerCase() == database.getName().toLowerCase())
			{
				return database;
			}
		}
		
		Database database = new Database(name);
		database.setName(name);
		database.setData("");
		
		return database;
	}
}