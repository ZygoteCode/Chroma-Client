package net.minecraft.client.particle.chroma.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.Minecraft;

public class Database
{
	private String name;
	private String data;
	
	public static final Path MAIN = Minecraft.getMinecraft().mcDataDir.toPath().resolve("chroma");
	
	public Database(String n)
	{
		this.name = n;
		
        for (Field field : Database.class.getFields())
        {
            Path path = null;
            
			try
			{
				path = (Path)field.get(null);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
            if (Files.exists(path, new LinkOption[0])) continue;
            
            try
            {
				Files.createDirectory(path, new FileAttribute[0]);
			}
            catch (IOException e)
            {
				e.printStackTrace();
			}
        }
		
		File file = new File(MAIN.toFile(), this.getName().toLowerCase() + ".yml");
		
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				
			}
		}
	}
	
	public void setName(String n)
	{
		this.name = n;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void reload()
	{
        for (Field field : Database.class.getFields())
        {
            Path path = null;
            
			try
			{
				path = (Path)field.get(null);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
            if (Files.exists(path, new LinkOption[0])) continue;
            
            try
            {
				Files.createDirectory(path, new FileAttribute[0]);
			}
            catch (IOException e)
            {
				e.printStackTrace();
			}
        }
        
		File file = new File(MAIN.toFile(), this.getName().toLowerCase() + ".yml");
		FileInputStream inputStream = null;
		
		try
		{
			inputStream = new FileInputStream(file);
		}
		catch(FileNotFoundException e)
		{
			
		}
		
		try
		{
		    try
		    {
				String everything = IOUtils.toString(inputStream);
				this.data = everything;
			}
		    catch (IOException e)
		    {
		    	
		    }
		}
		finally
		{
		    try
		    {
				inputStream.close();
			} 
		    catch (IOException e)
		    {
		    	
		    }
		}
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public void addLine(String line)
	{
		if (data == "")
		{
			data = line;
		}
		else
		{
			data += "\n" + line;
		}
	}
	
	public String getData()
	{
		return this.data;
	}
	
	public String[] getLines()
	{
		return this.data.split("\n");
	}
	
	public ArrayList<String> getArrayLines()
	{
		ArrayList<String> arrayLines = new ArrayList<String>();
		String[] lines = getLines();
		
		for (int i = 0; i < lines.length; i++)
		{
			arrayLines.add(lines[i]);
		}
		
		return arrayLines;
	}
	
	public List<String> getListLines()
	{
		List<String> arrayLines = new ArrayList<String>();
		String[] lines = getLines();
		
		for (int i = 0; i < lines.length; i++)
		{
			arrayLines.add(lines[i]);
		}
		
		return arrayLines;
	}
	
	public String getLine(int i)
	{
		String[] lines = getLines();
		String line = "";
		
		for (int k = 0; k < lines.length; k++)
		{
			if (k == i)
			{
				line = lines[k];
				break;
			}
		}
		
		return line;
	}
	
	public void delete()
	{
		File file = new File(MAIN.toFile(), this.getName().toLowerCase() + ".yml");
		
		if (file.exists())
		{
			file.delete();
		}
	}
	
	public boolean isUsable()
	{
		return !(this.data == "") && !(this.data == null);
	}
	
	public void save()
	{
        for (Field field : Database.class.getFields())
        {
            Path path = null;
            
			try
			{
				path = (Path)field.get(null);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
            if (Files.exists(path, new LinkOption[0])) continue;
            
            try
            {
				Files.createDirectory(path, new FileAttribute[0]);
			}
            catch (IOException e)
            {
				e.printStackTrace();
			}
        }
		
        BufferedWriter output = null;
        
        try
        {
    		File file = new File(MAIN.toFile(), this.getName().toLowerCase() + ".yml");
            output = new BufferedWriter(new FileWriter(file));
            output.write(getData());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
          if (output != null)
          {
            try
            {
				output.close();
			}
            catch (IOException e)
            {
				e.printStackTrace();
			}
          }
        }
	}
}