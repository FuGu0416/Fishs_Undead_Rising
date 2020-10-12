package com.Fishmod.mod_LavaCow.util;

import java.lang.reflect.Field;
import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.blocks.BlockBasic;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.init.ModPotions;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.item.ItemScarecrowHead;
import com.Fishmod.mod_LavaCow.item.crafting.RecipePoisonArrow;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
@EventBusSubscriber
public class RegistryHandler {
	
	public static final IRecipe POISON_ARROW = new RecipePoisonArrow().setRegistryName(mod_LavaCow.MODID, "poisonarrow");	  
	
    /**
     * Register this mod's {@link Item}s and {@link ItemBlock}s.
     *
     * @param event The event
     */
	@SubscribeEvent
    public static void registerItems(Register<Item> event) {
        for (Field field : FishItems.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof Item) {
                	Item item = (Item) obj;
                	event.getRegistry().register(item);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    	
    	event.getRegistry().registerAll(               
                BlockBasic.setItemName(new ItemBlock(Modblocks.GLOWSHROOM), Modblocks.GLOWSHROOM.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.PILEOFSLUDGE), Modblocks.PILEOFSLUDGE.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.GLOWSHROOM_BLOCK_STEM), Modblocks.GLOWSHROOM_BLOCK_STEM.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.GLOWSHROOM_BLOCK_CAP), Modblocks.GLOWSHROOM_BLOCK_CAP.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.BLOODTOOTH_SHROOM), Modblocks.BLOODTOOTH_SHROOM.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.CORDY_SHROOM), Modblocks.CORDY_SHROOM.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.VEIL_SHROOM), Modblocks.VEIL_SHROOM.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemScarecrowHead(Modblocks.SCARECROWHEAD_COMMON), Modblocks.SCARECROWHEAD_COMMON.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemScarecrowHead(Modblocks.SCARECROWHEAD_STRAW), Modblocks.SCARECROWHEAD_STRAW.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemScarecrowHead(Modblocks.SCARECROWHEAD_PLAGUE), Modblocks.SCARECROWHEAD_PLAGUE.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS),
                BlockBasic.setItemName(new ItemBlock(Modblocks.TOMBSTONE), Modblocks.TOMBSTONE.getRegistryName().getResourcePath()).setCreativeTab(mod_LavaCow.TAB_ITEMS)
        );        
    }
    
    @SubscribeEvent
	public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {	
        for (Field field : FishItems.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof SoundEvent) {
                	SoundEvent sound = (SoundEvent) obj;
                	event.getRegistry().register(sound);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
	}
    
	@SubscribeEvent
	public static void registerEnchantments(Register<Enchantment> event) {
        for (Field field : ModEnchantments.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof Enchantment) {
                	Enchantment enchantment = (Enchantment) obj;
                	event.getRegistry().register(enchantment);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
	}  
		
	@SubscribeEvent
	public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(POISON_ARROW);
	}
	
    @SubscribeEvent
    public static void onRegisterPotion(RegistryEvent.Register<Potion> event) {
        for (Field field : ModMobEffects.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof Potion) {
                	Potion potion = (Potion) obj;
                	event.getRegistry().register(potion);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    }
    
    @SubscribeEvent
    public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event) {
        for (Field field : ModPotions.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof PotionType) {
                	PotionType potiontype = (PotionType) obj;
                	event.getRegistry().register(potiontype);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    }
}
