package com.Fishmod.mod_LavaCow.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class FishGuiConfig extends GuiConfig {
	
    public FishGuiConfig(GuiScreen parent) {
        super(parent, getElements(), mod_LavaCow.MODID, false, false, "Fish's Undead Rising Config");
        titleLine2 = Modconfig.config.getConfigFile().getAbsolutePath();
    }
    
	private static List<IConfigElement> getElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		for (String category : Modconfig.INSTANCE.usedCategories) {
			//System.out.println("loading config category " + category.toLowerCase());
			list.add(new ConfigElement(Modconfig.config.getCategory(category.toLowerCase())));
		}
		return list;
	}

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
