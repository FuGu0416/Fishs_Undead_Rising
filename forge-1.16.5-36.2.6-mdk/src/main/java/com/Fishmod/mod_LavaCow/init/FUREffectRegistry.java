package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.FURBrewingRecipe;
import com.Fishmod.mod_LavaCow.effect.EffectCharmingPheromone;
import com.Fishmod.mod_LavaCow.effect.EffectCorroded;
import com.Fishmod.mod_LavaCow.effect.EffectFear;
import com.Fishmod.mod_LavaCow.effect.EffectFlourished;
import com.Fishmod.mod_LavaCow.effect.EffectFragile;
import com.Fishmod.mod_LavaCow.effect.EffectImmolation;
import com.Fishmod.mod_LavaCow.effect.EffectInfested;
import com.Fishmod.mod_LavaCow.effect.EffectSoiled;
import com.Fishmod.mod_LavaCow.effect.EffectThorned;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FUREffectRegistry {
	public static final Effect CORRODED = new EffectCorroded();
	public static final Effect SOILED = new EffectSoiled();
	public static final Effect INFESTED = new EffectInfested().addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final Effect FRAGILE = new EffectFragile();
	public static final Effect FEAR = new EffectFear().addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", -4.0D, AttributeModifier.Operation.ADDITION).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)-0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final Effect THORNED = new EffectThorned();
	public static final Effect IMMOLATION = new EffectImmolation();
	public static final Effect CHARMING_PHEROMONE = new EffectCharmingPheromone();
	public static final Effect FLOURISHED = new EffectFlourished();
	public static final Potion CORROSIVE_POTION = new Potion(new EffectInstance(CORRODED, 900)).setRegistryName(mod_LavaCow.MODID + ":corrosive");
	public static final Potion STRONG_CORROSIVE_POTION = new Potion(new EffectInstance(CORRODED, 900, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_corrosive");
	public static final Potion LONG_CORROSIVE_POTION = new Potion(new EffectInstance(CORRODED, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_corrosive");
	public static final Potion FOULODOR_POTION = new Potion(new EffectInstance(SOILED, 900)).setRegistryName(mod_LavaCow.MODID + ":foulodor");
	public static final Potion STRONG_FOULODOR_POTION = new Potion(new EffectInstance(SOILED, 900, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_foulodor");
	public static final Potion LONG_FOULODOR_POTION = new Potion(new EffectInstance(SOILED, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_foulodor");
	public static final Potion INFESTATION_POTION = new Potion(new EffectInstance(INFESTED, 1800)).setRegistryName(mod_LavaCow.MODID + ":infestation");
	public static final Potion STRONG_INFESTATION_POTION = new Potion(new EffectInstance(INFESTED, 1800, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_infestation");
	public static final Potion LONG_INFESTATION_POTION = new Potion(new EffectInstance(INFESTED, 3600)).setRegistryName(mod_LavaCow.MODID + ":long_infestation");
	public static final Potion FRAGILE_POTION = new Potion(new EffectInstance(FRAGILE, 900)).setRegistryName(mod_LavaCow.MODID + ":fragile");
	public static final Potion STRONG_FRAGILE_POTION = new Potion(new EffectInstance(FRAGILE, 900, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_fragile");
	public static final Potion LONG_FRAGILE_POTION = new Potion(new EffectInstance(FRAGILE, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_fragile");
	public static final Potion THORN_POTION = new Potion(new EffectInstance(THORNED, 3600)).setRegistryName(mod_LavaCow.MODID + ":thorn");
	public static final Potion STRONG_THORN_POTION = new Potion(new EffectInstance(THORNED, 1800, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_thorn");
	public static final Potion LONG_THORN_POTION = new Potion(new EffectInstance(THORNED, 9600)).setRegistryName(mod_LavaCow.MODID + ":long_thorn");
	public static final Potion IMMOLATION_POTION = new Potion(new EffectInstance(IMMOLATION, 3600)).setRegistryName(mod_LavaCow.MODID + ":immolation");
	public static final Potion STRONG_IMMOLATION_POTION = new Potion(new EffectInstance(IMMOLATION, 1800, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_immolation");
	public static final Potion LONG_IMMOLATION_POTION = new Potion(new EffectInstance(IMMOLATION, 9600)).setRegistryName(mod_LavaCow.MODID + ":long_immolation");
	
    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
        try {
            for (Field f : FUREffectRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Effect) {
                    event.getRegistry().register((Effect) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        try {
            for (Field f : FUREffectRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Potion) {
                    event.getRegistry().register((Potion) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        
        if (FURConfig.Potion_Enable.get()) {
        	FUREffectRegistry.onInitItems();
        }
    }

    public static ItemStack createPotion(Item potionType, Potion potion){
        return  PotionUtils.setPotion(new ItemStack(potionType), potion);
    }
    
    public static void onInitItems() {
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.STRONG_REGENERATION)), Ingredient.of(new ItemStack(FURItemRegistry.HYPHAE)), new ItemStack(FURItemRegistry.FISSIONPOTION)));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(new ItemStack(FURItemRegistry.FISSIONPOTION)), Ingredient.of(new ItemStack(FURItemRegistry.MOOTENHEART)), new ItemStack(FURItemRegistry.POTION_OF_MOOTEN_LAVA));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PHEROMONE_GLAND)), new ItemStack(FURItemRegistry.CHARMING_CATALYST)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PTERA_WING)), createPotion(Items.POTION, Potions.SLOW_FALLING)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_CORROSIVE_POTION)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE)), createPotion(Items.POTION, FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE)), createPotion(Items.LINGERING_POTION, FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE)), createPotion(Items.SPLASH_POTION, FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_FOULODOR_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FOULODOR_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_FOULODOR_POTION)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISONSPORE)), createPotion(Items.POTION, Potions.LONG_POISON)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISONSPORE)), createPotion(Items.LINGERING_POTION, Potions.LONG_POISON)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISONSPORE)), createPotion(Items.SPLASH_POTION, Potions.LONG_POISON)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_COMMON)), createPotion(Items.POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_COMMON)), createPotion(Items.LINGERING_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_COMMON)), createPotion(Items.SPLASH_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_DESERT)), createPotion(Items.POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_DESERT)), createPotion(Items.LINGERING_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_DESERT)), createPotion(Items.SPLASH_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_JUNGLE)), createPotion(Items.POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_JUNGLE)), createPotion(Items.LINGERING_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_JUNGLE)), createPotion(Items.SPLASH_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_INFESTATION_POTION)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD)), createPotion(Items.POTION, FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD)), createPotion(Items.LINGERING_POTION, FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD)), createPotion(Items.SPLASH_POTION, FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_FRAGILE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FRAGILE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_FRAGILE_POTION)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT)), createPotion(Items.POTION, THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT)), createPotion(Items.LINGERING_POTION, THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT)), createPotion(Items.SPLASH_POTION, THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_THORN_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, THORN_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_THORN_POTION)));
    
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.POTION, IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.LINGERING_POTION, IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.SPLASH_POTION, IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, IMMOLATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.POTION, LONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.LINGERING_POTION, LONG_IMMOLATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN)), createPotion(Items.SPLASH_POTION, LONG_IMMOLATION_POTION)));
    }
}
